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
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

	private TableViewController myTable = new TableViewController();
	private String area = ChatClient.userController.getUser().getRegion().toString();
	private SetSceneController scene = new SetSceneController();

	@FXML
	public void initialize() {
		tblCalls.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumns();
		setTableItems();
	}

	  @FXML
	    void clickInventoryUpdateBtn(ActionEvent event) {
		  
	    }
	  
	@FXML
	void clickBackBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/InventoryOperationWorker_MainView.fxml");
	}

	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

	private void setColumns() {
		myTable.setColumn(clCallID, "callID");
		myTable.setColumn(clStatus, "Status");
		myTable.setColumn(clDeviceName, "deviceName");
		myTable.setColumn(clProduct, "productName");

	}

	private void setTableItems() {
		tblCalls.getItems().clear();
		ArrayList<String> areaAndStatus = new ArrayList<>();
		areaAndStatus.addAll(Arrays.asList(area, CallStatus.OPEN.toString()));
		ClientUI.chat.accept(new Message(Request.Get_Inventory_Calls_By_Area, areaAndStatus));
		tblCalls.setItems(ChatClient.inventoryCallController.getAreaCalls());
	}

}