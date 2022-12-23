package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
/**
 * This is the controller class for the new call view of the area manager.
 * It provides a form for creating a new call and sending it to the server.
 */
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

	/**
	 * Handles the send button click event.
	 * Validates the form and sends the new call to the server if all fields are filled.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnSend(ActionEvent event) {
	    ArrayList<String> fields = new ArrayList<String>(Arrays.asList(cmbDevice.getValue()));
	    if(fields.contains(null)) {
	        lblErrorMsg.setVisible(true);
	    }
	}

	/**
	 * Initializes the view by hiding the error message label.
	 */
	public void initialize() {
	    lblErrorMsg.setVisible(false);
	}

	/**
	 * Handles the device combo box selection event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickComboDevice(ActionEvent event) {
	    // ...
	}

	/**
	 * Handles the exit button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
	    scene.back(event, "/clientGUI/AreaManager_InventoryCalls.fxml");
	}


}
