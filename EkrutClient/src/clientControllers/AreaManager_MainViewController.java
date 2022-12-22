package clientControllers;

import java.io.IOException;

import client.ClientUI;
import common.SetScene;
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


	@FXML
	void clickBtnLogOut(ActionEvent event) {
		ClientUI.chat.accept("Disconnect");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/Identification_Interface.fxml"));
		try {
			loader.load();
			Parent root = loader.getRoot();
			stage.getScene().setRoot(root);
			stage.sizeToScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@FXML
	void clickBtnUserManagement(ActionEvent event) {
		
		SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_UsersConfirmationForm.fxml");
	}

	@FXML
	void clickBtnViewMonthlyReports(ActionEvent event) {
		SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_ReportChoose.fxml");
	}
	
	@FXML
	void clickBtnInventoryManagement(ActionEvent event) {
		SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_InventoryManagementForm.fxml");
	}


	@FXML
	void clickExitBtn(ActionEvent event) {
		//ClientUI.chat.accept("Disconnect");
		System.exit(0);	
	}

}