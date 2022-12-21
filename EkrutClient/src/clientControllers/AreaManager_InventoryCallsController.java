package clientControllers;

import java.io.IOException;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AreaManager_InventoryCallsController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCreateCall;

    @FXML
    private Button btnexit1;

    @FXML
    private GridPane gpCallsList;

    @FXML
    void clickBackBtn(ActionEvent event) {
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
    void clickCreateNewCallBtn(ActionEvent event) {

    }

    @FXML
    void getExitBtn(ActionEvent event) {
		ClientUI.chat.accept("Disconnect");
		System.exit(0);
    }


}
