package clientControllers;

import java.awt.event.ActionEvent;
import client.ClientUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class CEO_MainViewController {

    @FXML
    private Button btnexit1;

    @FXML
    private Button btnReportView;

    @FXML
    private Button btnLogOut;

    @FXML
    void getExitBtn(ActionEvent event)  throws Exception  {
    	ClientUI.chat.accept("Disconnect");
		System.exit(0);
		
    }

    @FXML
    void ClickLogOutBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = loader.load(getClass().getResource("/clientGUI/Identification_Interface.fxml").openStream());
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Login screen");
		primaryStage.setScene(scene);		
		primaryStage.show();
    }

    @FXML
    void ViewMonthllyReport(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = loader.load(getClass().getResource("/clientGUI/CEO_ReportChoose.fxml").openStream());
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Login screen");
		primaryStage.setScene(scene);		
		primaryStage.show();

    }
    


}
