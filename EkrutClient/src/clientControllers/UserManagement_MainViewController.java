package clientControllers;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * 
 * This class represents the controller for the UserManagement_MainView.fxml
 * file. It handles the actions performed by the user on the main view of the
 * user management section.
 * 
 * @author Dolev Seri and Eden Bar
 */
public class UserManagement_MainViewController {

	@FXML
	private Button btnCreateAccount;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnLogOut;

	@FXML
	private Button btnUpdate;

	@FXML
	private Label lblWelcome;

	SetSceneController scene = new SetSceneController();
	private static boolean isUpdate = false;

	/**
	 * Returns the value of the isUpdate variable
	 * 
	 * @return boolean value indicating whether the user wants to update an account
	 *         or create a new one
	 */
	public boolean isUpdate() {
		return isUpdate;
	}

	/**
	 * Sets the value of the isUpdate variable
	 * 
	 * @param isUpdate - a boolean value indicating whether the user wants to update
	 *                 an account or create a new one
	 */
	public void setUpdate(boolean isUpdate) {
		UserManagement_MainViewController.isUpdate = isUpdate;
	}

	/**
	 * Initializes the view, setting the welcome message to include the user's first
	 * and last name
	 */
	@FXML
	public void initialize() {
		lblWelcome.setText("Welcome Back " + ChatClient.userController.getUser().getFirstName() + " "
				+ ChatClient.userController.getUser().getLastName() + "!");
	}

	/**
	 * Handles the button click event for creating a new account. Sets the isUpdate
	 * variable to false and opens the UsersManagement_UsersDataView.fxml file.
	 * 
	 * @param event - the button click event
	 */
	@FXML
	void clickBtnCreateAccount(ActionEvent event) {
		this.setUpdate(false);
		scene.setScreen(new Stage(), "/clientGUI/UsersManagement_UsersDataView.fxml");
		// scene.createOrUpdateClient(false);
	}

	/**
	 * Handles the button click event for updating an existing account. Sets the
	 * isUpdate variable to true and opens the UsersManagement_UsersDataView.fxml
	 * file.
	 * 
	 * @param event - the button click event
	 */
	@FXML
	void clickBtnUpdate(ActionEvent event) {
		this.setUpdate(true);
		scene.setScreen(new Stage(), "/clientGUI/UsersManagement_UsersDataView.fxml");

		// scene.createOrUpdateClient(true);

	}

	/**
	 * Handles the button click event for logging out. calls the exitOrLogOut method
	 * of the SetSceneController to log the user out and return to the login screen.
	 * 
	 * @param event - the button click event
	 */
	@FXML
	void clickOnLogout(ActionEvent event) {
		scene.exitOrLogOut(event, true);
	}

	/**
	 * Handles the event when the "Exit" button is clicked. Exits the application.
	 * 
	 * @param event the event triggered by clicking the button
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

}
