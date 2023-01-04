package clientControllers;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import entities.Device;
import entities.Message;
import enums.Devices;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class AreaManager_SetTresholdController {
	private SetSceneController scene = new SetSceneController();	
	@FXML
	private Button btnBack;

	@FXML
	private Label lblRegion;

	@FXML
	private TableView<Device> tblDevice;

	@FXML
	private TableColumn<Device, String>deviceName;

	@FXML
	private TableColumn<Device, Integer> threshold;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnRefresh;

	@FXML
	private Button btnexit;
	@FXML
	private Label lblError;
	private TableViewController myTable = new TableViewController();
	private String area = ChatClient.userController.getUser().getRegion().toString();


	@FXML
	public void initialize() {
		tblDevice.setEditable(true);
		setColumns();
		setTableItems();
	}

	@FXML
	void clickBackBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/AreaManager_MainView.fxml");

	}

	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);

	}

	@FXML
	void clickSaveBtn(ActionEvent event) {
		ArrayList<Device> devicesToUpdate = new ArrayList<>();
		boolean allValuesValid = true;
		for (Device device : tblDevice.getItems()) {
			int thresholdValue = device.getThreshold();
			if (thresholdValue < 0) {
				allValuesValid = false;
				break;
			}
		}

		if (allValuesValid) {
			lblError.setVisible(false);
			devicesToUpdate.addAll(tblDevice.getItems());
			ClientUI.chat.accept(new Message(Request.Threshold_Update_Request,devicesToUpdate));

		} else {
			lblError.setText("Error: Threshold values must be positive integers");
			lblError.setVisible(true);
		}
	}

	@FXML
	void clickBtnRefresh() {
		setTableItems();
	}
	
	@FXML
	private void setColumns() {
		myTable.setColumn(deviceName, "deviceName");
		myTable.setColumn(threshold, "threshold");
		threshold.setCellFactory((cell) -> {
			return new TextFieldTableCell<>(new IntegerStringConverter());});
		myTable.setCellEditable(threshold);
	}
	private void setTableItems() {
		tblDevice.getItems().clear();
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, area));
		tblDevice.setItems(ChatClient.deviceController.getAreaDevices());
	}
}
