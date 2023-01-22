package clientControllers;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entities.User;
import entityControllers.CostumerController;
import entityControllers.UserController;
import enums.Request;
import enums.Role;
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
	TextField txtUsername = null;

	@FXML
	PasswordField txtPswd = null;

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
	Label lblErrorOnDetails;

	public UserController userController;
	public CostumerController costumerController;
	public String configuration = new String();
	private SetSceneController newScreen = new SetSceneController();

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public void initialize() {
		lblErrorOnDetails.setVisible(false);
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		ekrutLogo.setImage(image);

		Image image2 = new Image("/images/QR_Code.png");
		QRimage.setImage(image2);
		setCongifuration(ChatClient.configuration);
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
		userLogin(event);
	}

	private String userLogin(ActionEvent event) {
		setUserDetails();
		if (userController == null) {
			return "UserControllerIsNull";
		}

		// if user is exist in DB
		if (!userController.isUserExist()) {
			// In case the user login input was invalid (username/password) - error label
			// will be shown
			setTextLableErrorUserNotExist();
			return "UserNotExist";
		}
		if (userController.checkNullFieldsOfUser(userController.getUser())) {
			setTextLableUserNotInitilazeCorrectly();
			return "UserNotInitializeCorrectly";
		}
		else {
			if (userController.getUser().isLoggedIn()) {
				setTextLableErrorUserAlreadyLoggedIn();
				return "UserLoggedIn";
			} else {

				// loading next screen for specific user.
				if (userController.getUser().getRole().toString().equals("Costumer")) {
					getCostumer();
					if ((configuration.equals("OL"))
							&& !(costumerController.getCostumer().getStatus().toString().equals("NOTAPPROVED"))) {
						changeScreenToRelevant("/clientGUI/Client_OL_MainView.fxml", event);
						return "OLCostumer";
					} else if ((configuration.equals("EK")
							&& !(costumerController.getCostumer().getStatus().toString().equals("NOTAPPROVED")))) {
						changeScreenToRelevant("/clientGUI/Client_EK_MainView.fxml", event);
						return "EKCostumer";
					}
					if (costumerController.getCostumer().getStatus().toString().equals("NOTAPPROVED")) {
						changeScreenToRelevant("/clientGUI/ScreenForNotApproveUserAfterLogin.fxml", event);
						return "CostumerNotApproved";
					}

				}

				else {
					if (userController.getUser().getRole().equals(Role.NotSignUp)) {
						changeScreenToRelevant("/clientGUI/" + userController.getUser().getRole().toString(), event);
						return "UserNotSignUP";
					}

					changeScreenToRelevant("/clientGUI/" + userController.getUser().getRole().toString(), event);
					return "EmployeeUser";
				}
			}
		}
		return "UserNotExist";
	}

	public void getCostumer() {
		ClientUI.chat.accept(new Message(Request.Get_Costumer, userController.getUser().getId()));
		costumerController = ChatClient.costumerController;

	}

	public void setUserDetails() {
		ArrayList<String> usernameAndPsw = new ArrayList<>();
		usernameAndPsw.add(txtUsername.getText());
		usernameAndPsw.add(txtPswd.getText());
		ClientUI.chat.accept(new Message(Request.Login_Request, usernameAndPsw));
		userController = ChatClient.userController;
	}

	public void setTextLableErrorUserNotExist() {
		lblErrorOnDetails.setVisible(true);
		lblErrorOnDetails.setText("Wrong username OR password! Try again!");
	}

	public void setCongifuration(String configuration) {
		this.configuration = configuration;
	}

	public void setTextLableErrorUserAlreadyLoggedIn() {
		lblErrorOnDetails.setVisible(true);
		lblErrorOnDetails.setText("User is already logged in!");
	}

	public void setTextLableUserNotInitilazeCorrectly() {
		lblErrorOnDetails.setVisible(true);
		lblErrorOnDetails.setText("User is not sign up correctly!");
	}

	public void changeScreenToRelevant(String path, ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
		newScreen.setScreen(new Stage(), path);
	}

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
		userController = ChatClient.userController;
		configuration = ChatClient.configuration;
		// if user is already loggedin
		if (!userController.isUserExist()) {
			// In case the user login input was invalid (username/password) - error label
			// will be shown
			lblErrorOnDetails.setVisible(true);
			lblErrorOnDetails.setText("Wrong username OR password! Try again!");
		} else {
			if (userController.getUser().isLoggedIn() == true) {
				lblErrorOnDetails.setVisible(true);
				lblErrorOnDetails.setText("User is already logged in!");
			} else {
				// loading next screen for specific user.
				if (userController.getUser().getRole().toString().equals("Costumer")) {
					ClientUI.chat
							.accept(new Message(Request.Get_Costumer, ChatClient.userController.getUser().getId()));
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
					if (configuration.toString().equals("OL")) {
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

}
