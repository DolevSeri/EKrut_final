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
 * This is the controller class for the main view of the area manager. It
 * provides functions for managing users, viewing monthly reports, managing
 * inventory, logging out, and exiting the application.
 * 
 * @author Eden Bar
 */
public class AreaManager_MainViewController {
	@FXML
	private Button btnexit;

	@FXML
	private Button btnViewMonthlyReports;

	@FXML
	private Button btnInventoryManagement;

	@FXML
	private Button btnLogOut;

	@FXML
	private Button btnUserManagement;
	
    @FXML
    private ImageView logo;

	@FXML
	private Label lblWelcome;
	SetSceneController scene = new SetSceneController();

	/**
	 * The initialize method is called by JavaFX when the view is being set up. It
	 * sets the text of the welcome label to include the user's first and last name.
	 *
	 */
	@FXML
	public void initialize() {
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		logo.setImage(image);
		
		lblWelcome.setText("Welcome Back " + ChatClient.userController.getUser().getFirstName() + " "
				+ ChatClient.userController.getUser().getLastName() + "!");
	}

	/**
	 * Handles the log out button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnLogOut(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.exitOrLogOut(event, true);
	}

	/**
	 * Handles the user management button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnUserManagement(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_CostumerApproval.fxml");
	}

	/**
	 * Handles the view monthly reports button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnViewMonthlyReports(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.setScreen(new Stage(), "/clientGUI/ChooseReport.fxml");
	}

	/**
	 * Handles the inventory management button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnInventoryManagement(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_InventoryManagementForm.fxml");
	}

	/**
	 * Handles the exit button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickExitBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.exitOrLogOut(event, false);
	}

}