package clientControllers;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AreaManager_UsersConfirmationFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnexit1;

    @FXML
    private AnchorPane gpUsers;

    @FXML
    private GridPane gpUsersList;

    @FXML
    void clickBackBtn(ActionEvent event) {
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

}
