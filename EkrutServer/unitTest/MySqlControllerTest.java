
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import entities.InventoryReport;
import entities.User;
import enums.Region;
import enums.Role;
import server.MySqlController;

class MySqlControllerTest {

	// Variables for inventory report tests
	InventoryReport haifa6_22;
	InventoryReport month2;

	HashMap<String, Integer> products = new HashMap<>();
	ArrayList<String> data = new ArrayList<>(Arrays.asList("NORTH", "2022", "06", "InventoryReport", "Haifa"));

	// Lists for login:
	ArrayList<String> userExistNotLoggedIn;
	ArrayList<String> userExistLoggedIn;
	ArrayList<String> userExistWrongPswd;
	ArrayList<String> nonExistingUser;

	@BeforeEach
	void setUp() throws Exception {
		MySqlController.connectToDB("jdbc:mysql://localhost/ekrut?serverTimezone=IST&useSSL=false", "root",
				"Aa102030aa!@");
		products.put("Bamba", 7);
		products.put("BisliGrill", 15);
		products.put("DoritosExtra", 3);
		products.put("BambaNogat", 19);
		haifa6_22 = new InventoryReport("06", "2022", "Haifa", products, "BambaNogat", 6);

		// Login Lists initialization
		userExistNotLoggedIn = new ArrayList<String>(Arrays.asList("ceo", "123456"));
		userExistLoggedIn = new ArrayList<String>(Arrays.asList("costumer", "123456"));
		userExistWrongPswd = new ArrayList<String>(Arrays.asList("costumer", "99999"));
		nonExistingUser = new ArrayList<String>(Arrays.asList("notValid", "99999"));

	}

	@Test
	void getInventoryReportTest_ExisttingReport() {
		InventoryReport res = MySqlController.getInventoryReportData(data);
		assertEquals(res, haifa6_22);
	}

	/**
	 * Description: Checking login method for existing user with matching user name
	 * and password Input data: User details - username: "ceo", password: "123456"
	 * Expected result: User object as follow: new User("ceo", "123456", "Ron",
	 * "Lahiani", ron@braude.ac.il, "0509913037", "false", 123,Role.CEO,Region.ALL);
	 */
	@Test
	public void LoggInForExistingAndNotLoggedInUser() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(userExistNotLoggedIn);
		User expected = new User("ceo", "123456", "Ron", "Lahiani", "ron@braude.ac.il", "0509913037", false, "123",
				Role.CEO, Region.ALL);
		assertEquals(expected, result);
	}

	/**
	 * Description: Checking login method for non exist user. Input data: User
	 * details - user name: "notValid", password:"99999". Expected result: null
	 */
	@Test
	public void LoggInForNonExistingUser() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(nonExistingUser);
		User expected = null;
		assertEquals(expected, result);
	}

	/**
	 * Description: Checking login method for invalid user (unsuccessful login)
	 * Input data: User details - username: "costumer", password: "99999" Expected
	 * result: null
	 */
	@Test
	public void loginForExitingUserWrongPassword() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(userExistWrongPswd);
		User expected = null;
		assertEquals(expected, result);
	}

	/**
	 * Description: Checking login method for existing user with matching user name
	 * and password Input data: User details - username: "ceo", password: "123456"
	 * Expected result: user object: new User("costumer", "123456", "Peleg",
	 * "Oanunu", "peleg@braude.ac.il", "0525678891", true, "122", Role.Costumer,
	 * Region.SOUTH);
	 */
	@Test
	public void LoggInForExistingUserAndLoggedIn() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(userExistLoggedIn);
		User expected = new User("costumer", "123456", "Peleg", "Oanunu", "peleg@braude.ac.il", "0525678891", true,
				"122", Role.Costumer, Region.SOUTH);
		assertEquals(expected, result);
	}

}
