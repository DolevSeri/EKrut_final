package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import enums.Request;
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
    private ComboBox<String> cmbProduct;

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
	    ArrayList<String> fields = new ArrayList<String>(Arrays.asList(cmbDevice.getValue(), cmbProduct.getValue()));
	    if(fields.contains(null)) {
	        lblErrorMsg.setVisible(true);
	    }
	    else {
	    	ClientUI.chat.accept(new Message(Request.Create_Inventory_Call, fields));
	    }
	}

	/**
	 * Initializes the view by hiding the error message label.
	 */
	public void initialize() {
	    lblErrorMsg.setVisible(false);
	    cmbProduct.setDisable(true);
	    cmbProduct.setPromptText("Choose Device First");
	    String area = ChatClient.userController.getUser().getRegion().toString();
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, area));
		cmbDevice.getItems().addAll(ChatClient.deviceController.getAreaDevicesNames());
	}

	/**
	 * Handles the device combo box selection event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickComboDevice(ActionEvent event) {
		cmbProduct.getItems().clear();
		cmbProduct.setDisable(false);
		String deviceChosen = null;
		try {
			deviceChosen = cmbDevice.getValue().toString();
		}catch (NullPointerException e) {}
		ClientUI.chat.accept(new Message(Request.Get_Products, deviceChosen));
		cmbProduct.getItems().addAll(ChatClient.productCatalogController.getProductsInDevicesNames());
		cmbProduct.setPromptText("Choose Product");
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
