package clientControllers;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * 
 * The SalesWorkerMainScreenController is the controller class that handles the
 * main screen of the Sales Worker user. It allows the Sales Worker to create
 * new sale patterns, activate existing sale patterns and log out or exit the
 * system. The class is responsible for displaying the main screen of the Sales
 * Worker user and handling the actions of the user.
 * 
 * @author Ron Lahiani
 * @author Peleg Oanuno
 */
public class SalesWorkerMainScreenController {

	@FXML
	private Button btnActivate;

	@FXML
	private Button btnCreatePattern;

	@FXML
	private Button btnLogOut;

	@FXML
	private Button btnexit1;

	@FXML
	private Label lblWelcome;

	@FXML
	private Label lblArea;

	@FXML
	private ImageView logoIcon;
	SetSceneController newScreen = new SetSceneController();

	/**
	 * 
	 * Initialize method is called when the scene is loaded. It sets the welcome
	 * message, the region of the user and the logo.
	 */
	public void initialize() {
		lblWelcome.setText("Welcome Back " + ChatClient.userController.getUser().getFirstName() + " "
				+ ChatClient.userController.getUser().getLastName() + "!");
		lblArea.setText(ChatClient.userController.getUser().getRegion().toString());
		Image image = new Image("/images/SaleWOrkerImageMainScreen.png");
		logoIcon.setImage(image);
	}

	/**
	 * 
	 * ClickOnCreateSalePattern method is called when the user clicks on the "Create
	 * Sale Pattern" button. It allows the Sales Worker to create new sale patterns.
	 * 
	 * @param event the event that triggered the method call.
	 */
	@FXML
	void ClickOnCreateSalePattern(ActionEvent event) {
		System.out.println("Sales Worker want to create pattern");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_CreatePattern.fxml");
	}

	/**
	 * 
	 * clickOnActivateSale method is called when the user clicks on the "Activate
	 * Sale" button. It allows the Sales Worker to activate existing sale patterns.
	 * 
	 * @param event the event that triggered the method call.
	 */
	@FXML
	void clickOnActivateSale(ActionEvent event) {
		System.out.println("Sales Worker want to Activate sale");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_ActivateSale.fxml");
	}

	/**
	 *
	 * 
	 * @param event the event that triggers this method, usually a button click This
	 *              method handles the log out functionality for the user. It hides
	 *              the current window and shows the login screen.
	 */
	@FXML
	void clickOnLogOut(ActionEvent event) {
		newScreen.exitOrLogOut(event, true);
	}

	/**
	 *
	 * 
	 * @param event the event that triggers this method, usually a button click This
	 *              method handles the exit functionality for the application. It
	 *              closes the current window and exits the application.
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

}
