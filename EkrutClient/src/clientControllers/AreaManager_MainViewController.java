package clientControllers;

import java.io.IOException;


import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AreaManager_MainViewController{
	//AreaManager_MainViewController myController;
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



	@FXML
	void clickBtnLogOut(ActionEvent event) {
		scene.exitOrLogOut(event, true);
	}

	@FXML
	void clickBtnUserManagement(ActionEvent event) {
		
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_UsersConfirmationForm.fxml");
	}

	@FXML
	void clickBtnViewMonthlyReports(ActionEvent event) {
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_ReportChoose.fxml");
	}
	
	@FXML
	void clickBtnInventoryManagement(ActionEvent event) {
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_InventoryManagementForm.fxml");
	}


	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

}