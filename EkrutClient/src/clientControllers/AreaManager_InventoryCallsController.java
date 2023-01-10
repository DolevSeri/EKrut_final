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

public class AreaManager_InventoryCallsController {

    @FXML
    private Button btnexit;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCreateCall;

    @FXML
    private Label lblError;

    @FXML
    private ComboBox<String> cmbDevice;

    @FXML
    private ComboBox<String> cmbProduct;


	private String area = ChatClient.userController.getUser().getRegion().toString();
	private SetSceneController scene = new SetSceneController();

	/**
	 * The initialize method is called by JavaFX when the view is being set up. It
	 * hides certain labels, disables the product combo box, sets the prompt text
	 * for the product combo box, and gets the list of devices for the user's area
	 * to populate the device combo box.
	 */
	public void initialize() {
		cmbProduct.setDisable(true);
		cmbProduct.setPromptText("Choose Device First");
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, area));
		cmbDevice.getItems().addAll(ChatClient.deviceController.getAreaDevicesNames());
	}

	/**
	 * Handles the action of clicking the back button.
	 *
	 * @param event the action event that triggered this method call
	 */
	@FXML
	void clickBackBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/AreaManager_InventoryManagementForm.fxml");
	}

	@FXML
	void clickComboDevice(ActionEvent event) {
		cmbProduct.getItems().clear();
		cmbProduct.setDisable(false);
		String deviceChosen = null;
		try {
			deviceChosen = cmbDevice.getValue().toString();
		} catch (NullPointerException e) {
		}
		
		ClientUI.chat.accept(new Message(Request.Get_Products_under_thres, deviceChosen));
		cmbProduct.getItems().addAll(ChatClient.productCatalogController.getProductsInDevicesNames());
		cmbProduct.setPromptText("Choose Product");
	}
	
	/**
	 * Handles the action of clicking the create new call button.
	 * if the product that the manager wants to open call on
	 * is already has an open call on it - an error message will pop.
	 *
	 * @param event the action event that triggered this method call
	 */
	@FXML
	void clickCreateNewCallBtn(ActionEvent event) {
		ArrayList<String> fields = new ArrayList<String>(Arrays.asList(cmbDevice.getValue(), cmbProduct.getValue()));
		if (fields.contains(null)) {
			scene.popUpMessage("You need to choose Device and Product!");
		} else {
			ClientUI.chat.accept(new Message(Request.Create_Inventory_Call, fields));
			if(ChatClient.inventoryCallController.isCreated()) {
				scene.popUpMessage("The call was created succesfully!");
			}
			else
				scene.popUpMessage("NOTICE: The call is already exist for this product!");
		}

	}

	/**
	 * Handles the action of clicking the close call button. If no calls are
	 * selected or if any of the selected calls have a status other than DONE, an
	 * error message is displayed. Otherwise, the selected calls are closed and the
	 * table is updated.
	 * 
	 * @param selectedCalls -the list of selected calls in the table
	 * @param allDone       -a flag indicating whether all selected calls have a
	 *                       status of DONE
	 * @param callsToClose  -the list of calls to be closed
	 * 
	 *
	 * @param event         the action event that triggered this method call
	 */

//	@FXML
//	void clickCloseCallBtn(ActionEvent event) {
//		ObservableList<InventoryCall> selectedCalls = tblCalls.getSelectionModel().getSelectedItems();
//		if (selectedCalls.isEmpty()) {
//			lblError.setText("Error: No calls selected");
//			lblError.setVisible(true);
//		} else {
//			boolean allDone = true;
//			for (InventoryCall call : selectedCalls) {
//				if (call.getStatus() != CallStatus.DONE) {
//					allDone = false;
//					break;
//				}
//			}
//			if (!allDone) {
//				lblError.setText("Error: Cannot close calls with a status other than" + CallStatus.DONE.toString());
//				lblError.setVisible(true);
//			} else {
//				lblError.setVisible(false);
//				ArrayList<InventoryCall> callsToClose = new ArrayList<>(selectedCalls);
//				ClientUI.chat.accept(new Message(Request.Inventory_Calls_To_Close, callsToClose));
//				setTableItems();
//			}
//		}
//	}

	/**
	 * Handles the action of clicking the exit button.
	 *
	 * @param event the action event that triggered this method call
	 */
	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}
}