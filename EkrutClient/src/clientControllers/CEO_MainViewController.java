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
 * This class represents the controller for the CEO Main View FXML file. It
 * handles the user's interactions with the view and navigates to the
 * appropriate views.
 * 
 * @author Inbar Mizrahi
 */
public class CEO_MainViewController {

	@FXML
	private Button btnexit1;

	@FXML
	private Button btnReportView;

	@FXML
	private Button btnLogOut;

	@FXML
	private ImageView logo;
	@FXML
	private Label lblWelcome;

	SetSceneController scene = new SetSceneController();

	@FXML
	public void initialize() {
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		logo.setImage(image);
		
		lblWelcome.setText("Welcome Back " + ChatClient.userController.getUser().getFirstName() + " "
				+ ChatClient.userController.getUser().getLastName() + "!");
	}
	
	/**
	 * Handles the 'Exit' button click event. Closes the application.
	 *
	 * @param event the ActionEvent object representing the button click event
	 * @throws Exception if an exception occurs while setting the scene
	 */
	@FXML
	void getExitBtn(ActionEvent event) throws Exception {

		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);
		scene.exitOrLogOut(event, false);
	}

	/**
	 * Handles the 'Log Out' button click event. Navigates the user back to the
	 * login screen.
	 *
	 * @param event the ActionEvent object representing the button click event
	 * @throws Exception if an exception occurs while setting the scene
	 */
	@FXML
	void ClickLogOutBtn(ActionEvent event) throws Exception {
		scene.exitOrLogOut(event, true);
	}

	/**
	 * Handles the 'View Monthly Report' button click event. Navigates the user to
	 * the Choose Report view.
	 *
	 * @param event the ActionEvent object representing the button click event
	 * @throws Exception if an exception occurs while setting the scene
	 */
	@FXML
	void ViewMonthllyReport(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.setScreen(new Stage(), "/clientGUI/ChooseReport.fxml");
	}

}
