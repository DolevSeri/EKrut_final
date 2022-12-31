package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Costumer;
import entities.Device;
import entities.OrderReport;
import entities.Order;
import entities.ProductInDevice;
import entities.User;
import enums.Configuration;
import enums.CostumerStatus;
import enums.Devices;
import enums.ProductStatus;
import enums.Region;
import enums.Role;
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
						Role.valueOf(result.getString("role")), Region.valueOf(result.getString("region")),
						Configuration.valueOf(result.getString("configuration")));

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
	 * @author ron
	 */
	public static void UserLogoutAndUpdateDB(User user) throws SQLException {
		PreparedStatement ps = dbConnector.prepareStatement("UPDATE ekrut.users SET isLoggedIn = ? WHERE username = ?");
		System.out.println("Update succsed");
		try {
			ps.setBoolean(1, false);
			ps.setString(2, user.getUsername());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Executing statement-Updating login status on users table had failed!");
		}
	}

//	public static String viewUser(String ID) {
//		String sub = "";
//		// Subscriber sub = new Subscriber(null, null, null, null, null, null, null);
//		PreparedStatement stmt;
//		System.err.println(ID);
//		try {
//			stmt = dbConnector.prepareStatement("SELECT * FROM ekrutdb.subscriber WHERE ID=\"" + ID + "\";");
//			ResultSet res = stmt.executeQuery();
//			// System.out.println(res.getNString(0));
//			while (res.next()) {
//				sub += (res.getString(1)) + " ";
//				sub += (res.getString(2)) + " ";
//				sub += (res.getString(3)) + " ";
//				sub += (res.getString(4) + " ");
//				sub += (res.getString(5)) + " ";
//				sub += (res.getString(6) + " ");
//				sub += (res.getString(7));
//				System.out.println("Import data suceeded");
//
//			}
//		} catch (SQLException e) {
//			System.out.println("Import data fail!!!");
//
//		}
//		System.out.println("!" + sub.toString());
//
//		return sub;
//
//	}
//
//	public static void updateSubscriberTable(String[] updatedSubscriber) {
//		PreparedStatement ps = null;
//		try {
//			ps = dbConnector.prepareStatement(
//					"UPDATE ekrutdb.subscriber SET creditCardNumber = ?," + " subscriberNumber = ? WHERE ID = ?");
//		} catch (SQLException e1) {
//			System.out.println("Statement failure");
//		}
//		try {
//			System.out.println(updatedSubscriber[1] + " " + updatedSubscriber[2] + " " + updatedSubscriber[3]);
//			ps.setString(1, updatedSubscriber[2]);
//
//			ps.setString(2, updatedSubscriber[3]);
//			System.out.println("2131");
//			ps.setString(3, updatedSubscriber[1]);
//			System.out.println("!#@!#");
//			ps.executeUpdate();
//
//			System.out.println("Update data suceeded");
//		} catch (Exception e) {
//			System.out.println("Update data fail!!!");
//		}
//	}
//
//	public static void updateSubscriberTable(String messageFromClient) {
//		// TODO Auto-generated method stub
//
//	}

	public static OrderReport getOrdersReportData(ArrayList<String> reportDetails) {
		String area = reportDetails.get(0), year = reportDetails.get(1), month = reportDetails.get(2);
		String[] deviceList = null;
		String devices = null, mostSellingDevice = null;
		HashMap<String, Integer> mapOfDevices = new HashMap<String, Integer>();
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
				numOfTotalOrders = rs.getInt("numOfTotalOrders");
				numOfPickUpOrders = rs.getInt("totalPickUp");
				mostSellingDevice = rs.getString("mostSelling");
				
			} else
				return null;
		} catch (SQLException e) {
			System.out.println("Statement for getting Monthly Orders Report has failed!");
		}
		System.out.println("Succefully imported Monthly Orders Report Data");

		deviceList = devices.split(",");
		for (int i = 0; i < (deviceList.length); i = i + 2) {
			mapOfDevices.put(deviceList[i], (int) Integer.valueOf(deviceList[i + 1]));
		}
		
		OrderReport report = new OrderReport(mapOfDevices, numOfTotalOrders, (float) numOfTotalOrders / 30,
				numOfPickUpOrders, area, month, year, mostSellingDevice);
	
		return report;
	}

	public static ArrayList<Device> getAllDevicesByArea(String region) {
		PreparedStatement ps;
		ArrayList<Device> devices = new ArrayList<>();

		try {
			ps = dbConnector.prepareStatement("SELECT * FROM ekrut.devices WHERE region=\"" + region + "\";");
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				devices.add(new Device(res.getInt(1), Region.valueOf(res.getString(2)), res.getString(3)));
			}
			System.out.println("Import devices by area data suceeded");
		} catch (Exception e) {
			System.out.println("Import devices by area data failed");
		}
		return devices;
	}

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
			System.out.println("Update threshold suceeded");

		} catch (SQLException e) {
			System.out.println("Update threshold failed");
		}

	}

	/**
	 * Creates a monthly orders report and stores it in the database. The report
	 * contains data on the total number of orders, the number of orders that were
	 * picked up, the most popular device in the area, and a list of all devices in
	 * the area and the number of orders for each device.
	 * 
	 * @param reportData a list containing the month, year, and area for which to
	 *        create the report
	 */

	public static void createMonthlyOrdersReport(ArrayList<String> reportData) {

		String month = reportData.get(0), year = reportData.get(1), area = reportData.get(2);
		ArrayList<Device> areaDevices = getAllDevicesByArea(area);
		String mostSelling = null, devices = "";
		Integer max = 0, num;
		int totalOrders = 0, totalPickUpOrders = 0;
		HashMap<String, Integer> deviceNumOfOrders = new HashMap<>();
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
				} else {
					num = deviceNumOfOrders.get(order.getDeviceName()) + 1;
					deviceNumOfOrders.replace(order.getDeviceName(), num);
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
		}
		devices.replaceFirst(",", "");
		try {
			PreparedStatement ps = dbConnector.prepareStatement("INSERT INTO ekrut.orderReport "
					+ "(month, year, area, numOfTotalOrders, totalPickUp, mostSelling, devices) VALUES(?, ?, ?, ?, ?, ?, ?)");
			try {
				ps.setString(1, month);
				ps.setString(2, year);
				ps.setString(3, area);
				ps.setInt(4, totalOrders);
				ps.setInt(5, totalPickUpOrders);
				ps.setString(6, mostSelling);
				ps.setString(7, devices);
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Enter data to ordersReport on DB failed");
				System.out.println("Error on createMonthlyOrderReport");
			}
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
				orders.add(new Order(deviceName, rs.getInt(1), rs.getFloat(3), rs.getString(5), rs.getString(8),
						rs.getString(9), rs.getString(10), SupplyMethod.valueOf(rs.getString(4)), rs.getString(7)));
			}
		} catch (Exception e) {
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
		HashMap<String, Integer> producsUnderThreshold = getProductsUnderThresholdCount(reportData);

		for (String str : producsUnderThreshold.keySet()) {
			num = producsUnderThreshold.get(str);
			itemsList += "," + str + "," + num.toString();
			if (max < num) {
				max = producsUnderThreshold.get(str);
				itemUnderThres = str;

			}
		}
		itemsList.replaceFirst(",", "");

		try {
			PreparedStatement ps = dbConnector.prepareStatement("INSERT INTO ekrut.inventoryreport "
					+ "(month, year, deviceName, products, itemUnderThres) VALUES(?, ?, ?, ?, ?)");
			try {
				ps.setString(1, month);
				ps.setString(2, year);
				ps.setString(3, device);
				ps.setString(4, itemsList);
				ps.setString(5, itemUnderThres);

			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Enter data to ordersReport on DB failed");
				System.out.println("Error on createMonthlyInventoryReport");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on execute statement");
		}

	}

	/*
	 * Retrieves a count of the number of products that were below a certain
	 * threshold for a given month and year, for a specific device.
	 * 
	 * @param reportData an ArrayList of Strings containing the month, year, and
	 * device for which the report is to be generated, in that order
	 * 
	 * @return a HashMap mapping product codes to their respective count of being
	 * below the threshold
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
				productsThres.put(rs.getString("prCode"), rs.getInt("count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exucute statement failed");
			System.out.println("Error on getProductsUnderThresholdCount");
		}
		return productsThres;
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
							+ "WHERE ekrut.products.productCode = ekrut.product_in_device.productCode "
							+ "and ekrut.product_in_device.deviceName = ?");

			try {
				ps.setString(1, deviceName);

			} catch (Exception e) {
				System.out.println("Executing statement failed!");
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				products.add(new ProductInDevice(rs.getInt("productCode"), rs.getString("productName"),
						rs.getDouble("price"), rs.getString("imagePath"), rs.getInt("quantity"),
						ProductStatus.valueOf(rs.getString("status")), rs.getString("deviceName")));
			}
			return products;

		} catch (Exception e) {
			System.out.println("Import orders data from orders table has failed!");
			System.out.println("Failed at getOrdersDataOfDevice method");
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
						Configuration.valueOf(rs.getString("configuration")), rs.getString("creditCard"),
						rs.getString("subscriberID"), CostumerStatus.valueOf(rs.getString("status")),
						rs.getString("deviceName"));
				return costumer;
			}
		} catch (Exception e) {
			System.out.println("Import orders data from orders table has failed!");
			System.out.println("Failed at getOrdersDataOfDevice method");
		}
		return null;
	}

	/*
	 * Creates a report on the activity level of customers in a given area during a
	 * specific month and year. The activity level is defined as follows: low
	 * activity: 0 to 5 orders medium activity: 6 to 12 orders high activity: 13 to
	 * 20 orders very high activity: more than 20 orders
	 * 
	 * @param reportData an ArrayList of Strings containing the month, year, and
	 * area for which the report is to be generated, in that order
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
			if (i >= 0 && i <= 5)
				costumersActivity.replace("lowActivity", costumersActivity.get("lowActivity"),
						costumersActivity.get("lowActivity") + 1);
			if (i > 5 && i <= 12)
				costumersActivity.replace("mediumActivity", costumersActivity.get("mediumActivity"),
						costumersActivity.get("mediumActivity") + 1);
			if (i > 12 && i <= 20)
				costumersActivity.replace("highActivity", costumersActivity.get("highActivity"),
						costumersActivity.get("highActivity") + 1);
			else
				costumersActivity.replace("veryHigh", costumersActivity.get("veryHigh"),
						costumersActivity.get("veryHigh") + 1);
		}

		try {
			PreparedStatement ps = dbConnector.prepareStatement("INSERT INTO ekrut.costumer_report "
					+ "(region, month, year, lowActivityCount, "
					+ "mediumActivityCount, highActivityCount, veryHighActivityCount) VALUES (?, ?, ?, ?, ?, ?, ?)");
			try {
				ps.setString(1, area);
				ps.setString(2, month);
				ps.setString(3, year);
				ps.setInt(4, costumersActivity.get("lowActivity"));
				ps.setInt(5, costumersActivity.get("mediumActivity"));
				ps.setInt(6, costumersActivity.get("highActivity"));
				ps.setInt(7, costumersActivity.get("veryHighActivity"));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Enter costumer report details to DB failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement on createMonthlyCostumersReport failed");
		}

	}

	/*
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
					"SELECT COUNT(*) ekrut.orders " + "WHERE username = ? AND month = ? AND year = ?");
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

	/*
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
							+ "u.firstName, u.lastName, u.email, u.phoneNumber, u.isLoggedIn, u.id, "
							+ "u.role, u.region, u.configuration " + "FROM ekrut.costumers c "
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
						Configuration.valueOf(rs.getString("configuration")), rs.getString("creditCard"),
						rs.getString("subscriberID"), CostumerStatus.valueOf(rs.getString("status")),
						rs.getString("deviceName")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Executing statement failed on getCostumersByArea");
		}

		return areaCostumers;
	}

}
