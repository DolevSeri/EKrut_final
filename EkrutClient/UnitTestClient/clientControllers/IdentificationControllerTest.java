package clientControllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import client.ChatClientIF;
import client.ScreenInterface;
import entities.Costumer;
import entities.Message;
import entities.User;
import entityControllers.CostumerController;
import entityControllers.UserController;
import enums.CostumerStatus;
import enums.Region;
import enums.Request;
import enums.Role;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

class IdentificationControllerTest {

	@Mock
	Stage mockStage;
	@Mock
	ChatClientIF mockChatClient;
	@Mock
	ScreenInterface mockScreenInterface;
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
	/*
	 * checking functionality:Test for empty password field Input: Valid username
	 * and empty password field Output: Error message displayed on the screen,
	 * changeScreen method should not be called
	 * 
	 */

	@Test
	public void testEmptyPasswordField() throws Exception {
		mockController.txtUsername.setText("validusername");
		mockController.txtPswd.setText("");
		mockController.getLoginBtn(null);
		verify(mockChatClient, times(0)).accept(any());
		verify(mockScreenInterface, times(0)).changeScreen(mockStage, "mainMenu.fxml");
		assertTrue(mockController.lblErrorOnDetails.isVisible());
	}

	/*
	 * *checking functionality:Test for successful login with special characters in
	 * the username and password Input: Valid username and password that contain
	 * special characters Output: changeScreen method should be called
	 */
	@Test
	public void testValidLoginWithSpecialChars() throws Exception {
		mockController.txtUsername.setText("validuser@#$");
		mockController.txtPswd.setText("validpassword@#$");
		mockController.getLoginBtn(null);
		verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {
			{
				add("validuser@#$");
				add("validpassword@#$");
			}
		}));
		verify(mockScreenInterface).changeScreen(mockStage, "mainMenu.fxml");
	}

	/*
	 * checking functioality:Test for successful login with maximum length of
	 * username and password Input: Valid username and password that have the
	 * maximum allowed length Output: changeScreen method should be called
	 * 
	 */
	@Test
	public void testValidLoginWithMaxLength() throws Exception {
		StringBuilder maxUsername = new StringBuilder();
		StringBuilder maxPassword = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			maxUsername.append("a");
			maxPassword.append("b");
		}
		mockController.txtUsername.setText(maxUsername.toString());
		mockController.txtPswd.setText(maxPassword.toString());
		mockController.getLoginBtn(null);
		verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {
			{
				add(maxUsername.toString());
				add(maxPassword.toString());
			}
		}));
		verify(mockScreenInterface).changeScreen(mockStage, "mainMenu.fxml");
	}

	/*
	 * checking functioality:Test for exception handling Input: An exception is
	 * thrown during the communication with the server Output: Error message
	 * displayed on the screen, changeScreen method should not be called
	 * 
	 */
	@Test
	public void testExceptionHandling() throws Exception {
		ChatClientIF mockChatClient = mock(ChatClientIF.class);
		ScreenInterface mockScreenInterface = mock(ScreenInterface.class);
		// IdentificationController controller = new
		// IdentificationController(mockChatClient, mockScreenInterface);
		// controller.txtUsername.setText("validusername");
		// controller.txtPswd.setText("validpassword");
		// configure the mockChatClient to throw an exception when the accept method is
		// called
		doThrow(new Exception("Server Error")).when(mockChatClient).accept(any());
		// controller.getLoginBtn(null);
		verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {
			{
				add("validusername");
				add("validpassword");
			}
		}));
		verify(mockScreenInterface, times(0)).changeScreen(mockStage, "mainMenu.fxml");
		// assertTrue(controller.lblErrorOnDetails.isVisible());
	}

	/*
	 * checking functiolity:Test for successful login with lowercase username and
	 * password Input: valid username and password that are in lowercase Output:
	 * changeScreen method should be called
	 */
	@Test
	public void testValidLoginWithLowercase() throws Exception {
		mockController.txtUsername.setText("validusername");
		mockController.txtPswd.setText("validpassword");
		mockController.getLoginBtn(null);
		verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {
			{
				add("validusername");
				add("validpassword");
			}
		}));
		verify(mockScreenInterface).changeScreen(mockStage, "mainMenu.fxml");
	}

	/*
	 * checking functionality:Test for successful login with uppercase username and
	 * password Input: valid username and password that are in uppercase Output:
	 * changeScreen method should be called
	 * 
	 */
	@Test
	public void testValidLoginWithUppercase() throws Exception {
		mockController.txtUsername.setText("VALIDUSERNAME");
		mockController.txtPswd.setText("VALIDPASSWORD");
		mockController.getLoginBtn(null);
		verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {
			{
				add("VALIDUSERNAME");
				add("VALIDPASSWORD");
			}
		}));
		verify(mockScreenInterface).changeScreen(mockStage, "mainMenu.fxml");
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
		// Configure the mockChatClient to return true when isLoggedIn() is called
		when(mockChatClient.isLoggedIn()).thenReturn(true);
		mockController.txtUsername.setText("validusername");
		mockController.txtPswd.setText("validpassword");
		mockController.getLoginBtn(null);
		assertTrue(mockController.lblErrorOnDetails.isVisible());
		assertEquals("User is already logged in!", mockController.lblErrorOnDetails.getText());
		verify(mockChatClient, times(0)).accept(any());
		verify(mockScreenInterface, times(0)).changeScreen(mockStage, "mainMenu.fxml");
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
		when(mockChatClient.isLoggedIn()).thenReturn(false);
		User mockUser = mock(User.class);
		when(mockUser.getRole()).thenReturn(Role.SalesWorker);
		when(mockChatClient.getUser()).thenReturn(mockUser);
		mockController.txtUsername.setText("validusername");
		mockController.txtPswd.setText("validpassword");
		mockController.getLoginBtn(null);
		verify(mockScreenInterface, times(1)).changeScreen(mockStage, "/clientGUI/SalesWorker_MainView.fxml");
	}

}
