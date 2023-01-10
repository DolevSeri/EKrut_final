package clientControllers;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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


    public boolean isUpdate() {
		return isUpdate;
	}


	public void setUpdate(boolean isUpdate) {
		UserManagement_MainViewController.isUpdate = isUpdate;
	}


	@FXML
	public void initialize() {
		lblWelcome.setText("Welcome Back "+ ChatClient.userController.getUser().getFirstName()
				+" "+ ChatClient.userController.getUser().getLastName()+"!");
	}
	
    
    @FXML
    void clickBtnCreateAccount(ActionEvent event) {
    	this.setUpdate(false);
    	scene.setScreen(new Stage(), "/clientGUI/UsersManagement_UsersDataView.fxml");
    	//scene.createOrUpdateClient(false);
    }

    @FXML
    void clickBtnUpdate(ActionEvent event) {
    	this.setUpdate(true);
    	scene.setScreen(new Stage(), "/clientGUI/UsersManagement_UsersDataView.fxml");

    	//scene.createOrUpdateClient(true);

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
