package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * This is the controller class for the main view of the area manager.
 * It provides functions for managing users, viewing monthly reports, 
 * managing inventory, logging out, and exiting the application.
 */
public class AreaManager_MainViewController{
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
	
	SetSceneController scene = new SetSceneController();

	/**
	 * Handles the log out button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnLogOut(ActionEvent event) {
	    scene.exitOrLogOut(event, true);
	}

	/**
	 * Handles the user management button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnUserManagement(ActionEvent event) {
	    scene.setScreen(new Stage(), "/clientGUI/AreaManager_UsersConfirmationForm.fxml");
	}

	/**
	 * Handles the view monthly reports button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnViewMonthlyReports(ActionEvent event) {
	    scene.setScreen(new Stage(), "/clientGUI/ChooseReport.fxml");
	}

	/**
	 * Handles the inventory management button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnInventoryManagement(ActionEvent event) {
	    scene.setScreen(new Stage(), "/clientGUI/AreaManager_InventoryManagementForm.fxml");
	}

	/**
	 * Handles the exit button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickExitBtn(ActionEvent event) {
	    scene.exitOrLogOut(event, false);
	}

}