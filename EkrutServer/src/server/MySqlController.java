package server;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Costumer;
import entities.CostumersReport;
import entities.Delivery;
import entities.DeliveryReport;
import entities.Device;
import entities.InventoryCall;
import entities.InventoryReport;
import entities.Order;
import entities.OrderReport;
import entities.ProductInDevice;
import entities.Sale;
import entities.SalesPattern;
import entities.User;
import enums.CallStatus;
import enums.CostumerStatus;
import enums.DeliveryStatus;
import enums.ProductStatus;
import enums.Region;
import enums.Role;
import enums.SaleStatus;
import enums.SupplyMethod;

/**
 * @author peleg MySqlController- a controller class that will connect between
 *         the server and the DB by retrieving data from the database and
 *         sending it to the server.
 *
 */
public class MySqlController {

	private static Connection dbConnector = null;

	public static void connectToDB(String dbName, String dbUserName, String dbPwd) {
		if (dbConnector != null) {
			return;
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			dbConnector = DriverManager.getConnection(dbName, dbUserName, dbPwd);
			System.out.println("SQL connection succeed");

		} catch (SQLException ex) {
			/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

	public static Connection getdbConnector() {
		return dbConnector;
	}

	public static void disconnectFromDB() {
		if (dbConnector != null) {
			try {
				dbConnector.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dbConnector = null;
		}

	}

	/**
	 * peleg LoginCheckAndUpdateLoggedIn method-a method that get an ArrayList with
	 * username and password and will do the login operation for any user
	 */
	public static User LoginCheckAndUpdateLoggedIn(ArrayList<String> userANDpassword) {
		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("SELECT * FROM ekrut.users WHERE username = ? and password = ?;");

			ps.setString(1, userANDpassword.get(0));
			ps.setString(2, userANDpassword.get(1));
			ResultSet result = ps.executeQuery();

			if (result.next() == false) {
				return null;
			} else {

				/**
				 * peleg user- a variable that will help us save the user that wants to log in
				 */
				// Save user details
				User user = new User(result.getString("username"), result.getString("password"),
						result.getString("firstName"), result.getString("lastName"), result.getString("email"),
						result.getString("phoneNumber"), result.getBoolean("isLoggedIn"), result.getString("id"),
						Role.valueOf(result.getString("role")), Region.valueOf(result.getString("region")));

				// Set the login status to true so no one else can access it
				try {
					ps = dbConnector.prepareStatement("UPDATE ekrut.users SET isLoggedIn = ? WHERE username = ?");
					System.out.println("Update succsed");
				} catch (SQLException e1) {
					System.out.println("update user to logged in failed!");
				}
				try {

					ps.setBoolean(1, true);
					ps.setString(2, result.getString("username"));
					ps.executeUpdate();
				} catch (Exception e) {
					System.out.println("Executing statement-Updating login status on users table had failed!");
				}
				// Return user details
				return user;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author Ron This method updates the login status of a user in the
	 *         "ekrut.users" database table.
	 * @param user A User object representing the user whose login status is being
	 *             updated.
	 * @throws SQLException If an error occurs while executing the SQL statement.
	 */
	public static void UserLogoutAndUpdateDB(User user) throws SQLException {
		PreparedStatement ps = dbConnector
				.prepareStatement("UPDATE ekrut.users SET isLoggedIn = ? " + "WHERE username = ?");
		System.out.println("Update succsed");
		try {
			ps.setBoolean(1, false);
			ps.setString(2, user.getUsername());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Executing statement-Updating login status on users table had failed!");
		}
	}

	/**
	 * @author Ron This method imports user data from the "ekrut.users" database
	 *         table and creates a User object with the imported data.
	 * 
	 * @param userInfo An ArrayList of Strings containing the user's username and
	 *                 status.
	 * @return A User object containing the imported data or null if the user is not
	 *         found.
	 * @throws SQLException If an error occurs while executing the SQL statement.
	 */
	public static User importUserData(ArrayList<String> userInfo) {
		String username = userInfo.get(0), status = userInfo.get(1);
		User user = null;
		PreparedStatement ps;
		try {
			ps = dbConnector.prepareStatement("SELECT * FROM ekrut.users WHERE username = ? AND role = ?");
			try {
				ps.setString(1, username);
				ps.setString(2, status);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Enter parameter to query fail on importUserData");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User(username, rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"),
						rs.getString("phoneNumber"), rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Import user data fail on importUserData");
		}
		System.out.println("User information imported successfully");
		return user;
	}

	/**
	 * 
	 * This method updates a user's role to "customer" in the "ekrut.users" and
	 * "ekrut.customers" database tables.
	 * 
	 * Additionally, it also inserts the customer's credit card, subscriber ID,
	 * status, and device name into the "ekrut.customers" table.
	 * 
	 * @param userData An ArrayList of Strings containing the user's username,
	 *                 credit card number, and membership status.
	 * 
	 * @throws SQLException If an error occurs while executing the SQL statement.
	 */
	public static void updateUserToCustomer(ArrayList<String> userData) {
		String username = userData.get(0), creditCard = userData.get(1);
		int isMember = Integer.valueOf(userData.get(2));

		String userArea = null, region = null;
		PreparedStatement ps = null;

		try {
			ps = dbConnector.prepareStatement("SELECT region FROM ekrut.users WHERE username = ?");
			try {
				ps.setString(1, username);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Find user region statement failed");
			}
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				userArea = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Find user region statement failed");
		}

		switch (userArea) {
		case "NORTH":
			region = "Haifa";
			break;
		case "SOUTH":
			region = "TelAviv";
			break;
		case "UAE":
			region = "Dubai";
			break;
		default:
			break;
		}

		if (isMember == 1) {
			try {
				ps = dbConnector.prepareStatement("SELECT MAX(subscriberID) FROM ekrut.costumers");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					isMember = rs.getInt(1);
				}
				isMember++;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Find max subsciberID statement failed");
			}
		}

		try {
			ps = dbConnector.prepareStatement(
					"INSERT INTO ekrut.costumers (username, creditCard, subscriberID, status, deviceName) VALUES (?, ?, ?, ?, ?)");
			try {
				ps.setString(1, username);
				ps.setString(2, creditCard);
				ps.setInt(3, isMember);
				ps.setString(4, "NOTAPPROVED");
				ps.setString(5, region);

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Set statement on updateUserToCustomer failed");
			}
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Update user to costumer failure");
		}
		try {
			ps = dbConnector.prepareStatement("UPDATE ekrut.users SET role = 'Costumer' WHERE username = ?");
			try {
				ps.setString(1, username);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Set statement on updateUserToCustomer failed");
			}
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Update user to costumer failure");
		}
	}

	/**
	 * This method updates a customer's status to "member" in the "costumers" table
	 * in the database. It finds the maximum subscriber ID of all members,
	 * increments it by 1, and then updates the subscriber ID for the customer with
	 * the specified username.
	 * 
	 * @param username the username of the customer to update
	 * 
	 */
	public static void updateCostumerToMember(String username) {
		PreparedStatement ps = null;
		int isMember = 0;

		try {
			ps = dbConnector.prepareStatement("SELECT MAX(subscriberID) FROM ekrut.costumers");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				isMember = rs.getInt(1);
			}
			isMember++;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Find max subsciberID statement failed");
		}
		try {
			ps = dbConnector.prepareStatement("UPDATE ekrut.costumers SET subscriberID = ? WHERE username = ?");
			try {
				ps.setInt(1, isMember);
				ps.setString(2, username);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Set statement on updateUserToCustomer failed");
			}
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Update user to costumer failure");
		}
		System.out.println("Update customer to member succeed");
	}

	/**
	 * This method retrieves delivery report data from the database for a specified
	 * month and year.
	 * 
	 * @param reportDetails An ArrayList of strings containing the details of the
	 *                      report to retrieve. The month should be in index 2 and
	 *                      the year in index 1
	 * @return A DeliveryReport object containing the data for the specified month
	 *         and year, or null if no data is found or an error occurs.
	 */

	public static DeliveryReport getDeliveryReportData(ArrayList<String> reportDetails) {
		String year = reportDetails.get(1), month = reportDetails.get(2);
		int numOfDeliveries = 0;
		float totalSumIncomes = 0;

		PreparedStatement ps = null;

		try {
			ps = dbConnector.prepareStatement("SELECT * FROM ekrut.delivery_report WHERE month = ? AND year = ?");
			ps.setString(1, month);
			ps.setString(2, year);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				numOfDeliveries = rs.getInt("numOfDeliveries");
				totalSumIncomes = rs.getFloat("totalSumIncomes");
			} else
				return null;
		} catch (SQLException e) {
			System.out.println("Statement for getting Monthly Delivery Report has failed!");
			return null;
		}

		System.out.println("Succefully imported Monthly Delivery Report Data");

		DeliveryReport report = new DeliveryReport(month, year, numOfDeliveries, totalSumIncomes);
		return report;
	}

	/**
	 * 
	 * This method retrieves order report data from a database and returns it as an
	 * OrderReport object.
	 * 
	 * @param reportDetails A list of strings containing the desired area, year, and
	 *                      month for the report.
	 * @return An OrderReport object containing the data for the specified month,
	 *         year, and area. Returns null if no data is found.
	 */

	public static OrderReport getOrdersReportData(ArrayList<String> reportDetails) {
		String area = reportDetails.get(0), year = reportDetails.get(1), month = reportDetails.get(2);
		String[] deviceList = null;
		String[] incomesList = null;
		String devices = null, incomes = "", mostSellingDevice = null;
		HashMap<String, Integer> mapOfDevices = new HashMap<String, Integer>();
		HashMap<String, Float> mapOfIncomes = new HashMap<String, Float>();

		int numOfTotalOrders = 0, numOfPickUpOrders = 0;
		PreparedStatement ps = null;
		try {
			ps = dbConnector.prepareStatement(
					"SELECT * FROM ekrut.orders_report " + "WHERE month = ? AND year = ? AND area = ?");
			ps.setString(1, month);
			ps.setString(2, year);
			ps.setString(3, area);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				devices = rs.getString("devices");
				incomes = rs.getString("Incomes");
				numOfTotalOrders = rs.getInt("numOfTotalOrders");
				numOfPickUpOrders = rs.getInt("totalPickUp");
				mostSellingDevice = rs.getString("mostSelling");

			} else
				return null;
		} catch (SQLException e) {
			System.out.println("Statement for getting Monthly Orders Report has failed!");
		}
		System.out.println("Succefully imported Monthly Orders Report Data");

		if (devices != null) {
			deviceList = devices.split(",");
		}
		if (incomes != null) {
			incomesList = incomes.split(",");
		}
		if (deviceList != null && deviceList.length % 2 == 0) {
			for (int i = 0; i < (deviceList.length); i = i + 2) {
				mapOfDevices.put(deviceList[i], (int) Integer.valueOf(deviceList[i + 1]));
				mapOfIncomes.put(incomesList[i], (float) Float.valueOf(incomesList[i + 1]));
			}
		}
		OrderReport report = new OrderReport(mapOfDevices, numOfTotalOrders, (float) numOfTotalOrders / 30,
				numOfPickUpOrders, area, month, year, mostSellingDevice, mapOfIncomes);

		return report;
	}

	/**
	 * 
	 * This method retrieves inventory report data from a database and returns it as
	 * an InventoryReport object.
	 * 
	 * @param reportDetails A list of strings containing the desired year, month,
	 *                      and device for the report.
	 * @return An InventoryReport object containing the data for the specified
	 *         month, year, and device. Returns null if no data is found.
	 */

	public static InventoryReport getInventoryReportData(ArrayList<String> reportDetails) {

		String year = reportDetails.get(1), month = reportDetails.get(2), device = reportDetails.get(4);
		HashMap<String, Integer> productsUnderThres = new HashMap<>();
		String mexProductUnderThres = null, products = null;
		String[] prList = null;
		Integer deviceThres = 0;

		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"SELECT * FROM ekrut.inventory_report WHERE " + "month = ? AND year = ? AND deviceName = ?");
			try {
				ps.setString(1, month);
				ps.setString(2, year);
				ps.setString(3, device);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Set statement parameters failed on getInventoryReportData");
			}

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				products = rs.getString("products");
				mexProductUnderThres = rs.getString("itemUnderThres");
				deviceThres = rs.getInt("deviceThres");
			} else {
				System.out.println("Import Monthly Inventory Report Data failed");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Executing query failed on getInventoryReportData");
		}

		System.out.println("Succefully imported Monthly Inventory Report Data");
		if (products != null)
			prList = products.split(",");
		if (prList != null && prList.length % 2 == 0) {
			for (int i = 0; i < prList.length; i += 2) {
				productsUnderThres.put(prList[i], (int) Integer.valueOf(prList[i + 1]));
			}
		}
		InventoryReport report = new InventoryReport(month, year, device, productsUnderThres, mexProductUnderThres,
				deviceThres);
		return report;
	}

	/**
	 * 
	 * Retrieves customer report data from a database for a given month, year, and
	 * area (region). The data includes counts for low, medium, high, and very high
	 * levels of customer activity.
	 * 
	 * @param reportDetails An ArrayList containing the region, year, and month for
	 *                      which to retrieve the report data.
	 * @return A CostumersReport object containing the retrieved data, or null if
	 *         the data could not be retrieved.
	 */

	public static CostumersReport getCostumersReportData(ArrayList<String> reportDetails) {

		String area = reportDetails.get(0), year = reportDetails.get(1), month = reportDetails.get(2);
		Integer lowActivity = 0, mediumActivity = 0, highActivity = 0, veryHighActivity = 0;
		PreparedStatement ps;
		try {
			ps = dbConnector.prepareStatement(
					"SELECT * FROM ekrut.costumer_report WHERE " + "month = ? AND year = ? AND region = ?");
			try {
				ps.setString(1, month);
				ps.setString(2, year);
				ps.setString(3, area);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Set statement parameters failed on getCostumersReportData");
			}

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				lowActivity = rs.getInt("lowActivityCount");
				mediumActivity = rs.getInt("mediumActivityCount");
				highActivity = rs.getInt("highActivityCount");
				veryHighActivity = rs.getInt("veryHighActivityCount");
			} else {
				System.out.println("Import Monthly Costumers Report Data failed");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Executing query failed on getCostumersReportData");
		}

		CostumersReport costumersReport = new CostumersReport(lowActivity, mediumActivity, highActivity,
				veryHighActivity, month, year, Region.valueOf(area));

		return costumersReport;
	}

	/**
	 * 
	 * Retrieves a list of all devices from a database that are located in a given
	 * area (region).
	 * 
	 * @param region A string representing the region for which to retrieve devices.
	 * @return An ArrayList of Device objects representing the retrieved devices, or
	 *         an empty list if no devices were found.
	 */

	public static ArrayList<Device> getAllDevicesByArea(String region) {
		PreparedStatement ps;
		ArrayList<Device> devices = new ArrayList<>();

		try {
			ps = dbConnector.prepareStatement("SELECT * FROM ekrut.devices WHERE region=\"" + region + "\";");
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				devices.add(new Device(res.getInt(1), Region.valueOf(res.getString(2)), res.getString(3)));
			}
			System.out.println("Import devices by region succeeded");
		} catch (SQLException e) {
			System.out.println("Import devices by region failed");
		}
		return devices;
	}

	/**
	 * 
	 * Retrieves a list of not approved costumers from the database by their region.
	 * 
	 * @param region the region to filter by
	 * @return a list of not approved costumers from the specified region
	 */

	public static ArrayList<Costumer> getNotApprovedCostumersByArea(String region) {
		PreparedStatement ps;
		ArrayList<Costumer> costumers = new ArrayList<>();
		try {
			ps = dbConnector.prepareStatement(
					"SELECT * FROM ekrut.costumers " + "INNER JOIN ekrut.users ON costumers.username = users.username "
							+ "WHERE users.region = ? AND costumers.status = ?");
			ps.setString(1, region);
			ps.setString(2, CostumerStatus.NOTAPPROVED.toString());
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				costumers.add(new Costumer(res.getString("username"), res.getString("creditCard"),
						res.getInt("subscriberID"), CostumerStatus.valueOf(res.getString("status"))));
			}
			System.out.println("Import customer by region succeeded");

		} catch (SQLException e) {
			System.out.println("Import costumers by region failed");
		}
		return costumers;
	}

	/**
	 * 
	 * This method updates the threshold of a list of devices in the database.
	 * 
	 * @param devicesToUpdate a list of devices to update
	 */
	public static void updateDeviceThreshold(ArrayList<Device> devicesToUpdate) {
		PreparedStatement ps;
		try {
			ps = dbConnector.prepareStatement("UPDATE ekrut.devices SET threshold = ? WHERE deviceName = ?");

			// Update the records in the database
			for (Device device : devicesToUpdate) {
				ps.setInt(1, device.getThreshold());
				ps.setString(2, device.getDeviceName());
				ps.executeUpdate();
			}
			System.out.println("Update threshold succeeded");

		} catch (SQLException e) {
			System.out.println("Update threshold failed");
		}

	}

	/**
	 * 
	 * This method updates the status of a list of costumers in the database.
	 * 
	 * @param CostumerToUpdate a list of costumer objects containing the new status
	 *                         to be updated
	 */
	public static void updateCostumerStatus(ArrayList<Costumer> CostumerToUpdate) {
		PreparedStatement ps;
		try {
			ps = dbConnector.prepareStatement("UPDATE ekrut.costumers SET status = ? WHERE username = ?");

			// Update the records in the database
			for (Costumer costumer : CostumerToUpdate) {
				ps.setString(1, costumer.getStatus().toString());
				ps.setString(2, costumer.getUsername());
				ps.executeUpdate();
			}
			System.out.println("Update costumer status succeeded");

		} catch (SQLException e) {
			System.out.println("Update costumer status failed");
		}

	}

	/**
	 * 
	 * This method creates a monthly delivery report for a given month and year. The
	 * report includes the total number of deliveries and total income from those
	 * deliveries. The report data is inserted into a delivery_report table in the
	 * database.
	 * 
	 * @param reportData a list of data containing the month, year and other
	 *                   information needed to create the report
	 */
	public static void createMonthlyDeliveryReport(ArrayList<String> reportData) {
		String month = reportData.get(0), year = reportData.get(1);
		int numOfDeliveries = 0;
		float totalSumIncomes = 0;

		ArrayList<Order> ordersOfDelivary = new ArrayList<>();

		ordersOfDelivary.addAll(getOrdersDataOfDevice("Delivery"));
		numOfDeliveries = ordersOfDelivary.size();
		for (Order order : ordersOfDelivary) {
			totalSumIncomes += order.getOrderPrice();
		}
		try {
			PreparedStatement ps = dbConnector.prepareStatement("INSERT INTO ekrut.delivery_report "
					+ "(month,year,numOfDeliveries,totalSumIncomes) VALUES(?, ?, ?, ?)");
			try {
				ps.setString(1, month);
				ps.setString(2, year);
				ps.setInt(3, numOfDeliveries);
				ps.setFloat(4, totalSumIncomes);

			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Enter data to deliveryReport on DB failed");
				System.out.println("Error on createMonthlyDeliveryReport");
			}
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Exucute statement failed");
			System.out.println("Error on createMonthlyDeliveryReport");
		}
		System.out.println("Insert data to delivery_report was successfull");
	}

	/**
	 * Creates a monthly orders report and stores it in the database. The report
	 * contains data on the total number of orders, the number of orders that were
	 * picked up, the most popular device in the area, and a list of all devices in
	 * the area and the number of orders for each device.
	 * 
	 * @param reportData a list containing the month, year, and area for which to
	 *                   create the report
	 */

	public static void createMonthlyOrdersReport(ArrayList<String> reportData) {

		String month = reportData.get(0), year = reportData.get(1), area = reportData.get(2);
		ArrayList<Device> areaDevices = getAllDevicesByArea(area);
		String mostSelling = null, devices = "", incomes = "";
		Integer max = 0, num;
		Float sumPrice;
		int totalOrders = 0, totalPickUpOrders = 0;
		HashMap<String, Integer> deviceNumOfOrders = new HashMap<>();
		HashMap<String, Float> deviceSumOfIncome = new HashMap<>();
		ArrayList<Order> ordersOfDevice = new ArrayList<>();

		for (Device device : areaDevices) {
			ordersOfDevice.addAll(getOrdersDataOfDevice(device.getDeviceName()));
		}
		for (Order order : ordersOfDevice) {
			if (order.getMonth().equals(month) && order.getYear().equals(year)) {
				totalOrders++;
				if (order.getSupplyMethod().equals(SupplyMethod.PickUp))
					totalPickUpOrders++;

				if (!deviceNumOfOrders.containsKey(order.getDeviceName())) {
					deviceNumOfOrders.put(order.getDeviceName(), 1);
					deviceSumOfIncome.put(order.getDeviceName(), order.getOrderPrice());
				} else {
					num = deviceNumOfOrders.get(order.getDeviceName()) + 1;
					deviceNumOfOrders.replace(order.getDeviceName(), num);
					sumPrice = deviceSumOfIncome.get(order.getDeviceName());
					deviceSumOfIncome.replace(order.getDeviceName(), sumPrice + order.getOrderPrice());
				}
			}

		}
		for (String str : deviceNumOfOrders.keySet()) {
			num = deviceNumOfOrders.get(str);
			if (num > max) {
				max = num;
				mostSelling = str;
			}
			devices += "," + str + "," + num;
			incomes += "," + str + "," + deviceSumOfIncome.get(str);
		}
		devices = devices.replaceFirst(",", "");
		incomes = incomes.replaceFirst(",", "");

		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("INSERT INTO ekrut.orders_report " + "(month, year, area, numOfTotalOrders, "
							+ "totalPickUp, mostSelling, devices, incomes) VALUES(?, ?, ?, ?, ?, ?, ?,?)");
			try {
				ps.setString(1, month);
				ps.setString(2, year);
				ps.setString(3, area);
				ps.setInt(4, totalOrders);
				ps.setInt(5, totalPickUpOrders);
				ps.setString(6, mostSelling);
				if (devices == "")
					devices = null;
				ps.setString(7, devices);
				if (incomes == "")
					incomes = null;
				ps.setString(8, incomes);

			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Enter data to ordersReport on DB failed");
				System.out.println("Error on createMonthlyOrderReport");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Exucute statement failed");
			System.out.println("Error on createMonthlyOrderReport");
		}
		System.out.println("Insert data to orders_report was successfull");

	}

	/**
	 * Retrieves a list of all orders for a specific device from the database.
	 * 
	 * @param deviceName the name of the device for which to retrieve the orders
	 * @return an ArrayList of Order objects containing the data for each order for
	 *         the given device
	 */
	private static ArrayList<Order> getOrdersDataOfDevice(String deviceName) {
		ArrayList<Order> orders = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector.prepareStatement("SELECT * FROM ekrut.orders WHERE deviceName = ? ");
			try {
				ps.setString(1, deviceName);
			} catch (Exception e) {
				System.out.println("Executing statement failed!");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				orders.add(new Order(deviceName, rs.getInt("orderID"), rs.getFloat("orderPrice"),
						rs.getString("username"), rs.getString("day"), rs.getString("month"), rs.getString("year"),
						SupplyMethod.valueOf(rs.getString("supplyMethod")), rs.getString("orderProducts")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Import orders data from orders table has failed");
			System.out.println("Failed at getOrdersDataOfDevice method");
		}
		return orders;
	}

	/**
	 * Creates a report on the inventory of a specific device for a given month and
	 * year. The report includes a list of all the products that were below a
	 * certain threshold, and the product with the highest count of being below the
	 * threshold.
	 * 
	 * @param reportData an ArrayList of Strings containing the month, year, and
	 *                   device for which the report is to be generated, in that
	 *                   order
	 */

	public static void createMonthlyInventoryReport(ArrayList<String> reportData) {
		String month = reportData.get(0), year = reportData.get(1), device = reportData.get(2);
		String itemUnderThres = null, itemsList = "";
		Integer max = 0, num;
		Integer deviceThres = getDeviceThreshold(device);
		HashMap<String, Integer> producsUnderThreshold = getProductsUnderThresholdCount(reportData);

		for (String str : producsUnderThreshold.keySet()) {
			num = producsUnderThreshold.get(str);
			itemsList += "," + str + "," + num.toString();
			if (max < num) {
				max = producsUnderThreshold.get(str);
				itemUnderThres = str;
			}
		}
		itemsList = itemsList.replaceFirst(",", "");

		try {
			PreparedStatement ps = dbConnector.prepareStatement("INSERT INTO ekrut.inventory_report "
					+ "(month, year, deviceName, products, itemUnderThres, deviceThres) VALUES(?, ?, ?, ?, ?, ?)");
			try {
				ps.setString(1, month);
				ps.setString(2, year);
				ps.setString(3, device);
				if (itemsList == "")
					itemsList = null;
				ps.setString(4, itemsList);
				ps.setString(5, itemUnderThres);
				ps.setInt(6, deviceThres);

			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Enter data to ordersReport on DB failed");
				System.out.println("Error on createMonthlyInventoryReport");
			}
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on execute statement");
		}

	}

	/**
	 * Retrieves a count of the number of products that were below a certain
	 * threshold for a given month and year, for a specific device.
	 * 
	 * @param reportData an ArrayList of Strings containing the month, year, and
	 *                   device for which the report is to be generated, in that
	 *                   order
	 * 
	 * @return a HashMap mapping product codes to their respective count of being
	 *         below the threshold
	 */
	public static HashMap<String, Integer> getProductsUnderThresholdCount(ArrayList<String> reportData) {
		String month = reportData.get(0), year = reportData.get(1), device = reportData.get(2);
		HashMap<String, Integer> productsThres = new HashMap<>();
		try {
			PreparedStatement ps = dbConnector.prepareStatement("SELECT * FROM ekrut.products_under_threshold "
					+ "WHERE deviceName = ? AND month = ? AND year = ? ");
			try {
				ps.setString(1, device);
				ps.setString(2, month);
				ps.setString(3, year);
			} catch (Exception e) {
				System.out.println("Executing statement failed");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				productsThres.put(rs.getString("prName"), rs.getInt("count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exucute statement failed");
			System.out.println("Error on getProductsUnderThresholdCount");
		}
		return productsThres;
	}

	/**
	 * 
	 * Retrieves the threshold of a specific device from the "devices" table in the
	 * database.
	 * 
	 * @param device the name of the device to retrieve the threshold for
	 * @return the threshold of the specified device or null if the device is not
	 *         found
	 */

	public static Integer getDeviceThreshold(String device) {
		Integer thres = 0;
		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("SELECT * FROM ekrut.devices " + "WHERE deviceName = ?");
			try {
				ps.setString(1, device);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Create statement failed on getDeviceThreshold");
			}
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				thres = rs.getInt("threshold");
			} else {
				System.out.println("Import device threshold failed");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement failed on getDeviceThreshold");
		}

		return thres;
	}

	/**
	 * Method that will import the product in device to the catalog screen
	 * 
	 * @param String object deviceName
	 * @return products - list of Products in device object
	 */
	public static ArrayList<ProductInDevice> getProductsFromDevice(String deviceName) {

		ArrayList<ProductInDevice> products = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("SELECT ekrut.products.*, ekrut.product_in_device.quantity , "
							+ "ekrut.product_in_device.status, ekrut.product_in_device.deviceName "
							+ "FROM ekrut.products,ekrut.product_in_device "
							+ "WHERE ekrut.products.productCode = ekrut.product_in_device.productCode and ekrut.product_in_device.status = ? "
							+ "and ekrut.product_in_device.deviceName = ?");

			try {
				ps.setString(1, "AVAILABLE");
				ps.setString(2, deviceName);

			} catch (Exception e) {
				System.out.println("Executing statement failed");
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				products.add(new ProductInDevice(rs.getInt("productCode"), rs.getString("productName"),
						rs.getDouble("price"), rs.getString("imagePath"), rs.getInt("quantity"),
						ProductStatus.valueOf(rs.getString("status")), rs.getString("deviceName")));
			}
			return products;

		} catch (Exception e) {
			System.out.println("Import products data has failed!");
			System.out.println("Failed at getOrdersDataOfDevice method");
		}

		return null;
	}

	/**
	 * 
	 * Retrieves a list of products that are below the threshold in a specific
	 * device from the "products" and "product_in_device" table in the database.
	 * 
	 * @param deviceName the name of the device to retrieve the products from.
	 * @return A list of products that are below the threshold in the specified
	 *         device or null if no products are found or an error occurs.
	 */
	public static ArrayList<ProductInDevice> getProductsUnderThresholdFromDevice(String deviceName) {
		// Need to make the query to bring only products under threshold
		ArrayList<ProductInDevice> products = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"SELECT ekrut.products.*, ekrut.product_in_device.quantity ,ekrut.product_in_device.status,"
							+ " ekrut.product_in_device.deviceName FROM ekrut.products,ekrut.product_in_device,ekrut.devices "
							+ "WHERE ekrut.products.productCode = ekrut.product_in_device.productCode "
							+ "and ekrut.product_in_device.deviceName = ? and devices.deviceName = product_in_device.deviceName "
							+ "and product_in_device.quantity < devices.threshold");

			try {
				ps.setString(1, deviceName);

			} catch (Exception e) {
				System.out.println("Executing statement failed");
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				products.add(new ProductInDevice(rs.getInt("productCode"), rs.getString("productName"),
						rs.getDouble("price"), rs.getString("imagePath"), rs.getInt("quantity"),
						ProductStatus.valueOf(rs.getString("status")), rs.getString("deviceName")));
			}
			return products;

		} catch (Exception e) {
			System.out.println("Import product has failed!");
			System.out.println("Failed to get product method");
		}

		return null;
	}

	/**
	 * a method that will help us import costumer data for the products in device.
	 * 
	 * @param userID String that will help us to import the costumer from DB
	 * @return costumer object
	 */
	public static Costumer getCostumerData(String userID) {
		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("SELECT ekrut.users.*,ekrut.costumers.creditCard,ekrut.costumers.subscriberID,"
							+ "ekrut.costumers.status,ekrut.costumers.deviceName "
							+ "FROM ekrut.users,ekrut.costumers WHERE ekrut.users.id = ? "
							+ "AND ekrut.users.username=ekrut.costumers.username");
			try {
				ps.setString(1, userID);
			} catch (Exception e) {
				System.out.println("Executing statement failed!");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Costumer costumer = new Costumer(rs.getString("username"), rs.getString("password"),
						rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"),
						rs.getString("phoneNumber"), rs.getBoolean("isLoggedIn"), rs.getString("id"),
						Role.valueOf(rs.getString("role")), Region.valueOf(rs.getString("region")),
						rs.getString("creditCard"), rs.getInt("subscriberID"),
						CostumerStatus.valueOf(rs.getString("status")), rs.getString("deviceName"));
				return costumer;
			}
		} catch (Exception e) {
			System.out.println("Import costumer data from user and costumer tables has failed!");
			System.out.println("Failed at getCostumerData method");
		}
		return null;
	}

	/**
	 * Creates a report on the activity level of customers in a given area during a
	 * specific month and year. The activity level is defined as follows: low
	 * activity: 0 to 5 orders medium activity: 6 to 12 orders high activity: 13 to
	 * 20 orders very high activity: more than 20 orders
	 * 
	 * @param reportData an ArrayList of Strings containing the month, year, and
	 *                   area for which the report is to be generated, in that order
	 */
	public static void createMonthlyCostumersReport(ArrayList<String> reportData) {
		String month = reportData.get(0), year = reportData.get(1), area = reportData.get(2);
		ArrayList<Costumer> costumersOfArea = getCostumersByArea(area);
		ArrayList<Integer> costumersOrdersCount = new ArrayList<>();
		HashMap<String, Integer> costumersActivity = new HashMap<>();

		costumersActivity.put("lowActivity", 0);
		costumersActivity.put("mediumActivity", 0);
		costumersActivity.put("highActivity", 0);
		costumersActivity.put("veryHigh", 0);

		for (Costumer c : costumersOfArea) {
			costumersOrdersCount.add(getCostumerOrdersCount(c.getUsername(), month, year));
		}

		for (Integer i : costumersOrdersCount) {
			if (i >= 0 && i <= 5) {
				Integer val = costumersActivity.get("lowActivity");
				val++;
				costumersActivity.replace("lowActivity", val);
			}

			else if (i > 5 && i <= 12) {
				Integer val = costumersActivity.get("mediumActivity");
				val++;
				costumersActivity.replace("mediumActivity", val);
			}

			else if (i > 12 && i <= 20) {
				Integer val = costumersActivity.get("highActivity");
				val++;
				costumersActivity.replace("highActivity", val);
			}

			else {
				Integer val = costumersActivity.get("veryHigh");
				val++;
				costumersActivity.replace("veryHigh", val);
			}
		}

		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("INSERT INTO ekrut.costumer_report " + "(region, month, year, lowActivityCount, "
							+ "mediumActivityCount, highActivityCount, veryHighActivityCount) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
			try {
				ps.setString(1, area);
				ps.setString(2, month);
				ps.setString(3, year);
				ps.setInt(4, costumersActivity.get("lowActivity"));
				ps.setInt(5, costumersActivity.get("mediumActivity"));
				ps.setInt(6, costumersActivity.get("highActivity"));
				ps.setInt(7, costumersActivity.get("veryHigh"));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Enter costumer report details to DB failed");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement on createMonthlyCostumersReport failed");
		}

	}

	/**
	 * method will return the number of orders that related to costumer that he made
	 * on related month and year
	 * 
	 * @param costumer username, month and year
	 * 
	 * @return Array list of costumers that belongs to his area
	 */
	private static int getCostumerOrdersCount(String username, String month, String year) {
		Integer cnt = 0;
		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"SELECT COUNT(*) FROM ekrut.orders " + "WHERE username = ? AND month = ? AND year = ?");
			try {
				ps.setString(1, username);
				ps.setString(2, month);
				ps.setString(3, year);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Executing statement failed on getCostumerOrdersCount");
			}
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				cnt = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement failed");
		}

		return cnt;
	}

	/**
	 * method will return all costumers that approved on specific area
	 * 
	 * @param area name
	 * 
	 * @return Array list of costumers that belongs to his area
	 */
	private static ArrayList<Costumer> getCostumersByArea(String area) {

		ArrayList<Costumer> areaCostumers = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("SELECT c.username, c.creditCard, c.subscriberID, c.status, c.deviceName, "
							+ "u.firstName, u.password, u.lastName, u.email, u.phoneNumber, u.isLoggedIn, u.id, "
							+ "u.role, u.region " + "FROM ekrut.costumers c "
							+ "JOIN ekrut.users u ON c.username = u.username "
							+ "WHERE u.region = ? AND c.status = 'APPROVED'");
			try {
				ps.setString(1, area);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Executing statement failed on getCostumersByArea");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				areaCostumers.add(new Costumer(rs.getString("username"), rs.getString("password"),
						rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"),
						rs.getString("phoneNumber"), rs.getBoolean("isLoggedIn"), rs.getString("id"),
						Role.valueOf(rs.getString("role")), Region.valueOf(rs.getString("region")),
						rs.getString("creditCard"), rs.getInt("subscriberID"),
						CostumerStatus.valueOf(rs.getString("status")), rs.getString("deviceName")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement failed on getCostumersByArea");
		}

		return areaCostumers;
	}

	/**
	 * 
	 * This method creates an inventory call for a specific device and product under
	 * thershold. It checks if there is already an open inventory call for the same
	 * device and product, if there is, it returns false, indicating that the call
	 * was not created. If the call is created successfully, it returns true.
	 * 
	 * @param callData - an ArrayList of Strings containing the device name and
	 *                 product name to create the call for.
	 * @return boolean - indicating whether the call was created successfully or
	 *         not.
	 */
	public static boolean createInventoryCall(ArrayList<String> callData) {
		String device = callData.get(0), product = callData.get(1);
		int isExist = 0;
		try {
			PreparedStatement psExist = dbConnector.prepareStatement("SELECT COUNT(*) FROM ekrut.inventory_calls "
					+ "WHERE status = 'OPEN' AND deviceName = ? AND productUpdate = ?");
			try {
				psExist.setString(1, device);
				psExist.setString(2, product);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Executing statement failed on checking if product is in inventory call table");
			}
			ResultSet rs = psExist.executeQuery();
			if (rs.next())
				isExist = rs.getInt("COUNT(*)");
			if (isExist > 0)
				// if the product is already in the table - no need to create another row
				// return false - not created
				return false;

			PreparedStatement ps = dbConnector.prepareStatement(
					"INSERT INTO ekrut.inventory_calls (status, deviceName, productUpdate) " + "VALUES (?, ?, ?)");
			try {
				ps.setString(1, "OPEN");
				ps.setString(2, device);
				ps.setString(3, product);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing query on createInventoryCall failed");
		}
		System.out.println("Enter new inventory call successfully");
		return true;
	}

	/**
	 * 
	 * This method imports all orders from the database and returns a List of Order
	 * objects. It uses a PreparedStatement to query the "ekrut.orders" table and
	 * retrieves all rows from the table. For each row, it creates an Order object
	 * and populates it with the data from the current row. The Order object is then
	 * added to the List of orders. If any exception occurs, it will print the stack
	 * trace and a message indicating that the execution of statement failed.
	 * 
	 * @return A List of Order objects representing all the orders in the
	 *         "ekrut.orders" table.
	 */
	public static List<Order> importOrders() {
		List<Order> orders = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector.prepareStatement("SELECT * FROM ekrut.orders");
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				orders.add(new Order(rs.getString("deviceName"), rs.getInt("orderID"), rs.getFloat("orderPrice"),
						rs.getString("username"), rs.getString("day"), rs.getString("month"), rs.getString("year"),
						SupplyMethod.valueOf(rs.getString("supplyMethod")), rs.getString("orderProducts")));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement failed");
		}

		return orders;
	}

	/**
	 * 
	 * This method updates an existing order in the database.
	 * 
	 * @param order the order object containing the updated information to be stored
	 *              in the database
	 */

	public static void updateOrder(Order order) {
		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"INSERT INTO ekrut.orders (orderID,orderPrice,supplyMethod,username,deviceName,orderProducts,month,year,day) "
							+ "VALUES (?,?,?,?,?,?,?,?,?)");
			try {
				ps.setInt(1, order.getOrderID());
				ps.setFloat(2, order.getOrderPrice());
				;
				ps.setString(3, order.getSupplyMethod().toString());
				ps.setString(4, order.getCostumerUserName());
				ps.setString(5, order.getDeviceName());
				ps.setString(6, order.getOrderProducts());
				ps.setString(7, order.getMonth());
				ps.setString(8, order.getYear());
				ps.setString(9, order.getDay());
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("failed update order in DB");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed Statment updateOrder in DB");
		}
	}

	/**
	 * 
	 * This method updates the status and quantity of a list of products in a
	 * specific device in the database.
	 * 
	 * @param productsInDevice a list of products to update in the database
	 */
	public static void updateProductsInDevice(List<ProductInDevice> productsInDevice) {
		PreparedStatement ps, ps2;
		try {
			ps = dbConnector.prepareStatement(
					"UPDATE ekrut.product_in_device SET ekrut.product_in_device.status = ? WHERE ekrut.product_in_device.deviceName = ? and ekrut.product_in_device.productCode = ?");
			ps2 = dbConnector.prepareStatement(
					"UPDATE ekrut.product_in_device SET ekrut.product_in_device.quantity = ? WHERE ekrut.product_in_device.deviceName = ? and ekrut.product_in_device.productCode = ?");
			// Update the records in the database
			for (ProductInDevice product : productsInDevice) {
				ps.setString(1, product.getStatus().toString());
				ps.setString(2, product.getDevice());
				ps.setInt(3, product.getProductCode());
				ps.executeUpdate();

				ps2.setInt(1, product.getQuantity());
				ps2.setString(2, product.getDevice());
				ps2.setInt(3, product.getProductCode());
				ps2.executeUpdate();
			}
			System.out.println("Update products status succeeded");

		} catch (SQLException e) {
			System.out.println("Update products status failed");
		}

	}

	/**
	 * 
	 * Retrieves a list of InventoryCall objects from a database based on the given
	 * region and status.
	 * 
	 * @param regionAndStatus a list containing the region and status to filter the
	 *                        inventory calls by. The first element should be the
	 *                        region and the second element should be the status. If
	 *                        the status element is null, it will not be used in the
	 *                        query.
	 * @return an ArrayList of InventoryCall objects that match the given region and
	 *         status.
	 */

	public static ArrayList<InventoryCall> getInventoryCallsByRegionAndStatus(ArrayList<String> regionAndStatus) {
		PreparedStatement ps;
		ArrayList<InventoryCall> calls = new ArrayList<>();

		try {
			String query = "SELECT inventory_calls.* FROM inventory_calls "
					+ "JOIN devices ON inventory_calls.deviceName = devices.deviceName " + "WHERE devices.region = ?";
			if (regionAndStatus.get(1) != null) {
				query += " AND inventory_calls.status = ?";
			}
			ps = dbConnector.prepareStatement(query);
			ps.setString(1, regionAndStatus.get(0));
			if (regionAndStatus.get(1) != null) {
				ps.setString(2, regionAndStatus.get(1));
			}
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				calls.add(new InventoryCall(res.getInt(1), CallStatus.valueOf(res.getString(2)), res.getString(3),
						res.getString(4)));
			}
			System.out.println("Import inventory calls by region succeeded");
		} catch (SQLException e) {
			System.out.println("Import inventory calls by region failed");
		}
		return calls;
	}

	/**
	 * salesPatternToDB-a method that will save a salesPattern in the DB
	 * 
	 * @param SalesPattern sp, sp is a sale pattern
	 */

	public static void salesPatternToDB(SalesPattern sp) {
		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"INSERT INTO ekrut.sales_patterns (patternID, discountType, startDay,endDay,startHour,endHour) "
							+ "VALUES (?, ?, ?, ?, ?, ?)");
			try {
				ps.setInt(1, sp.getPatternID());
				ps.setString(2, sp.getDiscountType());
				ps.setString(3, sp.getStartDay());
				ps.setString(4, sp.getEndDay());
				ps.setString(5, sp.getStartHour());
				ps.setString(6, sp.getEndHour());

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing query on createSalesPattern failed");
		}
		System.out.println("Enter new SalesPattern successfully");
	}

	/**
	 * 
	 * Retrieves a list of SalesPattern objects from the database.
	 * 
	 * @return an ArrayList of SalesPattern objects that are in the sales_patterns
	 *         table in the database.
	 */
	public static ArrayList<SalesPattern> importSalesPattern() {
		ArrayList<SalesPattern> salespatterns = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector.prepareStatement("SELECT * FROM ekrut.sales_patterns");
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				salespatterns.add(
						new SalesPattern(rs.getInt("patternID"), rs.getString("discountType"), rs.getString("startDay"),
								rs.getString("endDay"), rs.getString("startHour"), rs.getString("endHour")));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement failed");
		}

		return salespatterns;
	}

	/**
	 * 
	 * Retrieves a list of Sale objects from the database that have an 'ACTIVATE'
	 * status.
	 * 
	 * @return an ArrayList of Sale objects that are in the sales table in the
	 *         database and have an 'ACTIVATE' status.
	 */

	public static ArrayList<Sale> importSales() {
		ArrayList<Sale> sale = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"SELECT ekrut.sales_patterns.*, ekrut.sales.region, ekrut.sales.saleID,ekrut.sales.status FROM ekrut.sales,ekrut.sales_patterns WHERE ekrut.sales.patternID=ekrut.sales_patterns.patternID AND ekrut.sales.status=?");
			ps.setString(1, "ACTIVATE");
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				sale.add(new Sale(rs.getInt("patternID"), rs.getString("discountType"), rs.getString("startDay"),
						rs.getString("endDay"), rs.getString("startHour"), rs.getString("endHour"),
						Region.valueOf(rs.getString("region")), rs.getInt("saleID"),
						SaleStatus.valueOf(rs.getString("status"))));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement failed");
		}

		return sale;
	}

	public static void updateSaleInDB(Sale sale) {
		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"INSERT INTO ekrut.sales(region, saleID, patternID,status) " + "VALUES (?, ?, ?, ?)");
			try {
				ps.setString(1, sale.getRegion().toString());
				ps.setInt(2, sale.getSaleID());
				ps.setInt(3, sale.getPatternID());
				ps.setString(4, sale.getStatus().toString());

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing query on createSales failed");
		}
		System.out.println("Enter new Sales successfully");
	}

	/**
	 * 
	 * Inserts or updates a Sale object in the database.
	 * 
	 * @param sale the Sale object to be inserted or updated in the database.
	 */
	public static void savePickUpOrderInDB(int orderID) {
		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("INSERT INTO ekrut.takeaway(orderID,collected) " + "VALUES (?,?)");
			try {
				ps.setInt(1, orderID);
				ps.setBoolean(2, false);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing query on update msg table failed");
		}
		System.out.println("Enter new msg to system_message successfully");
	}

	public static ArrayList<Integer> importPickUpOrders(String username) {
		ArrayList<Integer> pickUpOrders = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"SELECT ekrut.orders.orderID FROM ekrut.orders,ekrut.takeaway WHERE ekrut.orders.username = ? AND ekrut.orders.orderID=ekrut.takeaway.orderID AND ekrut.takeaway.collected = ?");
			try {
				ps.setString(1, username);
				ps.setBoolean(2, false);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				pickUpOrders.add(rs.getInt("orderID"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement failed");
		}

		return pickUpOrders;
	}

	/**
	 * 
	 * Retrieves a list of order IDs from the orders table in the database for a
	 * given username where the order is a pickup order and not yet collected.
	 * 
	 * @param username the username to filter the orders by.
	 * @return an ArrayList of order IDs that match the given username and are
	 *         pickup orders that haven't been collected.
	 */

	public static void updatePickUPasCollected(Integer orderToUpdate) {
		PreparedStatement ps;
		try {
			ps = dbConnector.prepareStatement("UPDATE ekrut.takeaway SET collected = ? WHERE orderID = ?");

			// Update the records in the database
			ps.setBoolean(1, true);
			ps.setInt(2, orderToUpdate);
			ps.executeUpdate();
			System.out.println("Update order status succeeded");

		} catch (SQLException e) {
			System.out.println("Update order status failed");
		}

	}

	/**
	 * 
	 * Inserts a delivery object into the delivery table in the database
	 * 
	 * @param delivery the Delivery object to be inserted in the database.
	 */
	public static void saveDeliveryInOrders(Delivery delivery) {
		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"INSERT INTO ekrut.delivery(costumerAdress,deliveryStatus,orderID,region) " + "VALUES (?,?,?,?)");
			try {
				ps.setString(1, delivery.getCostumerAdress());
				ps.setString(2, delivery.getStatus().toString());
				ps.setInt(3, delivery.getOrderID());
				ps.setString(4, delivery.getRegion().toString());

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing query on delivery table is failed");
		}
		System.out.println("Enter new delivery to delivery table successfully");

	}

	/**
	 * 
	 * Updates the status of a Sale object in the sales table in the database.
	 * 
	 * @param sale the Sale object to be updated in the database.
	 */
	public static void updateSaleStatusInDB(Sale sale) {
		try {
			PreparedStatement ps = dbConnector.prepareStatement("UPDATE ekrut.sales SET status = ? WHERE saleID = ?");
			try {
				ps.setString(1, "ACTIVATE");
				ps.setInt(2, sale.getSaleID());

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing query on UPDATE Sales failed");
		}
		System.out.println("UPDATE Sales successfully");
	}

	/**
	 * 
	 * Retrieves the order data for a specific user from the ekrut.orders table in
	 * the database.
	 * 
	 * @param username The username of the user whose order data is to be retrieved.
	 * @return An ArrayList of Order objects, containing the order data for the
	 *         specified user.
	 */
	public static ArrayList<Order> getOrdersDataOfUSER(String username) {
		ArrayList<Order> orders = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector.prepareStatement("SELECT * FROM ekrut.orders WHERE username = ? ");
			try {
				ps.setString(1, username);
			} catch (Exception e) {
				System.out.println("Executing statement failed!");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				orders.add(new Order(rs.getString("deviceName"), rs.getInt("orderID"), rs.getFloat("orderPrice"),
						username, rs.getString("day"), rs.getString("month"), rs.getString("year"),
						SupplyMethod.valueOf(rs.getString("supplyMethod")), rs.getString("orderProducts")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Import orders data from orders table has failed");

		}
		return orders;
	}

	/**
	 * 
	 * Retrieves the customer data for a specific user from the ekrut.costumers
	 * table in the database.
	 * 
	 * @param username The username of the customer whose data is to be retrieved.
	 * @return A Costumer object, containing the data for the specified user.
	 */
	public static Costumer importCustomerDataToUpdate(String username) {
		Costumer c = null;
		try {
			PreparedStatement ps = dbConnector.prepareStatement("SELECT * FROM ekrut.costumers WHERE username = ?");
			try {
				ps.setString(1, username);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Set statement failed on importCustomerDataToUpdate");
			}
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				c = new Costumer(username, rs.getString("creditCard"), rs.getInt("subscriberID"),
						CostumerStatus.valueOf(rs.getString("status")));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Executing statement failed on importCustomerDataToUpdate");
		}
		return c;
	}

	/**
	 * 
	 * Updates the status of a specific sale in the ekrut.sales table in the
	 * database to "DONE".
	 * 
	 * @param sale The Sale object whose status is to be updated.
	 */
	public static void updateSaleStatusdoneInDB(Sale sale) {
		try {
			PreparedStatement ps = dbConnector.prepareStatement("UPDATE ekrut.sales SET status = ? WHERE saleID = ?");
			try {
				ps.setString(1, "DONE");
				ps.setInt(2, sale.getSaleID());

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing query on UPDATE Sales failed");
		}
		System.out.println("UPDATE Sales successfully");
	}

	/**
	 * 
	 * Retrieves a list of deliveries from the ekrut.delivery table in the database
	 * based on the specified area and status.
	 * 
	 * @param data ArrayList containing two elements, the area and status of the
	 *             deliveries to be retrieved.
	 * @return An ArrayList of Delivery objects, containing the data of the
	 *         deliveries that match the specified area and status.
	 */
	public static ArrayList<Delivery> getDeliveriesByArea(ArrayList<String> data) {
		ArrayList<Delivery> deliveries = new ArrayList<>();
		String area = data.get(0), status = data.get(1);
		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("SELECT * From ekrut.delivery WHERE region = ?" + "AND deliveryStatus = ?");
			try {
				ps.setString(1, area);
				ps.setString(2, status);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Set query failed on getDeliveriesByArea");
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				deliveries.add(new Delivery(rs.getString("costumerAdress"),
						DeliveryStatus.valueOf(rs.getString("deliveryStatus")), rs.getInt("orderID"),
						Region.valueOf(rs.getString("region"))));
			}
			System.out.println("Deliveries importded successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Execute query failed on getDeliveriesByArea");
		}
		return deliveries;
	}

	/**
	 * 
	 * Updates the status of multiple deliveries in the ekrut.delivery table in the
	 * database.
	 * 
	 * @param deliveryToUpdate An ArrayList of Delivery objects, containing the
	 *                         updated delivery statuses.
	 */
	public static void updateDeliveryStatus(ArrayList<Delivery> deliveryToUpdate) {
		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("UPDATE ekrut.delivery SET deliveryStatus = ? WHERE orderID = ? ");
			for (Delivery delivery : deliveryToUpdate) {
				ps.setString(1, delivery.getStatus().toString());
				ps.setInt(2, delivery.getOrderID());
				ps.executeUpdate();
			}
			System.out.println("Update delivery status suceed");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Execute query failed on updateDeliveryStatus");
		}
	}

	/**
	 * 
	 * Updates the arrival time of multiple deliveries in the ekrut.delivery table
	 * in the database.
	 * 
	 * @param deliveryToUpdate An ArrayList of Delivery objects, containing the
	 *                         updated arrival time of deliveries.
	 */

	public static void updateDeliveryArrivalTime(ArrayList<Delivery> deliveryToUpdate) {
		try {
			PreparedStatement ps = dbConnector.prepareStatement(
					"UPDATE ekrut.delivery SET supplyTimedate = ?, supplyTimehour = ? WHERE orderID = ?");
			for (Delivery delivery : deliveryToUpdate) {
				ps.setString(1, delivery.getArrivalDate());
				ps.setString(2, delivery.getArrivalHour());
				ps.setInt(3, delivery.getOrderID());
				ps.executeUpdate();
			}
			System.out.println("Update delivery status suceed");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Execute query failed on updateDeliveryStatus");
		}
	}

	/**
	 * 
	 * Updates the quantity of a product in a specific device, and closes a call in
	 * the ekrut.inventory_calls table in the database.
	 * 
	 * @param data An ArrayList containing four elements: the device name, product
	 *             name, new quantity and the call ID.
	 */
	public static void UpdateProductQuantityAndCloseCall(ArrayList<String> data) {
		String deviceName = data.get(0), productName = data.get(1), newQuantity = data.get(2), callID = data.get(3);
		try {
			PreparedStatement updatePs = dbConnector.prepareStatement("UPDATE ekrut.product_in_device SET "
					+ "status = 'AVAILABLE', quantity = ? " + "WHERE deviceName = ? AND productCode "
					+ "IN (SELECT productCode FROM products WHERE productName = ?)");
			try {
				updatePs.setInt(1, Integer.valueOf(newQuantity));
				updatePs.setString(2, deviceName);
				updatePs.setString(3, productName);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Query to update quantity has Failed");
			}
			updatePs.executeUpdate();

			PreparedStatement ps = dbConnector
					.prepareStatement("UPDATE ekrut.inventory_calls SET status = 'DONE' WHERE callID = ?");
			try {
				ps.setInt(1, Integer.valueOf(callID));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Query to close call has Failed");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing query on UPDATE Sales failed");
		}
		System.out.println("Update Product Quantity And Close Call success!");
	}

	/**
	 * 
	 * Retrieves a list of deliveries from the ekrut.delivery table in the database
	 * for a specific customer based on the specified status of the deliveries.
	 * 
	 * @param username The username of the customer whose deliveries are to be
	 *                 retrieved.
	 * @return An ArrayList of Delivery objects, containing the data of the
	 *         deliveries that match the customer's username and whose status is
	 *         either NOTAPPROVED or APPROVED.
	 */
	public static ArrayList<Delivery> getDeliveriesByCustomer(String username) {
		ArrayList<Delivery> deliveries = new ArrayList<>();
		try {
			PreparedStatement ps = dbConnector
					.prepareStatement("SELECT * FROM ekrut.delivery d " + "JOIN ekrut.orders o ON d.orderID=o.orderID "
							+ "WHERE o.username = ? AND d.deliveryStatus IN('NOTAPPROVED', 'APPROVED')");

			try {
				ps.setString(1, username);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Set query failed on getDeliveriesByCustomer");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				deliveries.add(new Delivery(rs.getString("costumerAdress"),
						DeliveryStatus.valueOf(rs.getString("deliveryStatus")), rs.getInt("orderID"),
						Region.valueOf(rs.getString("region"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Execute query failed on getDeliveriesByCustomer");
		}
		return deliveries;
	}

	/**
	 * 
	 * Retrieves the username of an area manager from the ekrut.users table in the
	 * database based on the specified region.
	 * 
	 * @param region The region for which the area manager's username is to be
	 *               retrieved.
	 * @return A string containing the username of the area manager for the
	 *         specified region
	 */
	public static String getAreaManagerUsername(String region) {
		PreparedStatement ps;
		String areaManagerUsername = null;
		try {
			ps = dbConnector
					.prepareStatement("SELECT username FROM ekrut.users WHERE region = ? AND Role = 'AreaManager'");
			ps.setString(1, region);
			ResultSet res = ps.executeQuery();
			if (res.next()) {
				areaManagerUsername = res.getString("username");
			}
			System.out.println("Import Area Manager Usernames by region succeeded");
		} catch (SQLException e) {
			System.out.println("Import Area Manager Usernames by region failed");
		}
		return areaManagerUsername;
	}

	/**
	 * 
	 * Retrieves the customer data from the ekrut.costumers table in the database
	 * based on the specified order ID.
	 * 
	 * @param orderID The order ID of the customer whose data is to be retrieved.
	 * @return A Costumer object, containing the data for the customer associated
	 *         with the specified order ID.
	 */
	public static Costumer getCustomerByOrderID(int orderID) {
		PreparedStatement ps;
		Costumer c = null;
		try {
			ps = dbConnector.prepareStatement("SELECT * FROM ekrut.costumers WHERE username "
					+ "IN(SELECT username FROM ekrut.orders WHERE orderID = ?)");
			try {
				ps.setInt(1, orderID);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Set query failed on getCustomerByOrderID");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				c = new Costumer(rs.getString("username"), rs.getString("creditCard"), rs.getInt("subscriberID"),
						CostumerStatus.valueOf(rs.getString("status")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exceute query failed on getCustomerByOrderID");
		}
		return c;
	}

	/**
	 * 
	 * Update the count of a product that is under the threshold level in the
	 * ekrut.products_under_threshold table in the database. The product is
	 * identified by its deviceName, productCode, productName, month and year If the
	 * product is not exist in the table it will be inserted with count 1 If the
	 * product is exist it will update the count by adding 1 to the current count
	 * 
	 * @param product A ProductInDevice object containing the product's information.
	 */

	public static void updateProductInProductsUnderThreshold(ProductInDevice product) {
		// String currentMonthNumber = String.valueOf(LocalDate.now().getMonthValue());
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
		String formattedMonth = date.format(formatter);
		String currentYear = String.valueOf(LocalDate.now().getYear());
		PreparedStatement ps;
		try {
			ps = dbConnector.prepareStatement(
					"INSERT INTO ekrut.products_under_threshold (deviceName, prCode, prName, month, year, count) "
							+ "VALUES (?,?,?,?,?,1) " + "ON DUPLICATE KEY UPDATE count = count + 1");
			ps.setString(1, product.getDevice());
			ps.setInt(2, product.getProductCode());
			ps.setString(3, product.getProductName());
			ps.setString(4, formattedMonth);
			ps.setString(5, currentYear);
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 
	 * This method imports users from a "register_service" schema in a MySQL
	 * database and inserts them into the "ekrut" schema in the same database. It
	 * first establishes a connection to the "register_service" schema using the
	 * JDBC driver and a set of credentials (username, password) and then retrieves
	 * the data from the "users" table. Then, it establishes a connection to the
	 * "ekrut" schema and uses a prepared statement to insert the data into the
	 * "users" table in the "ekrut" schema. The method uses a "WHERE NOT EXISTS"
	 * clause to ensure that duplicate users are not inserted. The method also sets
	 * the value of the "isLoggedIn" field to 0 for all imported users. If the
	 * import is successful, it prints a message "Users imported succsesfully!" to
	 * the console, otherwise it prints "Users import failed!"
	 * 
	 * @throws SQLException if there is an issue with the SQL query or database
	 *                      connection.
	 */

	public static void importUsers() {
		// Connect to the "register_service" schema
		try {
			Connection sourceConnection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/register_service?serverTimezone=IST&useSSL=false", "root", "Aa123456");
			Statement sourceStatement = sourceConnection.createStatement();
			ResultSet sourceResult = sourceStatement.executeQuery("SELECT * FROM users");

			// Connect to the "ekrut" schema
			PreparedStatement targetStatement = dbConnector.prepareStatement(
					"INSERT INTO ekrut.users (username, password, firstName, lastName, email, phoneNumber, isLoggedIn, id, role, region)"
							+ " SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM ekrut.users WHERE username = ?) ");
			while (sourceResult.next()) {
				targetStatement.setString(1, sourceResult.getString("username"));
				targetStatement.setString(2, sourceResult.getString("password"));
				targetStatement.setString(3, sourceResult.getString("firstName"));
				targetStatement.setString(4, sourceResult.getString("lastName"));
				targetStatement.setString(5, sourceResult.getString("email"));
				targetStatement.setString(6, sourceResult.getString("phoneNumber"));
				targetStatement.setInt(7, 0);
				targetStatement.setString(8, sourceResult.getString("id"));
				targetStatement.setString(9, sourceResult.getString("role"));
				targetStatement.setString(10, sourceResult.getString("region"));
				targetStatement.setString(11, sourceResult.getString("username"));
				targetStatement.executeUpdate();
			}
			sourceConnection.close();
			System.out.println("Users imported succsesfully!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Users import failed!");
		}
	}

}
