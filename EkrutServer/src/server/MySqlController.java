package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Device;
import entities.MonthlyOrderReport;
import entities.User;
import enums.Region;
import enums.Role;
import javafx.collections.ObservableList;

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

	public static String viewUser(String ID) {
		String sub = "";
		// Subscriber sub = new Subscriber(null, null, null, null, null, null, null);
		PreparedStatement stmt;
		System.err.println(ID);
		try {
			stmt = dbConnector.prepareStatement("SELECT * FROM ekrutdb.subscriber WHERE ID=\"" + ID + "\";");
			ResultSet res = stmt.executeQuery();
			// System.out.println(res.getNString(0));
			while (res.next()) {
				sub += (res.getString(1)) + " ";
				sub += (res.getString(2)) + " ";
				sub += (res.getString(3)) + " ";
				sub += (res.getString(4) + " ");
				sub += (res.getString(5)) + " ";
				sub += (res.getString(6) + " ");
				sub += (res.getString(7));
				System.out.println("Import data suceeded");

			}
		} catch (SQLException e) {
			System.out.println("Import data fail!!!");

		}
		System.out.println("!" + sub.toString());

		return sub;

	}

	public static void updateSubscriberTable(String[] updatedSubscriber) {
		PreparedStatement ps = null;
		try {
			ps = dbConnector.prepareStatement(
					"UPDATE ekrutdb.subscriber SET creditCardNumber = ?," + " subscriberNumber = ? WHERE ID = ?");
		} catch (SQLException e1) {
			System.out.println("Statement failure");
		}
		try {
			System.out.println(updatedSubscriber[1] + " " + updatedSubscriber[2] + " " + updatedSubscriber[3]);
			ps.setString(1, updatedSubscriber[2]);

			ps.setString(2, updatedSubscriber[3]);
			System.out.println("2131");
			ps.setString(3, updatedSubscriber[1]);
			System.out.println("!#@!#");
			ps.executeUpdate();

			System.out.println("Update data suceeded");
		} catch (Exception e) {
			System.out.println("Update data fail!!!");
		}
	}

	public static void updateSubscriberTable(String messageFromClient) {
		// TODO Auto-generated method stub

	}

	public static MonthlyOrderReport getOrdersReportData(ArrayList<String> reportDetails) {
		String device = reportDetails.get(0), year = reportDetails.get(1), month = reportDetails.get(2);
		String[] itemsList = null;
		String products = null, mostWantedProduct = null;
		HashMap<String, Integer> mapOfItems = new HashMap<String, Integer>();
		int numOfTotalOrders = 0, numOfOrdersThatCanceled = 0;
		PreparedStatement ps = null;
		try {
			ps = dbConnector
					.prepareStatement("SELECT * FROM ekrut.orders_report WHERE month = ? AND year = ? AND store = ?");
			ps.setString(1, month);
			ps.setString(2, year);
			ps.setString(3, device);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				products = rs.getString("products");
				numOfTotalOrders = rs.getInt("numOfTotalOrders");
				// numOfOrdersThatCanceled = rs.getInt("numOfOrdersThatCanceled");
				mostWantedProduct = rs.getString("mostWantedItemName");
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
				mostWantedProduct);
	}

	public static ArrayList<Device> getAllDevicesByArea(String area) {
		PreparedStatement ps;
		ArrayList<Device> devices = new ArrayList<>();

		try {
			ps = dbConnector.prepareStatement("SELECT * FROM ekrut.devices WHERE region=\"" + area + "\";");
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				devices.add(new Device(res.getString(1), res.getInt(2), Region.valueOf(res.getString(3)),
						res.getString(4)));
			}
			System.out.println("Import data suceeded");
		} catch (Exception e) {
			System.out.println("Import data failed!");
		}
		return devices;
	}

	public static void updateDeviceThreshold(ArrayList<Device> devicesToUpdate) {
		PreparedStatement ps;
		try {
			ps = dbConnector.prepareStatement("UPDATE ekrut.devices SET threshold = ? WHERE deviceID = ?");

			// Update the records in the database
			for (Device device : devicesToUpdate) {
				ps.setInt(1, device.getThreshold());
				ps.setString(2, device.getDeviceID());
		        ps.executeUpdate();
			}
			System.out.println("Update threshold suceeded!");

		} catch (SQLException e) {
			System.out.println("Update threshold failed!");
		}
		
	}
}
