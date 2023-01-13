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
 * This class is the controller for the InventoryOperationWorker_MainView screen.
 * It allows the user to navigate to the inventory calls view and log out.
 * 
 * @author Inbar Mizrahi
 */
public class InventoryOperationWorker_MainViewController {

    @FXML
    private Button btnexit;

    @FXML
    private Label lblWelcome;

    @FXML
    private Button btnCallsView;

    @FXML
    private Button btnLogOut;
    
	@FXML
	private ImageView logo;

    private SetSceneController scene = new SetSceneController();
    
    
    /**
	 * Initializes the screen and sets the text of the welcome label to a
	 * welcome message for the user.
	 */
	@FXML
	public void initialize() {
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);
		lblWelcome.setText("Welcome Back "+ ChatClient.userController.getUser().getFirstName()
				+" "+ ChatClient.userController.getUser().getLastName()+"!");
	}
    
	/**
	 * Handles the event when the "Inventory Calls View" button is clicked.
	 * Navigates to the InventoryOperationWorker_CallsListView screen.
	 * 
	 * @param event the event triggered by clicking the button
	 */

    @FXML
    void ClickCallsViewBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    	scene.setScreen(new Stage(), "/clientGUI/InventoryOperationWorker_CallsListView.fxml");
    }
    
    
    /**
	 * Handles the event when the "Log Out" button is clicked. Logs out the user.
	 * 
	 * @param event the event triggered by clicking the button
	 */
    @FXML
    void clickBtnLogOut(ActionEvent event) {
	    scene.exitOrLogOut(event, true);
    }
    
    
    /**
     * Handles the event when the "Exit" button is clicked. Exits the application.
     * 
     * @param event the event triggered by clicking the button
     */
    @FXML
    void clickExitBtn(ActionEvent event) {
	    scene.exitOrLogOut(event, false);
    }

}
