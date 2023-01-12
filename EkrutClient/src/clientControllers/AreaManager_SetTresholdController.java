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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

/**
 * The AreaManager_SetTresholdController class is the controller for the Area Manager Set Treshold 
 * view. It handles the actions of the buttons and displays the appropriate views based on the button 
 * clicked. It also allows the user to edit the threshold values for the devices in their area and 
 * saves the updated values to the server.
 *
 * @author Dolev Seri

 */
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
	private Button btnHelp;

	@FXML
	private Button btnexit;
	@FXML
	private Label lblError;
	private TableViewController myTable = new TableViewController();
	private String area = ChatClient.userController.getUser().getRegion().toString();

	/**
	 * The initialize method is called by JavaFX when the view is being set up. It sets the text of the
	 * region label to the user's region, sets up the table columns to be editable, and sets the items
	 * in the table to the list of devices in the user's region.
	 */
	@FXML
	public void initialize() {
		tblDevice.setEditable(true);
		lblRegion.setText(area);
		setColumns();
		setTableItems();
	}
	
	/**
	 * Changes the scene to the previous view when the back button is clicked.
	 * 
	 * @param event the action event of the button being clicked
	 */
	@FXML
	void clickBackBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/AreaManager_InventoryManagementForm.fxml");

	}
	
	/**
	 * Exits or logs out of the application when the exit button is clicked.
	 * 
	 * @param event the action event of the button being clicked
	 */
	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);

	}

	/**
	 * Saves the updated threshold values to the server when the save button is clicked.
	 * 
	 * @param event the action event of the button being clicked
	 */
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
			setTableItems();
			scene.popUpMessage("Threshold set succesfully! ");


		} else {
			scene.popUpMessage("Error: Threshold values must be positive integers");
		}
	}
	
	/**
	 * Displays a message with instructions on how to update the threshold values when the help button 
	 * is clicked.
	 *
	 */
	@FXML
	void clickBtnHelp() {
		scene.popUpMessage("How to update threshold:\n1. Double click the number you want to update\n2. Change the number to the new thresholds\n3. Press ENTER\n4. Click save ");
		
	}
	
	/**
	 * Sets up the columns in the table view to be editable and sets the cell factory for the threshold 
	 * column to a text field table cell with an integer string converter.
	 *
	 */
	@FXML
	private void setColumns() {
		myTable.setColumn(deviceName, "deviceName");
		myTable.setColumn(threshold, "threshold");
		threshold.setCellFactory((cell) -> {
			return new TextFieldTableCell<>(new IntegerStringConverter());});
		myTable.setCellEditable(threshold);
	}
	
	/**
	 * Sets the items in the table view to the list of devices in the user's region.
	 *
	 */
	private void setTableItems() {
		tblDevice.getItems().clear();
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, area));
		tblDevice.setItems(ChatClient.deviceController.getAreaDevices());
	}
}
