package clientControllers;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Event;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChatClientIF;
import client.ScreenInterface;
import entities.Message;
import entities.User;
import enums.Request;
import enums.Role;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.junit.Before;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

class IdentificationControllerTest {

	    @Mock
	    Stage mockStage;
	    @Mock
	    ChatClientIF mockChatClient;
	    @Mock
	    ScreenInterface mockScreenInterface;

	    IdentificationController controller;
	    @Mock
	    ActionEvent  event;
	    @Mock
	    TextField mockTxtUsername;
	    @Mock
	    PasswordField mockTxtPswd;

	    @BeforeEach
	    public void setUp() {
	    	
	       mockStage = mock(Stage.class);
	        mockChatClient = mock(ChatClientIF.class);
	        mockScreenInterface = mock(ScreenInterface.class);
	        controller= new IdentificationController(mockChatClient, mockScreenInterface);
	        event = mock(ActionEvent.class);
	        

	        
	    }

	/*
	 *  checking functionality: Test for invalid login credentials
        Input: Invalid username:"NOTEXIST" and password:"123" 
        Result: Error message displayed on the screen, changeScreen method should not be called
	 */
	@Test
	public void testInvalidLoginCredentials() throws Exception {
		
		 // Arrange
	    when(mockScreenInterface.getTxtUsername()).thenReturn("NOTEXIST");
	    when(mockScreenInterface.getTxtPswd()).thenReturn("123");
	   // 
	    doNothing().when(mockChatClient).accept(any(Message.class));
	    when(mockChatClient.isUserExist()).thenReturn(false);
	    // Act
	    controller.getLoginBtn(event);

	    // Assert
	    // Verify that the accept method is called on the mockChatClient object with the correct arguments
	    verify(mockChatClient, times(1)).accept(new Message(Request.Login_Request, new String[] {"NOTEXIST", "123"}));
	    // Verify that the changeScreen method is not called
	    verify(mockScreenInterface, times(0)).changeScreen(any(),any());
	    // Verify that the error message label is visible
	    assertTrue(controller.lblErrorOnDetails.isVisible());
	    assertEquals("Wrong username OR password! Try again!", controller.lblErrorOnDetails.getText());
	}
	
	/*
	 checking functionality: Test for empty username field
	Input: Empty username field and valid password
	Output: Error message displayed on the screen, changeScreen method should not be called*/
	@Test
	public void testEmptyUsernameField() throws Exception {
	    controller.txtUsername.setText("");
	    controller.txtPswd.setText("validpassword");
	    controller.getLoginBtn(event);
	    verify(mockChatClient, times(0)).accept(any());
	    verify(mockScreenInterface, times(0)).changeScreen(mockStage,"mainMenu.fxml");
	    assertTrue(controller.lblErrorOnDetails.isVisible());
	}
	/*checking functionality:Test for empty password field
      Input: Valid username and empty password field
      Output: Error message displayed on the screen, changeScreen method should not be called
	 * 
	 */
	
	 @Test
	    public void testEmptyPasswordField() throws Exception {
	        controller.txtUsername.setText("validusername");
	        controller.txtPswd.setText("");
	        controller.getLoginBtn(null);
	        verify(mockChatClient, times(0)).accept(any());
	        verify(mockScreenInterface, times(0)).changeScreen(mockStage,"mainMenu.fxml");
	        assertTrue(controller.lblErrorOnDetails.isVisible());
	    }
	 
	 /*
	  * *checking functionality:Test for successful login with special characters in the username and password
         Input: Valid username and password that contain special characters
         Output: changeScreen method should be called
	  */
	 @Test
	 public void testValidLoginWithSpecialChars() throws Exception {
	     controller.txtUsername.setText("validuser@#$");
	     controller.txtPswd.setText("validpassword@#$");
	     controller.getLoginBtn(null);
	     verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {{ add("validuser@#$"); add("validpassword@#$"); }}));
	     verify(mockScreenInterface).changeScreen(mockStage,"mainMenu.fxml");
	 }
	 /*checking functioality:Test for successful login with maximum length of username and password
        Input: Valid username and password that have the maximum allowed length
        Output: changeScreen method should be called
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
	     controller.txtUsername.setText(maxUsername.toString());
	     controller.txtPswd.setText(maxPassword.toString());
	     controller.getLoginBtn(null);
	     verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {{ add(maxUsername.toString()); add(maxPassword.toString()); }}));
	     verify(mockScreenInterface).changeScreen(mockStage,"mainMenu.fxml");
	 }
	 /*checking functioality:Test for exception handling
        Input: An exception is thrown during the communication with the server
        Output: Error message displayed on the screen, changeScreen method should not be called
	  * 
	  */
	 @Test
	    public void testExceptionHandling() throws Exception {
	        ChatClientIF mockChatClient = mock(ChatClientIF.class);
	        ScreenInterface mockScreenInterface = mock(ScreenInterface.class);
	        IdentificationController controller = new IdentificationController(mockChatClient, mockScreenInterface);
	        controller.txtUsername.setText("validusername");
	        controller.txtPswd.setText("validpassword");
	        // configure the mockChatClient to throw an exception when the accept method is called
	        doThrow(new Exception("Server Error")).when(mockChatClient).accept(any());
	        controller.getLoginBtn(null);
	        verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {{ add("validusername"); add("validpassword"); }}));
	        verify(mockScreenInterface, times(0)).changeScreen(mockStage,"mainMenu.fxml");
	        assertTrue(controller.lblErrorOnDetails.isVisible());
	    }
	 
	 /*checking functiolity:Test for successful login with lowercase username and password
       Input: valid username and password that are in lowercase
        Output: changeScreen method should be called*/
        @Test
    public void testValidLoginWithLowercase() throws Exception {
        controller.txtUsername.setText("validusername");
        controller.txtPswd.setText("validpassword");
        controller.getLoginBtn(null);
        verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {{ add("validusername"); add("validpassword"); }}));
        verify(mockScreenInterface).changeScreen(mockStage,"mainMenu.fxml");
    }
	  /*checking functionality:Test for successful login with uppercase username and password
        Input: valid username and password that are in uppercase
        Output: changeScreen method should be called
	   * 
	   */
        @Test
        public void testValidLoginWithUppercase() throws Exception {
            controller.txtUsername.setText("VALIDUSERNAME");
            controller.txtPswd.setText("VALIDPASSWORD");
            controller.getLoginBtn(null);
            verify(mockChatClient).accept(new Message(Request.Login_Request, new ArrayList<String>() {{ add("VALIDUSERNAME"); add("VALIDPASSWORD"); }}));
            verify(mockScreenInterface).changeScreen(mockStage,"mainMenu.fxml");
        }
        
        /*checking functioality:This test case is checking if the method can handle the case when the user is already logged in.
         * Input: A user is already logged in and the text in the username and password fields is set to "validusername" and "validpassword" respectively.
           Output: The error label should be visible and the error message displayed should be "User is already logged in!".
            The accept method
           on the mockChatClient should not be called, and the changeScreen method on the mockScreenInterface should not be called.
         */
        @Test
        public void testAlreadyLoggedIn() throws Exception {
            // Configure the mockChatClient to return true when isLoggedIn() is called
            when(mockChatClient.isLoggedIn()).thenReturn(true);
            controller.txtUsername.setText("validusername");
            controller.txtPswd.setText("validpassword");
            controller.getLoginBtn(null);
            assertTrue(controller.lblErrorOnDetails.isVisible());
            assertEquals("User is already logged in!", controller.lblErrorOnDetails.getText());
            verify(mockChatClient, times(0)).accept(any());
            verify(mockScreenInterface, times(0)).changeScreen(mockStage,"mainMenu.fxml");
        }
        /*checking functioality: checking if
         *  the method can handle the case when the login is successful and redirect the user
         *   to the appropriate screen based on their role.
         * Input: A user is not already logged in and the text in the username and password fields is set to
         *  "validusername" and "validpassword" respectively.
           Output: The changeScreen method on the mockScreenInterface is called with the parameter "/clientGUI/SalesWorker_MainView.fxml" once.
         */
        @Test
        public void testLoginSuccessful_SalesWorker() throws Exception {
            when(mockChatClient.isLoggedIn()).thenReturn(false);
            User mockUser = mock(User.class);
            when(mockUser.getRole()).thenReturn(Role.SalesWorker);
            when(mockChatClient.getUser()).thenReturn(mockUser);
            controller.txtUsername.setText("validusername");
            controller.txtPswd.setText("validpassword");
            controller.getLoginBtn(null);
            verify(mockScreenInterface, times(1)).changeScreen(mockStage,"/clientGUI/SalesWorker_MainView.fxml");
        }
        
	  

}
