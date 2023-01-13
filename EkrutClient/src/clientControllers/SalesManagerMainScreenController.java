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
 * The SalesManagerMainScreenController class handles the logic and actions of
 * the Sales Manager Main Screen GUI.
 * 
 * @author Ron,Peleg
 */
public class SalesManagerMainScreenController {

	@FXML
	private Button btnCreateSale;

	@FXML
	private Button btnLogOut;

	@FXML
	private Button btnexit1;

	@FXML
	private Label lblArea;
	@FXML
	private Label lblWelcome;

	@FXML
	private ImageView logoIcon;

	SetSceneController newScreen = new SetSceneController();

	/**
	 * This method is called automatically when the GUI is loaded. It initializes
	 * the welcome message, the area label and the background image.
	 */
	public void initialize() {
		lblWelcome.setText("Welcome Back " + ChatClient.userController.getUser().getFirstName() + " "
				+ ChatClient.userController.getUser().getLastName() + "!");
		lblArea.setText(ChatClient.userController.getUser().getRegion().toString());
		Image image = new Image("/images/SaleManagerMainScreen.png");
		logoIcon.setImage(image);
	}

	/**
	 * This method is called when the user clicks on the "Create Sale" button. It
	 * hides the current window and opens the Sales Manager Create Sale window.
	 * 
	 * @param event the action event that triggers this method
	 */
	@FXML
	void clickOnCreateSaleBtn(ActionEvent event) {
		System.out.println("Sales Manager want to create sale");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesManager_CreateSale.fxml");
	}

	/**
	 * This method is called when the user clicks on the "Log Out" button. It logs
	 * the user out of the system.
	 * 
	 * @param event the action event that triggers this method
	 */
	@FXML
	void clickOnLogOut(ActionEvent event) {
		newScreen.exitOrLogOut(event, true);
	}

	/**
	 * This method is called when the user clicks on the "Exit" button. It exits the
	 * system.
	 * 
	 * @param event the action event that triggers this method
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

}
