package clientControllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import entities.Costumer;
import entities.User;
import entityControllers.CostumerController;
import entityControllers.UserController;
import enums.CostumerStatus;
import enums.Region;
import enums.Role;
import javafx.event.ActionEvent;

class IdentificationControllerTest {

	private Method loginTest;
	private IdentificationController mockController;
	private ActionEvent event;

	@BeforeEach
	public void setUp() throws NoSuchMethodException, SecurityException {
		loginTest = IdentificationController.class.getDeclaredMethod("userLogin", ActionEvent.class);
		loginTest.setAccessible(true);
		mockController = Mockito.mock(IdentificationController.class);
	}

	/***
	 * checking functionality: null username:"NOTEXIST" and password:"123" Result:
	 * Error message displayed on the screen, changeScreen method should not be
	 * called.
	 */
	@Test
	public void LoginTest_User_NotExist() throws Exception {
		doNothing().when(mockController).setTextLableErrorUserNotExist();
		doNothing().when(mockController).setUserDetails();
		mockController.userController = new UserController();
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "UserNotExist";
		verify(mockController, atLeastOnce()).setTextLableErrorUserNotExist();
		assertEquals(expected, result);
	}

	/*
	 * checking functionality: costumer alreadylogged in field and valid password
	 * Output: Error message displayed on the screen, changeScreen method should not
	 * be called
	 */
	@Test
	public void LoginTest_For_Logged_In_User() throws Exception {
		doNothing().when(mockController).setTextLableErrorUserAlreadyLoggedIn();
		doNothing().when(mockController).setUserDetails();
		mockController.userController = new UserController();
		mockController.userController.setUser(new User("costumer", "123456", "Peleg", "Oanuno", "peleg@braude.ac.il",
				"0525678891", true, "122", Role.Costumer, Region.SOUTH));
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "UserLoggedIn";
		verify(mockController, atLeastOnce()).setTextLableErrorUserAlreadyLoggedIn();
		assertEquals(expected, result);

	}

	@Test
	void LoginTest_Succsessful_ForExist_OL_Costumer() throws Exception {
		doNothing().when(mockController).changeScreenToRelevant("/clientGUI/Client_OL_MainView.fxml", event);
		doNothing().when(mockController).setUserDetails();
		doNothing().when(mockController).getCostumer();
		mockController.userController = new UserController();
		mockController.userController.setUser(new User("costumer2", "123456", "Inbar", "Mizrahi", "Inbar@braude.ac.ik",
				"0598883777", false, "125", Role.Costumer, Region.NORTH));
		mockController.configuration = "OL";
		mockController.costumerController = new CostumerController();
		mockController.costumerController
				.setCostumer(new Costumer("costumer2", "5326-7777-1111-4444", 2, CostumerStatus.APPROVED));
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "OLCostumer";
		verify(mockController, atLeastOnce()).changeScreenToRelevant("/clientGUI/Client_OL_MainView.fxml", event);
		assertEquals(expected, result);
	}

	@Test
	void LoginTest_Succsessful_ForExist_EK_Costumer() throws Exception {
		doNothing().when(mockController).changeScreenToRelevant("/clientGUI/Client_EK_MainView.fxml", event);
		doNothing().when(mockController).setUserDetails();
		doNothing().when(mockController).getCostumer();
		mockController.userController = new UserController();
		mockController.userController.setUser(new User("costumer2", "123456", "Inbar", "Mizrahi", "Inbar@braude.ac.ik",
				"0598883777", false, "125", Role.Costumer, Region.NORTH));
		mockController.configuration = "EK";
		mockController.costumerController = new CostumerController();
		mockController.costumerController
				.setCostumer(new Costumer("costumer2", "5326-7777-1111-4444", 2, CostumerStatus.APPROVED));
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "EKCostumer";
		verify(mockController, atLeastOnce()).changeScreenToRelevant("/clientGUI/Client_EK_MainView.fxml", event);
		assertEquals(expected, result);
	}

	@Test
	void LoginTest_Succsessful_For_Employee() throws Exception {
		doNothing().when(mockController).changeScreenToRelevant("/clientGUI/" + Role.CEO.toString(), event);
		doNothing().when(mockController).setUserDetails();
		mockController.userController = new UserController();
		mockController.userController.setUser(new User("ceo", "123456", "Ron", "Lahiani", "ron@braude.ac.il",
				"0509913037", false, "123", Role.CEO, Region.ALL));
		mockController.configuration = "EK";
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "EmployeeUser";
		verify(mockController, atLeastOnce()).changeScreenToRelevant("/clientGUI/" + Role.CEO.toString(), event);
		assertEquals(expected, result);
	}
	/*
	 * checking functionality:Test for empty password field Input: Valid username
	 * and empty password field Output: Error message displayed on the screen,
	 * changeScreen method should not be called
	 * 
	 */

	@Test
	public void testEmptyPasswordField() throws Exception {

	}

	/*
	 * *checking functionality:Test for successful login with special characters in
	 * the username and password Input: Valid username and password that contain
	 * special characters Output: changeScreen method should be called
	 */
	@Test
	public void testValidLoginWithSpecialChars() throws Exception {

	}

	/*
	 * checking functioality:Test for successful login with maximum length of
	 * username and password Input: Valid username and password that have the
	 * maximum allowed length Output: changeScreen method should be called
	 * 
	 */
	@Test
	public void testValidLoginWithMaxLength() throws Exception {

	}

	/*
	 * checking functioality:Test for exception handling Input: An exception is
	 * thrown during the communication with the server Output: Error message
	 * displayed on the screen, changeScreen method should not be called
	 * 
	 */
	@Test
	public void testExceptionHandling() throws Exception {

	}

	/*
	 * checking functiolity:Test for successful login with lowercase username and
	 * password Input: valid username and password that are in lowercase Output:
	 * changeScreen method should be called
	 */
	@Test
	public void testValidLoginWithLowercase() throws Exception {

	}

	/*
	 * checking functionality:Test for successful login with uppercase username and
	 * password Input: valid username and password that are in uppercase Output:
	 * changeScreen method should be called
	 * 
	 */
	@Test
	public void testValidLoginWithUppercase() throws Exception {

	}

	/*
	 * checking functioality:This test case is checking if the method can handle the
	 * case when the user is already logged in. Input: A user is already logged in
	 * and the text in the username and password fields is set to "validusername"
	 * and "validpassword" respectively. Output: The error label should be visible
	 * and the error message displayed should be "User is already logged in!". The
	 * accept method on the mockChatClient should not be called, and the
	 * changeScreen method on the mockScreenInterface should not be called.
	 */
	@Test
	public void testAlreadyLoggedIn() throws Exception {

	}

	/*
	 * checking functioality: checking if the method can handle the case when the
	 * login is successful and redirect the user to the appropriate screen based on
	 * their role. Input: A user is not already logged in and the text in the
	 * username and password fields is set to "validusername" and "validpassword"
	 * respectively. Output: The changeScreen method on the mockScreenInterface is
	 * called with the parameter "/clientGUI/SalesWorker_MainView.fxml" once.
	 */
	@Test
	public void testLoginSuccessful_SalesWorker() throws Exception {

	}

}
