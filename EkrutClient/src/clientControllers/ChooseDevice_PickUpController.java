package clientControllers;

import client.ChatClient;
import client.ClientUI;
import entities.Device;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * 
 * The ChooseDevice_PickUpController class is responsible for handling the
 * pick-up device selection screen for the client.
 * 
 * It allows the client to choose a device from a list of devices in their area
 * for pick-up.
 * 
 * @author peleg
 */
public class ChooseDevice_PickUpController {
	SetSceneController newScreen = new SetSceneController();
	@FXML
	private Button btnBack;

	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnexit1;

	@FXML
	private ComboBox<String> cmbDevice;
	@FXML
	private Label lblError;

	/**
	 * 
	 * The initialize method is called when the scene is loaded. It sets the error
	 * label to be invisible and populates the device combo box with the devices in
	 * the client's area.
	 */
	@FXML
	public void initialize() {
		lblError.setVisible(false);
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area,
				ChatClient.costumerController.getCostumer().getRegion().toString()));
		for (Device device : ChatClient.deviceController.getAreaDevices()) {
			cmbDevice.getItems().add(device.getDeviceName());
		}
	}

	/**
	 * 
	 * The clickOnConfirm method is called when the "Confirm" button is clicked. It
	 * sets the selected device as the customer's device and opens the
	 * 
	 * 
	 */
	@FXML
	void clickOnConfirm(ActionEvent event) {
		if (cmbDevice.getValue() == null) {
			lblError.setVisible(true);
		} else {
			ChatClient.costumerController.getCostumer().setDevice(cmbDevice.getValue());
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
		}
	}

	/**
	 * Handles the event of clicking on the Exit button. This method hides the
	 * primary window, calls exitOrLogOut method and passing true as parameter, it
	 * also prints "exit ConnectForm
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

	/**
	 * This method handles the click event on the "Back" button. It hides the
	 * current window and opens the "Client_OL_MainView.fxml"" screen.
	 *
	 * @param event - the event that triggered this method.
	 */
	@FXML
	void clickOnBack(ActionEvent event) {
		// stop counting 15 minutes for order
		ChatClient.checkWindowTimeThread.interrupt();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
	}

}
