package clientControllers;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The AreaManager_InventoryManagementController class is the controller for the 
 * Area Manager Inventory Management view. It handles the actions of the buttons and 
 * displays the appropriate views based on the button clicked.
 *
 * @author  Dolev Seri
 * @version none
 * @since   December 2022
 */
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
    
    /**
     * Changes the scene to the previous view when the back button is clicked.
     * 
     * @param event the action event of the button being clicked
     */
    @FXML
    void clickBtnBack(ActionEvent event) {
    	
		Image image = new Image("/images/inventory_dilemma.jpg");
		imgInventory.setImage(image);
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		imgLogo.setImage(imagelogo);
    	scene.back(event, "/clientGUI/AreaManager_MainView.fxml");
    }
    
    /**
     * Changes the scene to the treshold set view when the set treshold button is clicked.
     * 
     * @param event the action event of the button being clicked
     */
    @FXML
    void clickBtnSetTreshold(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_TresholdSet.fxml");
    }
    
    /**
     * Changes the scene to the inventory calls view when the stock calls button is clicked.
     * 
     * @param event the action event of the button being clicked
     */
    @FXML
    void clickBtnStockCalls(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_InventoryCalls.fxml");

    }
    
    /**
     * Exits or logs out of the application when the exit button is clicked.
     * 
     * @param event the action event of the button being clicked
     */
    @FXML
    void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);	
    }

}