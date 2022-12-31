package clientControllers;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	private TextField txtUsername = null;

	@FXML
	private PasswordField txtPswd = null;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnExit;

	@FXML
	private ImageView logoImage;

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

	public void initialize() {
		lblErrorOnDetails.setVisible(false);
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
					if (ChatClient.userController.getUser().getConfiguration().toString().equals("OL")) {
						newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
					} else {
						newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
					}
					if(ChatClient.costumerController.getCostumer().getStatus().toString().equals("NOTAPPROVED")) {
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

	/**
	 * @author  clickOnQrImage-a method that gets an action:clicked on QR
	 *         image to a subscriber,then client sends login request to server. usernameAndPsw - an
	 *         ArrayList that keeps the user name and password and send it to
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
			if (ChatClient.userController.getUser().isLoggedIn() == true) {
				lblErrorOnDetails.setVisible(true);
				lblErrorOnDetails.setText("User is already logged in!");
			} else {
				// loading next screen for specific user.
				if (ChatClient.userController.getUser().getRole().toString().equals("Costumer")) {
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
					if (ChatClient.userController.getUser().getConfiguration().toString().equals("OL")) {
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
