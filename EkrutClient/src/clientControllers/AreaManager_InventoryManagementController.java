package clientControllers;

import common.SetScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AreaManager_InventoryManagementController {

    @FXML
    private Button btBack;

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

    @FXML
    void clickBtnBack(ActionEvent event) {
    	
    	SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_MainView.fxml");

    }

    @FXML
    void clickBtnSetTreshold(ActionEvent event) {
    	SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_TresholdSet.fxml");
    }

    @FXML
    void clickBtnStockCalls(ActionEvent event) {
    	SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_InventoryCalls.fxml");

    }

    @FXML
    void getExitBtn(ActionEvent event) {
		//ClientUI.chat.accept("Disconnect");
		System.exit(0);	
    }

}