package clientControllers;

import java.io.IOException;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AreaManager_ReportChooseController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnShowReport;

    @FXML
    private Button btnexit1;

    @FXML
    private ComboBox<?> cmbDevice;

    @FXML
    private ComboBox<?> cmbMonth;

    @FXML
    private ComboBox<?> cmbType;

    @FXML
    private ComboBox<?> cmbYear;

    @FXML
    void clickBtnBack(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/AreaManager_MainView.fxml"));
		
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
    void clickBtnShowReport(ActionEvent event) {

    }

    @FXML
    void getExitBtn(ActionEvent event) {
		ClientUI.chat.accept("Disconnect");
		System.exit(0);	
    }

}
