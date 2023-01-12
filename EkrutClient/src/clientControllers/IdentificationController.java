package clientControllers;

import java.util.ArrayList;

import client.ChatClient;
import client.ChatClientIF;
import client.ClientUI;
import client.ScreenInterface;
import entities.Costumer;
import entities.Message;
import entities.User;
import entityControllers.CostumerController;
import entityControllers.UserController;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author peleg IdentificationController- a controller class that will connect
 *         between the fxml:Identification_Interface to the server by handle all
 *         the action from this screen.
 *
 */
public class IdentificationController {

	FXMLLoader loader = new FXMLLoader();
	@FXML
	private TextField txtUsername = null;

	@FXML
	private PasswordField txtPswd = null;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnExit;

	@FXML
	private ImageView ekrutLogo;

	/**
	 * QRimag - an image that will help us to connect the subscriber to system.
	 */
	@FXML
	private ImageView QRimage;

	@FXML
	private Label lblUsername;

	@FXML
	private Label lblPswd;

	@FXML
	private Label lblErrorOnDetails;

	private SetSceneController newScreen = new SetSceneController();
	
	//chatClient- a variable that help us to the dependency injection of ChatClient
	ChatClientIF chatClient;
	//screenInterface- a variable that help us to the dependency injection of the input of the user in the screen
	ScreenInterface screenInterface;

	/**
	 * An empty constructor that will cause the code to use the original chatClient methods and variables
	 */
	 public IdentificationController() {
		 chatClient=new ChatClientImpl();
		 screenInterface= new JavaFxFeatures();
	 }
   
/**
 * A constructor that receives a class that implements the interface ChatClientIF so that we can perform dependency injection in the test
 * @param chatClient-will help us for the test
 * @param screenInterface-will help us for the test
 */
    public IdentificationController(ChatClientIF chatClient,ScreenInterface screenInterface) {
       this.chatClient=chatClient;
       this.screenInterface= screenInterface;
    }

	public void initialize() {
		lblErrorOnDetails.setVisible(false);
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		ekrutLogo.setImage(image);
		
		Image image2 = new Image("/images/QR_Code.png");
		QRimage.setImage(image2);
	}
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		ClientUI.chat.accept(new Message(Request.Disconnect_request, null));
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("exit Identification Form");
		System.exit(0);
	}

	/**
	 * @author Ron getLoginBtn-a method that gets an action:clicked on Login
	 *         button,then client sends login request to server. usernameAndPsw - an
	 *         ArrayList that keeps the user name and password and send it to
	 *         server.
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void getLoginBtn(ActionEvent event) throws Exception {

		ArrayList<String> usernameAndPsw = new ArrayList<>();
		usernameAndPsw.add(screenInterface.getTxtUsername());
		usernameAndPsw.add(screenInterface.getTxtPswd());
		ClientUI.chat.accept(new Message(Request.Login_Request, usernameAndPsw));
		// if user is already loggedin
		if (!chatClient.isUserExist()) {
			// In case the user login input was invalid (username/password) - error label
			// will be shown
			lblErrorOnDetails.setVisible(true);
			lblErrorOnDetails.setText("Wrong username OR password! Try again!");
		} else {
			if (ChatClient.userController.getUser().isLoggedIn() == true) {
				lblErrorOnDetails.setVisible(true);
				lblErrorOnDetails.setText("User is already logged in!");
			} else {
				// loading next screen for specific user.
				if (chatClient.getRole().equals("Costumer")) {
					ClientUI.chat
							.accept(new Message(Request.Get_Costumer, ChatClient.userController.getUser().getId()));
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary

					//if (ChatClient.userController.getUser().getConfiguration().toString().equals("OL")
					if (chatClient.getConfiguration().equals("OL")
							&& !(chatClient.getStatus().equals("NOTAPPROVED"))) {
						newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
					} //else if (ChatClient.userController.getUser().getConfiguration().toString().equals("EK")
					else if (chatClient.getConfiguration().equals("EK")
							&& !(chatClient.getStatus().equals("NOTAPPROVED"))) {
						newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
					}
					if (chatClient.getStatus().equals("NOTAPPROVED")) {
						((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
						newScreen.setScreen(new Stage(), "/clientGUI/ScreenForNotApproveUserAfterLogin.fxml");
					}

				} else {
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
					newScreen.setScreen(new Stage(),
							"/clientGUI/" + ChatClient.userController.getUser().getRole().toString());

				}
			}
		}

	}
	
	
	/*
	 @FXML
	public void getLoginBtn(ActionEvent event) throws Exception {

		ArrayList<String> usernameAndPsw = new ArrayList<>();
		usernameAndPsw.add(txtUsername.getText());
		usernameAndPsw.add(txtPswd.getText());
		ClientUI.chat.accept(new Message(Request.Login_Request, usernameAndPsw));
		// if user is already loggedin
		if (!ChatClient.userController.isUserExist()) {
			// In case the user login input was invalid (username/password) - error label
			// will be shown
			lblErrorOnDetails.setVisible(true);
			lblErrorOnDetails.setText("Wrong username OR password! Try again!");
		} else {
			if (ChatClient.userController.getUser().isLoggedIn() == true) {
				lblErrorOnDetails.setVisible(true);
				lblErrorOnDetails.setText("User is already logged in!");
			} else {
				// loading next screen for specific user.
				if (ChatClient.userController.getUser().getRole().toString().equals("Costumer")) {
					ClientUI.chat
							.accept(new Message(Request.Get_Costumer, ChatClient.userController.getUser().getId()));
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary

					//if (ChatClient.userController.getUser().getConfiguration().toString().equals("OL")
					if (ChatClient.configuration.equals("OL")
							&& !(ChatClient.costumerController.getCostumer().getStatus().toString()
									.equals("NOTAPPROVED"))) {
						newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
					} //else if (ChatClient.userController.getUser().getConfiguration().toString().equals("EK")
					else if (ChatClient.configuration.equals("EK")
							&& !(ChatClient.costumerController.getCostumer().getStatus().toString()
									.equals("NOTAPPROVED"))) {
						newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
					}
					if (ChatClient.costumerController.getCostumer().getStatus().toString().equals("NOTAPPROVED")) {
						((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
						newScreen.setScreen(new Stage(), "/clientGUI/ScreenForNotApproveUserAfterLogin.fxml");
					}

				} else {
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
					newScreen.setScreen(new Stage(),
							"/clientGUI/" + ChatClient.userController.getUser().getRole().toString());

				}
			}
		}

	}
	 
	 
	 */

	/**
	 * @author clickOnQrImage-a method that gets an action:clicked on QR image to a
	 *         subscriber,then client sends login request to server. usernameAndPsw
	 *         - an ArrayList that keeps the user name and password and send it to
	 *         server.
	 * @param event
	 * @throws Exception
	 */
	public void clickOnQRImage(MouseEvent event) {
		ArrayList<String> usernameAndPsw = new ArrayList<>();
		usernameAndPsw.add("costumer2");
		usernameAndPsw.add("123456");
		ClientUI.chat.accept(new Message(Request.Login_Request, usernameAndPsw));
		// if user is already loggedin
		if (!ChatClient.userController.isUserExist()) {
			// In case the user login input was invalid (username/password) - error label
			// will be shown
			lblErrorOnDetails.setVisible(true);
			lblErrorOnDetails.setText("Wrong username OR password! Try again!");
		} else {
			if (chatClient.isLoggedIn() == true) {
				lblErrorOnDetails.setVisible(true);
				lblErrorOnDetails.setText("User is already logged in!");
			} else {
				// loading next screen for specific user.
				if (ChatClient.userController.getUser().getRole().toString().equals("Costumer")) {
					ClientUI.chat
							.accept(new Message(Request.Get_Costumer, ChatClient.userController.getUser().getId()));
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
					if (ChatClient.configuration.toString().equals("OL")) {
						newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
					} else {
						newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
					}

				} else {
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
					newScreen.setScreen(new Stage(),
							"/clientGUI/" + ChatClient.userController.getUser().getRole().toString());
				}
			}
		}
	}
	/**
	 * A class inheriting
	 *  from the interface ChatClientIF for injecting dependencies of the class ChatClient so that the original methods 
	 *  and variables of chatClient will be used in the code itself
	 * @author peleg
	 *
	 */
	public class ChatClientImpl implements ChatClientIF {
	    /**
	     * isUserExist- will return if the user is exist in the DB
	     */
	    public boolean isUserExist() {
	    	return ChatClient.userController.isUserExist();
	       
	    }
	    /**
	     *  getUser-will return the user that just do a login
	     *  @return User Object that login
	     */
	    public User getUser() {
	        return ChatClient.userController.getUser();
	    }
	    /**
	     * setUser-will set the user in userController that in ChatClient class
	     * @param user- User object that just log in
	     */
	    public void setUser(User user) {
	    	ChatClient.userController.setUser(user);
	    }
	    /**
	     *getCostumer- will return the costumer that just do a login
	     */
		@Override
		public Costumer getCostumer() {
			// TODO Auto-generated method stub
			return ChatClient.costumerController.getCostumer();
		}
		@Override
		public void accept(Message msg) {
			ClientUI.chat.accept(msg);
		
			
		}
		/**
		 * isLoggedIn-will return true in case the user is already LOGin and false if not
		 * @return boolean variable
		 */
		@Override
		public boolean isLoggedIn() {
			return ChatClient.userController.getUser().isLoggedIn();
		}
		/**getRole-will return the role of the user that do a login
		 * @return String object - a toString of the role of the user
		 */
		@Override
		public String getRole() {
			return ChatClient.userController.getUser().getRole().toString();
		}
		/**getStatus-will return the Status of the Costumer that do a login
		 * @return String object - a toString of the status of the Costumer
		 */
		@Override
		public String getStatus() {
			return ChatClient.costumerController.getCostumer().getStatus().toString();
		}
		/**
		 * getConfiguration-will return the Configuration of the system
		 * @return String object - a toString of the Configuration
		 */
		@Override
		public String getConfiguration() {
			return ChatClient.configuration.toString();
		}
	}
	
	public class JavaFxFeatures implements ScreenInterface{
		
		@Override
		public String getTxtUsername() {
			
			return txtUsername.getText();
		}

		@Override
		public String getTxtPswd() {
			// TODO Auto-generated method stub
			return txtPswd.getText();
		}
		
	}

	
}
