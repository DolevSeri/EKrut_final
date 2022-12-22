package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AreaManager_SetTresholdController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnexit1;

    @FXML
    private GridPane gpDeviceTres;
    
	SetSceneController scene = new SetSceneController();


    @FXML
    void clickBackBtn(ActionEvent event) {
    	scene.back(event, "/clientGUI/AreaManager_InventoryManagementForm.fxml");
    }

    @FXML
    void getExitBtn(ActionEvent event) {
    	scene.exitOrLogOut(event, false);
    }

}