package clientControllers;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AreaManager_InventoryManagementController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCalls;

    @FXML
    private Button btnSetTreshold;

    @FXML
    private Button btnexit;
    
    @FXML
    private ImageView imgInventory;

    @FXML
    private ImageView imgLogo;
    
    SetSceneController scene = new SetSceneController();

    @FXML
    void clickBtnBack(ActionEvent event) {
    	scene.back(event, "/clientGUI/AreaManager_MainView.fxml");
    }

    @FXML
    void clickBtnSetTreshold(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_TresholdSet.fxml");
    }

    @FXML
    void clickBtnStockCalls(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_InventoryCalls.fxml");

    }

    @FXML
    void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);	
    }

}