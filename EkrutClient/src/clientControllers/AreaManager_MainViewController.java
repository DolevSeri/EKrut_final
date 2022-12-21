package clientControllers;

import java.io.IOException;

import client.ClientUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AreaManager_MainViewController{
	//AreaManager_MainViewController myController;
	@FXML
	private Button btnexit;

	@FXML
	private Button btnViewMonthlyReports;

	@FXML
	private Button btnInventoryMangement;

	@FXML
	private Button btnLogOut;

	@FXML
	private Button btnUserManagement;


	@FXML
	void clickBtnLogOut(ActionEvent event) {

	}

//	public static void main(String[] args) {
//		launch();
//
//	}
//	
//	@Override 
//	public void start(Stage primaryStage) throws Exception {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/AreaManager_MainView.fxml"));
//		Parent root;
//		try {
//			root = loader.load();
//			my = loader.getController();
//			Scene scene = new Scene(root);
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@FXML
	void clickBtnUserManagement(ActionEvent event) {
//		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/AreaManager_UsersConfirmationForm.fxml"));
//		//Parent root;
//		try {
//			root = loader.load();
//			Scene scene = new Scene(root);
//			Stage primaryStage = new Stage();
//			primaryStage.setScene(scene);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/AreaManager_UsersConfirmationForm.fxml"));
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
	void clickBtnViewMonthlyReports(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/AreaManager_ReportChoose.fxml"));
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
	void clickBtnInventoryMangement(ActionEvent event) {

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/AreaManager_InventoryManagementForm.fxml"));
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
	void clickExitBtn(ActionEvent event) {
		ClientUI.chat.accept("Disconnect");
		System.exit(0);	
	}

}
