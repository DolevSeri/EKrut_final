package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * The NotApproveUserController class is the controller class that handles the
 * functionality of the not approved user screen. It contains the functionality
 * of the screen's buttons, such as logout and exit, and also displays the ekrut
 * logo.
 * 
 * @author Peleg
 */
public class NotApproveUserController {

	SetSceneController newScreen = new SetSceneController();
	FXMLLoader loader = new FXMLLoader();
	@FXML
	private Button btnExit;

	@FXML
	private Button btnLogOut;

	@FXML
	private ImageView ekrutLogo;

	@FXML
	private Label lblErrorOnDetails;

	/**
	 * 
	 * Initializes the not approve user screen by setting the ekrut logo image.
	 */
	public void initialize() {

		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		ekrutLogo.setImage(image);
	}

	/**
	 * 
	 * Handles the Exit button click event. Closes the application.
	 * 
	 * @param event the click event
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.exitOrLogOut(event, true);
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

	/**
	 * 
	 * Handles the Logout button click event. Logs the user out and redirects to the
	 * login screen.
	 * 
	 * @param event the click event
	 */
	@FXML
	void clickOnLogout(ActionEvent event) {
		newScreen.exitOrLogOut(event, true);
	}

}
