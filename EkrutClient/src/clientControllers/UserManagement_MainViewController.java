package clientControllers;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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


    @FXML
	public void initialize() {
		lblWelcome.setText("Welcome Back "+ ChatClient.userController.getUser().getFirstName()
				+" "+ ChatClient.userController.getUser().getLastName()+"!");
	}
	
    
    @FXML
    void clickBtnCreateAccount(ActionEvent event) {
    	scene.createOrUpdateClient(false);
    }

    @FXML
    void clickBtnUpdate(ActionEvent event) {
    	scene.createOrUpdateClient(true);

    }

    @FXML
    void clickOnLogout(ActionEvent event) {
    	scene.exitOrLogOut(event, true);
    }

    @FXML
    void getExitBtn(ActionEvent event) {
    	scene.exitOrLogOut(event, false);
    }

}
