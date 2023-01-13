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

public class DeliveryOperation_MainViewController {

    @FXML
    private Button btnDeliveryMan;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnexit;

    @FXML
    private Label lblWelcome;
    
	@FXML
	private ImageView logo;
	
	SetSceneController scene = new SetSceneController();

	/**
	 * The initialize method is called by JavaFX when the view is being set up. It
	 * sets the text of the welcome label to include the user's first and last name.
	 *
	 */
    public void initialize() {
		Image imagelogo = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);
		
    	lblWelcome.setText("Welcome Back " + ChatClient.userController.getUser().getFirstName() + " "
				+ ChatClient.userController.getUser().getLastName() + "!");
    }

	/**
	 * Handles the user management button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
    @FXML
    void clickBtnDelivery(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    	scene.setScreen(new Stage(), "/clientGUI/DeliveryOperator_ManageDeliveries.fxml");
    }
    
	/**
	 * Handles the exit button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
    @FXML
    void clickBtnExit(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.exitOrLogOut(event, false);
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

}
