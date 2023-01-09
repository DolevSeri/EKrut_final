package clientControllers;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InventoryOperationWorker_MainViewController {

    @FXML
    private Button btnexit;

    @FXML
    private Label lblWelcome;

    @FXML
    private Button btnCallsView;

    @FXML
    private Button btnLogOut;

    private SetSceneController scene = new SetSceneController();
    
	@FXML
	public void initialize() {
		lblWelcome.setText("Welcome Back "+ ChatClient.userController.getUser().getFirstName()
				+" "+ ChatClient.userController.getUser().getLastName()+"!");
	}
    
    @FXML
    void ClickCallsViewBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    	scene.setScreen(new Stage(), "/clientGUI/InventoryOperationWorker_CallsListView.fxml");
    }

    @FXML
    void clickBtnLogOut(ActionEvent event) {
	    scene.exitOrLogOut(event, true);
    }

    @FXML
    void clickExitBtn(ActionEvent event) {
	    scene.exitOrLogOut(event, false);
    }

}
