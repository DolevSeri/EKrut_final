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
import entities.MonthlyOrderReport;
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

	public static MonthlyOrderReport getOrdersReportData(ArrayList<String> reportDetails) {
		String device = reportDetails.get(0), year = reportDetails.get(1), month = reportDetails.get(2);
		String[] itemsList = null;
		String products = null, mostSellingDevice = null;
		HashMap<String, Integer> mapOfItems = new HashMap<String, Integer>();
		int numOfTotalOrders = 0, numOfPickUpOrders = 0;
		PreparedStatement ps = null;
		try {
			ps = dbConnector.prepareStatement(
					"SELECT * FROM ekrut.orders_report " + "WHERE month = ? AND year = ? AND area = ?");
			ps.setString(1, month);
			ps.setString(2, year);
			ps.setString(3, device);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				products = rs.getString("products");
				numOfTotalOrders = rs.getInt("numOfTotalOrders");
				numOfPickUpOrders = rs.getInt("totalPickUp");
				mostSellingDevice = rs.getString("mostSelling");
			} else
				return null;
		} catch (SQLException e) {
			System.out.println("Statement for getting Monthly Orders Report has failed!");
		}
		System.out.println("Succefully imported Monthly Orders Report Data!");
		String date = month + "/" + year;
		itemsList = products.split(",");
		for (int i = 0; i < (itemsList.length); i = i + 2) {
			mapOfItems.put(itemsList[i], (int) Integer.valueOf(itemsList[i + 1]));
		}

		return new MonthlyOrderReport(mapOfItems, numOfTotalOrders, (float) numOfTotalOrders / 30, device, date,
				mostSellingDevice);
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
			System.out.println("Import data suceeded");
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
				ps.setString(0, month);
				ps.setString(1, year);
				ps.setString(2, area);
				ps.setInt(3, totalOrders);
				ps.setInt(4, totalPickUpOrders);
				ps.setString(5, mostSelling);
				ps.setString(6, devices);
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

	}

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
				ps.setString(0, month);
				ps.setString(1, year);
				ps.setString(2, device);
				ps.setString(3, itemsList);
				ps.setString(4, itemUnderThres);

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

	public static HashMap<String, Integer> getProductsUnderThresholdCount(ArrayList<String> reportData) {
		String month = reportData.get(0), year = reportData.get(1), device = reportData.get(2);
		HashMap<String, Integer> productsThres = new HashMap<>();
		try {
			PreparedStatement ps = dbConnector.prepareStatement("SELECT * FROM ekrut.products_under_threshold WHERE"
					+ " deviceName = ? AND month = ? AND year = ? ");
			try {
				ps.setString(0, device);
				ps.setString(1, month);
				ps.setString(2, year);
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
			PreparedStatement ps = dbConnector.prepareStatement(
					"SELECT ekrut.users.*,ekrut.costumers.creditCard,ekrut.costumers.subscriberID,ekrut.costumers.status,ekrut.costumers.deviceName FROM ekrut.users,ekrut.costumers WHERE ekrut.users.id = ? AND ekrut.users.username=ekrut.costumers.username");
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

}
