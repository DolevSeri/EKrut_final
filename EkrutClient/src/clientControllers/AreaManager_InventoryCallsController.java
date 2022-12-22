package clientControllers;

import common.SetScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    	SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_InventoryManagementForm.fxml");
    }

    @FXML
    void clickCreateNewCallBtn(ActionEvent event) {

    }

    @FXML
    void getExitBtn(ActionEvent event) {
		//ClientUI.chat.accept("Disconnect");
		System.exit(0);
    }


}