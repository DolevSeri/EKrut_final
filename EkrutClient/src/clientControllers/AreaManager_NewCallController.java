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
 * This is the controller class for the new call view of the area manager. It
 * provides a form for creating a new call and sending it to the server.
 * 
 * @author Dolev Seri
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
	private Label lblCreated;

	@FXML
	private TextArea txtaCallDetails;

	SetSceneController scene = new SetSceneController();

	/**
	 * Handles the send button click event. Validates the form and sends the new
	 * call to the server if all fields are filled.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickBtnSend(ActionEvent event) {
		lblErrorMsg.setVisible(false);
		lblCreated.setVisible(false);
		ArrayList<String> fields = new ArrayList<String>(Arrays.asList(cmbDevice.getValue(), cmbProduct.getValue()));
		if (fields.contains(null)) {
			lblErrorMsg.setVisible(true);
		} else {
			ClientUI.chat.accept(new Message(Request.Create_Inventory_Call, fields));
			lblCreated.setVisible(true);
			try {
				Thread.sleep(1500);
				scene.back(event, "/clientGUI/AreaManager_InventoryCalls.fxml");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * The initialize method is called by JavaFX when the view is being set up. It
	 * hides certain labels, disables the product combo box, sets the prompt text
	 * for the product combo box, and gets the list of devices for the user's area
	 * to populate the device combo box.
	 */
	public void initialize() {
		lblErrorMsg.setVisible(false);
		lblCreated.setVisible(false);
		cmbProduct.setDisable(true);
		cmbProduct.setPromptText("Choose Device First");
		String area = ChatClient.userController.getUser().getRegion().toString();
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, area));
		cmbDevice.getItems().addAll(ChatClient.deviceController.getAreaDevicesNames());
	}

	/**
	 * Handles the action of the device combo box being clicked. It clears the items
	 * in the product combo box, enables the product combo box, gets the list of
	 * products for the chosen device, and sets the prompt text for the product
	 * combo box.
	 *
	 * @param event the action event of the combo box being clicked
	 * 
	 */
	@FXML
	void clickComboDevice(ActionEvent event) {
		cmbProduct.getItems().clear();
		cmbProduct.setDisable(false);
		String deviceChosen = null;
		try {
			deviceChosen = cmbDevice.getValue().toString();
		} catch (NullPointerException e) {
		}
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
