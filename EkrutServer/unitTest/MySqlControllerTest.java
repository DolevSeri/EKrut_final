import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import entities.InventoryReport;
import entities.User;
import enums.Region;
import enums.Role;
import server.MySqlController;
import serverControllers.EndOfMonthChecker;
import serverControllers.ServerPortFrameController;

class MySqlControllerTest {

	// Variables for inventory report tests
	InventoryReport haifa06_22;
	InventoryReport habshan12_22;
	InventoryReport haifa_check; // for checking create report functionality
	InventoryReport nullReport;
	HashMap<String, Integer> products = new HashMap<>();
	HashMap<String, Integer> haifaPoductsCheck = new HashMap<>();
	HashMap<String, Integer> emptyProducts = new HashMap<>();
	ArrayList<String> data = new ArrayList<>(Arrays.asList("NORTH", "2022", "06", "InventoryReport", "Haifa"));
	ArrayList<String> data2 = new ArrayList<>(Arrays.asList("UAE", "2022", "12", "InventoryReport", "Habshan"));
	ArrayList<String> data3 = new ArrayList<>();

	// Lists for login:
	ArrayList<String> userExistNotLoggedIn;
	ArrayList<String> userExistLoggedIn;
	ArrayList<String> userExistWrongPswd;
	ArrayList<String> nonExistingUser;
	ArrayList<String> emptyFields;
	ArrayList<String> nullCheck = null;
	ArrayList<String> userExistWrongUserName;
	ArrayList<String> emptyUsernameExistPswd;
	ArrayList<String> emptyPswdExistUsername;
	ArrayList<String> emptyObject;

	@BeforeEach
	void setUp() throws Exception {
		MySqlController.connectToDB("jdbc:mysql://localhost/ekrut?serverTimezone=IST&useSSL=false", "root", "Aa123456");
		products.put("Bamba", 7);
		products.put("BisliGrill", 15);
		products.put("DoritosExtra", 3);
		products.put("BambaNogat", 19);
		haifa06_22 = new InventoryReport("06", "2022", "Haifa", products, "BambaNogat", 6);

		habshan12_22 = new InventoryReport("12", "2022", "Habshan", emptyProducts, null, 2);

		nullReport = null;

		haifaPoductsCheck.put("Bamba", 51);
		haifaPoductsCheck.put("ShugiUgi", 8);
		haifaPoductsCheck.put("yellowM&M", 18);
		haifaPoductsCheck.put("RedFanta", 23);
		haifa_check = new InventoryReport("12", "2022", "Haifa", haifaPoductsCheck, "Bamba", 6);

		// Login Lists initialization
		userExistNotLoggedIn = new ArrayList<String>(Arrays.asList("ceo", "123456"));
		userExistLoggedIn = new ArrayList<String>(Arrays.asList("costumer3", "123456"));
		userExistWrongPswd = new ArrayList<String>(Arrays.asList("costumer", "99999"));
		nonExistingUser = new ArrayList<String>(Arrays.asList("notValid", "99999"));
		emptyFields = new ArrayList<String>(Arrays.asList("", ""));
		nullCheck = null;
		userExistWrongUserName = new ArrayList<String>(Arrays.asList("NotExist", "123456"));
		emptyUsernameExistPswd = new ArrayList<String>(Arrays.asList("", "123456"));
		emptyPswdExistUsername = new ArrayList<String>(Arrays.asList("costumer", ""));
		emptyObject = new ArrayList<String>();
	}

	// Functionality: check getInventoryReport functionality when report is exist on
	// DB
	// Input: haifa06_22 (InventoryReport), res(IneventoryReport)
	// Expected Result: new InventoryReport("06", "2022", "Haifa", products,
	// "BambaNogat", 6);
	@Test
	void getInventoryReportTest_ExisttingReport() {
		InventoryReport res = MySqlController.getInventoryReportData(data);
		assertEquals(res, haifa06_22);
	}

	// Functionality: check getInventoryReport functionality when report is exist on
	// DB but some of its field are empty
	// Input: habshan12_22 (InventoryReport), res(IneventoryReport)
	// Expected Result: new InventoryReport("12", "2022", "Habshan", emptyProducts,
	// null, 2);
	@Test
	void getInventoryReportTest_EmptyReport() {
		InventoryReport res = MySqlController.getInventoryReportData(data2);
		assertEquals(res, habshan12_22);
	}

	// Functionality: check getInventoryReport functionality when report is not
	// exist on DB
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: nullReport
	@Test
	void getInventoryReportTest_ReportNotExist_ByAllFields() {
		data3.addAll(Arrays.asList("SOUTH", "2023", "1", "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when report is not
	// exist on DB
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: nullReport
	@Test
	void getInventoryReportTest_ReportNotExist_ByMonth() {
		data3.addAll(Arrays.asList("SOUTH", "2022", "1", "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when report is not
	// exist on DB
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: nullReport
	@Test
	void getInventoryReportTest_ReportNotExist_ByYear() {
		data3.addAll(Arrays.asList("SOUTH", "2020", "12", "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when report is not
	// exist on DB
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: nullReport
	@Test
	void getInventoryReportTest_ReportNotExist_ByDeviceName() {
		data3.addAll(Arrays.asList("SOUTH", "2022", "12", "InventoryReport", "Eilat"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when report data list
	// is empty
	// Input: res(IneventoryReport)
	// Expected Result: throw IndexOutOfBoundsException
	@Test
	void getInventoryReportTest_EmptyData() {
		assertThrows(IndexOutOfBoundsException.class, () -> MySqlController.getInventoryReportData(data3));
	}

	// Functionality: check getInventoryReport functionality when month on report
	// data is null
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: nullReport
	@Test
	void getInventoryReportTest_ReportNotExist_nullMonth() {
		data3.addAll(Arrays.asList("SOUTH", "2022", null, "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when year on report
	// data is null
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: nullReport
	@Test
	void getInventoryReportTest_ReportNotExist_nullYear() {
		data3.addAll(Arrays.asList("SOUTH", null, "12", "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when device name on
	// report data is null
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: nullReport
	@Test
	void getInventoryReportTest_ReportNotExist_nullDeviceName() {
		data3.addAll(Arrays.asList("SOUTH", "2022", "12", "InventoryReport", null));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check createInventoryReport functionality all details valid
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result:new InventoryReport("12", "2022", "Haifa", haifaPoductsCheck,
	// "Bamba", 6)
	@Test
	void createInventoryReport_ReportWithProducts() {
		data3.addAll(Arrays.asList("NORTH", "2022", "12", "InventoryReport", "Haifa"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		// check if the report does not already exist on DB
		assertEquals(res, nullReport);
		MySqlController.createMonthlyInventoryReport(new ArrayList<String>(Arrays.asList("12", "2022", "Haifa")));
		res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, haifa_check);
	}

	// Functionality: check createInventoryReport functionality all details valid
	// when there is no products that was under threshold at this month
	// Input: expected (InventoryReport), res(IneventoryReport)
	// Expected Result: new InventoryReport("01", "2020", "Haifa", emptyProducts,
	// null, 6)
	@Test
	void createInventoryReport_ReportWithoutProducts() {
		data3.addAll(Arrays.asList("NORTH", "2020", "01", "InventoryReport", "Haifa"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		// check if the report does not already exist on DB
		assertEquals(res, nullReport);
		InventoryReport expected = new InventoryReport("01", "2020", "Haifa", emptyProducts, null, 6);
		MySqlController.createMonthlyInventoryReport(new ArrayList<String>(Arrays.asList("01", "2020", "Haifa")));
		res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, expected);
	}

	// Functionality: check createInventoryReport functionality all details null
	// Input: none
	// Expected Result: NullPointerException
	@Test
	void createInventoryReport_nullReportData() {
		assertThrows(NullPointerException.class, () -> MySqlController.getInventoryReportData(null));

	}

	// functionality: test for user is already loggedin try to loggin
	// Input data: ArrayList<String> object that contains username="ceo" and
	// password ="123456"
	// expected result: new User("ceo", "123456", "Ron", "Lahiani",
	// "ron@braude.ac.il", "0509913037", false, "123",
	// Role.CEO, Region.ALL);
	@Test
	public void LoggInForExistingAndNotLoggedInUser() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(userExistNotLoggedIn);
		User expected = new User("ceo", "123456", "Ron", "Lahiani", "ron@braude.ac.il", "0509913037", false, "123",
				Role.CEO, Region.ALL);
		assertEquals(expected, result);
	}

	// functionality: test for empty username and password when try to loggin
	// Input data: ArrayList<String> object that contains username="notValid" ,
	// password = "99999"
	// expected result: null
	@Test
	public void LoggInForNonExistingUser() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(nonExistingUser);
		User expected = null;
		assertEquals(expected, result);
	}

	// functionality: test for loggin with wrong password and valid username.
	// Input data: ArrayList<String> object that contains username="costumer" ,
	// password = "99999"
	// expected result: null
	@Test
	public void loginForExitingUserWrongPassword() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(userExistWrongPswd);
		User expected = null;
		assertEquals(expected, result);
	}

	// functionality: test for user is already loggedin try to loggin
	// Input data: ArrayList<String> object that contains username="costumer" and
	// password ="123456"
	// expected result:new User("costumer3", "123456", "Dolev", "Seri",
	// "Dolev@gmail.il", "0501122334", true, "125"
	// , Role.Costumer, Region.UAE);
	@Test
	public void LoggInForExistingUserAndLoggedIn() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(userExistLoggedIn);
		User expected = new User("costumer3", "123456", "Dolev", "Seri", "Dolev@gmail.il", "0501122334", true, "125",
				Role.Costumer, Region.UAE);
		assertEquals(expected, result);
	}

	// functionality: test for empty username and password when try to loggin
	// Input data: ArrayList<String> object that contains "" in username and
	// password
	// expected result: null
	@Test
	public void LoggIn_For_Empty_Fields() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(emptyFields);
		User expected = null;
		assertEquals(expected, result);
	}

	// functionality: test when username and passwords is null
	// Input data: null object
	// expected result: null
	@Test
	public void LoggIn_For_NULL_Fields() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(nullCheck);
		User expected = null;
		assertEquals(expected, result);
	}

	// functionality: test for login with wrong userName and exist password.
	// Input data: ArrayList<String> object that contains username="NotExist"
	// ,password = "123456"
	// expected result: null
	@Test
	public void login_For_WrongUserNameAndCorrectPassword() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(userExistWrongUserName);
		User expected = null;
		assertEquals(expected, result);
	}

	// functionality: test for login with empty userName and exist password.
	// Input data: ArrayList<String> object that contains username="" ,password =
	// "123456"
	// expected result: null
	@Test
	public void loginFor_Empty_UserNameAndCorrectPassword() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(emptyUsernameExistPswd);
		User expected = null;
		assertEquals(expected, result);
	}

	// functionality: test for login with exist userName and empty password.
	// Input data: ArrayList<String> object that contains username="costumer"
	// ,password = ""
	// expected result: null
	@Test
	public void loginFor_Empty_Password_AndCorrect_Username() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(emptyPswdExistUsername);
		User expected = null;
		assertEquals(expected, result);
	}

	// functionality: test for login with empty object
	// Input data: new ArrayList<String>() object
	// expected result: null
	@Test
	public void loginFor_Empty_Object() {
		User result = MySqlController.LoginCheckAndUpdateLoggedIn(emptyObject);
		User expected = null;
		assertEquals(expected, result);
	}

	@Test
	public void testCreateReport() {
		// create a mock controller
		ServerPortFrameController mockController = Mockito.mock(ServerPortFrameController.class);
		// create an instance of the EndOfMonthChecker class with the mock controller
		EndOfMonthChecker checker = new EndOfMonthChecker(mockController);

		// set the current date to the last day of the month
		Calendar today = Calendar.getInstance();
		int lastDayOfMonth = today.getActualMaximum(Calendar.DAY_OF_MONTH);
		today.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);

		// call the start method to schedule the endOfMonthChecker task
		checker.start();

		// wait for the task to run and create the report
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// verify that the createReport method was called with the correct parameters
		Mockito.verify(mockController, Mockito.times(1)).createReport(true);

		// shut down the executor
		checker.getExecutor().shutdown();
	}

}
