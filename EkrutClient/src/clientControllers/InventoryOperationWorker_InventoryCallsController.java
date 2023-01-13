package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import entities.InventoryCall;
import entities.Message;
import enums.CallStatus;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class is the controller for the
 * InventoryOperationWorker_InventoryCallsView screen. It allows an inventory
 * operation worker to view and update inventory calls.
 * 
 * @author Dolev Seri
 *
 */
public class InventoryOperationWorker_InventoryCallsController {

	@FXML
	private Button btnBack;

	@FXML
	private Button btnexit;

	@FXML
	private TableColumn<InventoryCall, Integer> clCallID;

	@FXML
	private TableColumn<InventoryCall, CallStatus> clStatus;

	@FXML
	private TableColumn<InventoryCall, String> clDeviceName;

	@FXML
	private TableColumn<InventoryCall, String> clProduct;

	@FXML
	private TableView<InventoryCall> tblCalls;
	
	@FXML
	private ImageView picture;
	
	@FXML
	private ImageView logo;

	private TableViewController myTable = new TableViewController();
	private String area = ChatClient.userController.getUser().getRegion().toString();
	private SetSceneController scene = new SetSceneController();

	/**
	 * 
	 * Initializes the table view and sets the selection mode to multiple. It also
	 * calls the setColumns and setTableItems methods.
	 */
	@FXML
	public void initialize() {
		Image image = new Image("/images/inventortMana.jpeg");
		picture.setImage(image);
		
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);
		tblCalls.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumns();
		setTableItems();
	}

	/**
	 * Handles the event when the "Inventory Update" button is clicked.
	 *
	 * @param event the event triggered by clicking the button
	 *
	 */
	@FXML
	void clickInventoryUpdateBtn(ActionEvent event) {
		InventoryCall selectedCall = tblCalls.getSelectionModel().getSelectedItem();
		if (selectedCall == null) {
			scene.popUpMessage("You need to chose call!");
		}
		else {
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			scene.setScreenWithData(new Stage(), 
					"/clientGUI/InventoryOperationWorker_UpdateProductQuantity.fxml",
					selectedCall);	
		}
	}

	/**
	 * 
	 * Handles the event when the "Back" button is clicked. Navigates to the
	 * InventoryOperationWorker_MainView screen.
	 * 
	 * @param event the event triggered by clicking the button
	 */
	@FXML
	void clickBackBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/InventoryOperationWorker_MainView.fxml");
	}

	/**
	 * 
	 * Handles the event when the "Exit" button is clicked. Exits the application or
	 * logs out, depending on the value of the parameter.
	 * 
	 * @param event the event triggered by clicking the button
	 * @param exit  if true, exits the application. If false, logs out the user.
	 */
	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

	/**
	 * 
	 * Initializes the columns in the table view.
	 */
	private void setColumns() {
		myTable.setColumn(clCallID, "callID");
		myTable.setColumn(clStatus, "Status");
		myTable.setColumn(clDeviceName, "deviceName");
		myTable.setColumn(clProduct, "productName");

	}

	/**
	 * 
	 * Populates the table view with inventory calls for the current area. Only
	 * calls with status "OPEN" will be displayed.
	 */

	private void setTableItems() {
		tblCalls.getItems().clear();
		ArrayList<String> areaAndStatus = new ArrayList<>();
		areaAndStatus.addAll(Arrays.asList(area, CallStatus.OPEN.toString()));
		ClientUI.chat.accept(new Message(Request.Get_Inventory_Calls_By_Area, areaAndStatus));
		tblCalls.setItems(ChatClient.inventoryCallController.getAreaCalls());
	}

}