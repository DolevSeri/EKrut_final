package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import entities.InventoryCall;
import entities.Message;
import enums.CallStatus;
import enums.Request;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * This class is the controller for the Area Manager Inventory Calls form. It
 * handles the actions of the buttons and initializes the table view.
 *
 * @author Eden Bar
 */
public class AreaManager_InventoryCallsController {

	@FXML
	private Button btnBack;

	@FXML
	private Button btnCreateCall;

	@FXML
	private Button btnexit;

	@FXML
	private Label lblError;

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
	private Button btnCloseCall;

	private TableViewController myTable = new TableViewController();
	private String area = ChatClient.userController.getUser().getRegion().toString();
	private SetSceneController scene = new SetSceneController();

	/**
	 * Initializes the table view and sets the columns and items.
	 */
	@FXML
	public void initialize() {
		tblCalls.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumns();
		setTableItems();
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

	/**
	 * Handles the action of clicking the create new call button.
	 *
	 * @param event the action event that triggered this method call
	 */
	@FXML
	void clickCreateNewCallBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_NewCallForm.fxml");

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

	@FXML
	void clickCloseCallBtn(ActionEvent event) {
		ObservableList<InventoryCall> selectedCalls = tblCalls.getSelectionModel().getSelectedItems();
		if (selectedCalls.isEmpty()) {
			lblError.setText("Error: No calls selected");
			lblError.setVisible(true);
		} else {
			boolean allDone = true;
			for (InventoryCall call : selectedCalls) {
				if (call.getStatus() != CallStatus.DONE) {
					allDone = false;
					break;
				}
			}
			if (!allDone) {
				lblError.setText("Error: Cannot close calls with a status other than" + CallStatus.DONE.toString());
				lblError.setVisible(true);
			} else {
				lblError.setVisible(false);
				ArrayList<InventoryCall> callsToClose = new ArrayList<>(selectedCalls);
				ClientUI.chat.accept(new Message(Request.Inventory_Calls_To_Close, callsToClose));
				setTableItems();
			}
		}
	}

	/**
	 * Handles the action of clicking the exit button.
	 *
	 * @param event the action event that triggered this method call
	 */
	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

	/**
	 * Sets the columns in the table to display the specified fields.
	 */
	private void setColumns() {
		myTable.setColumn(clCallID, "callID");
		myTable.setColumn(clStatus, "Status");
		myTable.setColumn(clDeviceName, "deviceName");
		myTable.setColumn(clProduct, "productName");

	}
	
	/**
	 * Clears the items in the table and sets them to the list of inventory calls for the current area.
	 */
	private void setTableItems() {
		tblCalls.getItems().clear();
		ArrayList<String> areaAndStatus = new ArrayList<>();
		areaAndStatus.addAll(Arrays.asList(area, null));
		ClientUI.chat.accept(new Message(Request.Get_Inventory_Calls_By_Area, areaAndStatus));
		tblCalls.setItems(ChatClient.inventoryCallController.getAreaCalls());
	}

}