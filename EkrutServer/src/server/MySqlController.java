package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
		// "jdbc:mysql://localhost/ekrutdb?serverTimezone=IST"
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

	public static String viewUser(String ID) {
		String sub = "";
		//Subscriber sub = new Subscriber(null, null, null, null, null, null, null);
		PreparedStatement stmt;
		System.err.println(ID);
		try {
			stmt = dbConnector.prepareStatement("SELECT * FROM ekrutdb.subscriber WHERE ID=\"" + ID +"\";");
			ResultSet res = stmt.executeQuery();
			//System.out.println(res.getNString(0));
			while (res.next()) {
			sub+=(res.getString(1))+" ";
			sub+=(res.getString(2))+" ";
			sub+=(res.getString(3))+" ";
			sub+=(res.getString(4)+" ");
			sub+=(res.getString(5))+" ";
			sub+=(res.getString(6)+" ");
			sub+=(res.getString(7));
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
					"UPDATE ekrutdb.subscriber SET creditCardNumber = ?,"
					+ " subscriberNumber = ? WHERE ID = ?");
		} catch (SQLException e1) {
			System.out.println("Statement failure");
		}
		try {
			System.out.println(updatedSubscriber[1]+" " + updatedSubscriber[2] + " " + updatedSubscriber[3]);
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
}
