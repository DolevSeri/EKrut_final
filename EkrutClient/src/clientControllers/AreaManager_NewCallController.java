package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class AreaManager_NewCallController {

    @FXML
    private Button btnSend;

    @FXML
    private Button btnexit;

    @FXML
    private ComboBox<String> cmbDevice;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private TextArea txtaCallDetails;
    
	SetSceneController scene = new SetSceneController();


    @FXML
    void clickBtnSend(ActionEvent event) {
        ArrayList<String> fields = new ArrayList<String>(Arrays.asList(cmbDevice.getValue()));
        if(fields.contains(null)) {
        	lblErrorMsg.setVisible(true);
        }

    }
    
    public void initialize() {
		
    	lblErrorMsg.setVisible(false);

    	
    }

    @FXML
    void clickComboDevice(ActionEvent event) {


    }

    @FXML
    void getExitBtn(ActionEvent event) {
    	scene.back(event, "/clientGUI/AreaManager_InventoryCalls.fxml");

    }

}
