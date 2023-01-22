package clientControllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

	// functionality : checking userLogin when the User is null object
	// Input data: ActionEvent object event , null object
	// Expected result: String "UserNotExist" and verify that
	// setTextLableErrorUserNotExist called.
	@Test
	public void userLoginTest_User_NotExistUser_UserIsNull() throws Exception {
		doNothing().when(mockController).setTextLableErrorUserNotExist();
		doNothing().when(mockController).setUserDetails();
		mockController.userController = new UserController();
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "UserNotExist";
		verify(mockController, atLeastOnce()).setTextLableErrorUserNotExist();
		assertEquals(expected, result);
	}

	// functionality : checking userLogin when the condition "if" match to User is
	// empty Object case
	// Input data: ActionEvent event object , new User object (empty object)
	// Expected result: String "UserNotInitializeCorrectly" and verify that
	// setTextLableUserNotInitilazeCorrectly called.
	@Test
	public void userLoginTest_User_NotExistUserUserIsEmptyObject() throws Exception {
		doNothing().when(mockController).setTextLableErrorUserNotExist();
		doNothing().when(mockController).setUserDetails();
		mockController.userController = new UserController();
		mockController.userController.setUser(new User());
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "UserNotInitializeCorrectly";
		verify(mockController, atLeastOnce()).setTextLableUserNotInitilazeCorrectly();
		assertEquals(expected, result);
	}

	// functionality : checking userLogin when the condition "if" match to User
	// already logged in case
	// Input data: ActionEvent event object , new User("costumer", "123456",
	// "Peleg", "Oanuno", "peleg@braude.ac.il","0525678891", true, "122",
	// Role.Costumer, Region.SOUTH))
	// Expected result: String "UserLoggedIn" and verify that
	// setTextLableErrorUserAlreadyLoggedIn called.
	@Test
	public void userLoginTest_For_Logged_In_User() throws Exception {
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

	// functionality : checking userLogin when user exist and and user is customer
	// and the configuration is OL
	// Input data: ActionEvent event object , new User("costumer2", "123456",
	// "Inbar", "Mizrahi", "Inbar@braude.ac.ik","0598883777", false, "125",
	// Role.Costumer, Region.NORTH)
	// Expected result: String "OLCostumer" and verify that changeScreenToRelevant
	// called.
	@Test
	void userLoginTest_Succsessful_ForExist_OL_Costumer() throws Exception {
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

	// functionality : checking userLogin when user exist and and user is customer
	// and the configuration is EK
	// Input data: ActionEvent event object , new User("costumer2", "123456",
	// "Inbar", "Mizrahi", "Inbar@braude.ac.ik","0598883777", false, "125",
	// Role.Costumer, Region.NORTH)
	// Expected result: String "EKCostumer" and verify that changeScreenToRelevant
	// called.
	@Test
	void userLoginTest_Succsessful_ForExist_EK_Costumer() throws Exception {
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

	// functionality : checking userLogin when user exist and and user is stuff
	// member
	// Input data: ActionEvent event object , new User("ceo", "123456", "Ron",
	// "Lahiani", "ron@braude.ac.il","0509913037", false, "123", Role.CEO,
	// Region.ALL)
	// Expected result: String "EmployeeUser" and verify that changeScreenToRelevant
	// called.
	@Test
	void userLoginTest_Succsessful_For_Employee() throws Exception {
		doNothing().when(mockController).changeScreenToRelevant("/clientGUI/" + Role.CEO.toString(), event);
		doNothing().when(mockController).setUserDetails();
		mockController.userController = new UserController();
		mockController.userController.setUser(new User("ceo", "123456", "Ron", "Lahiani", "ron@braude.ac.il",
				"0509913037", false, "123", Role.CEO, Region.ALL));
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "EmployeeUser";
		verify(mockController, atLeastOnce()).changeScreenToRelevant("/clientGUI/" + Role.CEO.toString(), event);
		assertEquals(expected, result);
	}

	// functionality : checking userLogin for user that not signup
	// Input data: ActionEvent event object ,new User("user3", "123456", "Osher",
	// "Lahiani", "osher@braude.ac.il","0509913039", false, "122", Role.NotSignUp,
	// Region.NORTH)
	// Expected result: String "UserNotSignUP" and verify that
	// changeScreenToRelevant called.
	@Test
	void userLoginTest_Succsessful_For_UserNotSignUp() throws Exception {
		doNothing().when(mockController).changeScreenToRelevant("/clientGUI/" + Role.NotSignUp.toString(), event);
		doNothing().when(mockController).setUserDetails();
		mockController.userController = new UserController();
		mockController.userController.setUser(new User("user3", "123456", "Osher", "Lahiani", "osher@braude.ac.il",
				"0509913039", false, "122", Role.NotSignUp, Region.NORTH));
		mockController.configuration = "EK";
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "UserNotSignUP";
		verify(mockController, atLeastOnce()).changeScreenToRelevant("/clientGUI/" + Role.NotSignUp.toString(), event);
		assertEquals(expected, result);

	}

	// functionality : checking userLogin for user exist and he is customer that not approved
	// Input data: ActionEvent event object ,new User("user3", "123456", "Osher",
	// "Lahiani", "osher@braude.ac.il","0509913039", false, "122", Role.NotSignUp,
	// Region.NORTH) , new Costumer("costumer2", "5326-7777-1111-4444", 2, CostumerStatus.NOTAPPROVED)
	// Expected result: String "CostumerNotApproved" and verify that
	// changeScreenToRelevant called.
	@Test
	void userLoginTest_Succsessful_For_Costumer_NotApproved() throws Exception {
		doNothing().when(mockController).changeScreenToRelevant("/clientGUI/ScreenForNotApproveUserAfterLogin.fxml", event);
		doNothing().when(mockController).setUserDetails();
		mockController.userController = new UserController();
		mockController.userController.setUser(new User("costumer2", "123456", "Osher", "Lahiani", "osher@braude.ac.il",
				"0509913039", false, "122", Role.Costumer, Region.NORTH));
		mockController.configuration = "EK";
		mockController.costumerController = new CostumerController();
		mockController.costumerController
				.setCostumer(new Costumer("costumer2", "5326-7777-1111-4444", 2, CostumerStatus.NOTAPPROVED));
		String result = (String) loginTest.invoke(mockController, event);
		String expected = "CostumerNotApproved";
		verify(mockController, atLeastOnce()).changeScreenToRelevant("/clientGUI/ScreenForNotApproveUserAfterLogin.fxml", event);
		assertEquals(expected, result);

	}
}
