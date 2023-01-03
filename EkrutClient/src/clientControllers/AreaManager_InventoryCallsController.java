package clientControllers;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
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
    
	SetSceneController scene = new SetSceneController();

    @FXML
    void clickBackBtn(ActionEvent event) {
    	scene.back(event, "/clientGUI/AreaManager_InventoryManagementForm.fxml");
    }

    @FXML
    void clickCreateNewCallBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    	scene.setScreen(new Stage(), "/clientGUI/AreaManager_NewCallForm.fxml");

    }

    @FXML
    void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
    }


}