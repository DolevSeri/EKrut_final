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
 * NotRegisterCostumerController is a class that controls the functionality of
 * the Not Register Costumer Screen.
 * 
 * It allows the user to log out or exit the application.
 * 
 * @author Ron
 * 
 * @version 1.0
 */
public class NotRegisterCostumerController {
	FXMLLoader loader = new FXMLLoader();
	@FXML
	private Button btnExit;

	@FXML
	private Button btnLogOut;

	@FXML
	private ImageView ekrutLogo;

	@FXML
	private Label lblErrorOnDetails;
	SetSceneController newScreen = new SetSceneController();

	/**
	 * 
	 * The initialize method is called when the screen is loaded. It sets the ekrut
	 * logo on the screen
	 */
	public void initialize() {

		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		ekrutLogo.setImage(image);
	}

	/**
	 * 
	 * The clickOnLogout method is called when the logout button is clicked.
	 * 
	 * It closes the current window and redirects the user to the login screen.
	 * 
	 */
	@FXML
	void clickOnLogout(ActionEvent event) {

		newScreen.exitOrLogOut(event, true);
	}

	/**
	 * 
	 * The getExitBtn method is called when the exit button is clicked. It closes
	 * the current window and exits the application.
	 * 
	 * @param event - the event that triggered the method call
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.exitOrLogOut(event, true);
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

}
