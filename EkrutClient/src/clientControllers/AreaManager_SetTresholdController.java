package clientControllers;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import entities.Device;
import entities.Message;
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
	SetSceneController scene = new SetSceneController();
	@FXML
	private Button btnBack;

	@FXML
	private Label lblRegion;

	@FXML
	private TableView<Device> tblDevice;

	@FXML
	private TableColumn<Device, String> deviceName;

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

	@SuppressWarnings("static-access")
	@FXML
	public void initialize() {
		lblRegion.setText(ChatClient.userController.getUser().getRegion().toString());
		setCulomns();
		String area = ChatClient.userController.getUser().getRegion().toString();
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, area));
		tblDevice.setItems(ChatClient.deviceController.getAreaDevices());
		setEditableCell(threshold);
		setTextFieldInCell(threshold);
		tblDevice.setEditable(true);
	}

	@FXML
	void clickBackBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/AreaManager_MainView.fxml");

	}

	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);

	}

	@SuppressWarnings("static-access")
	@FXML
	void clickSaveBtn(ActionEvent event) {
		ArrayList<Device> devicesToUpdate = new ArrayList<>();
		// Check if all threshold values are valid
		boolean allValuesValid = true;
		for (Device device : tblDevice.getItems()) {
			int thresholdValue = device.getThreshold();
			if (thresholdValue < 0) {
				allValuesValid = false;
				break;
			}
		}

		if (allValuesValid) {
			devicesToUpdate.addAll(tblDevice.getItems());
			ClientUI.chat.accept(new Message(Request.Threshold_Update_Request,devicesToUpdate));

		} else {
			// Show an error message to the user
			lblError.setText("Error: Threshold values must be positive integers.");
			lblError.setVisible(true);
		}
	}

	@SuppressWarnings("static-access")
	@FXML
	void clickBtnRefresh() {
		tblDevice.getItems().clear();
		String area = ChatClient.userController.getUser().getRegion().toString();
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, area));
		
		tblDevice.setItems(ChatClient.deviceController.getAreaDevices());
		
	}

	@FXML
	public void setCulomns() {
		deviceName.setCellValueFactory(new PropertyValueFactory<Device, String>("deviceName"));
		threshold.setCellValueFactory(new PropertyValueFactory<Device, Integer>("threshold"));

	}

	private void setEditableCell(TableColumn<Device, Integer> threshold) {
		threshold.setOnEditCommit(event -> {
			Device device = event.getRowValue();
			device.setThreshold(event.getNewValue());
			
		});
	}

	private void setTextFieldInCell(TableColumn<Device, Integer> threshold) {
		threshold.setCellFactory((param) -> {
			return new TextFieldTableCell<>(new IntegerStringConverter());
		});
	}

}
