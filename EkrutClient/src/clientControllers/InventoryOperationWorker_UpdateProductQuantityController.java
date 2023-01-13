package clientControllers;

import java.util.ArrayList;

import client.ClientUI;
import entities.InventoryCall;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * This script is a controller class for updating a product's quantity and
 * closing a call in the inventory management system. The class is responsible
 * for updating the quantity of a product and closing the call in the system.
 * 
 * @author Inbar Mizrahi
 * @version 1.0
 */
public class InventoryOperationWorker_UpdateProductQuantityController {

	@FXML
	private Button btnexit;

	@FXML
	private Button btnUpdate;

	@FXML
	private Label lblDeviceName;

	@FXML
	private Label lblProductName;

	@FXML
	private TextField txtPrQuantity;
	
	@FXML
	private ImageView logo;

	/**
	 * selectedCall - the selected call from the call list scene - a
	 * SetSceneController object for navigating between scenes
	 */
	private InventoryCall selectedCall;
	private SetSceneController scene = new SetSceneController();

	/**
	 * Initializes the selected call data for display
	 * 
	 * @param call - the selected call from the call list
	 */
	public void initData(InventoryCall call) {
		
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);
		selectedCall = call;
		lblDeviceName.setText(selectedCall.getDeviceName());
		lblProductName.setText(selectedCall.getProductName());
	}

	/**
	 * Handles the update button click event. Validates the input, updates the
	 * product quantity and closes the call.
	 * 
	 * @param event - the button click event
	 */
	@FXML
	void clickBtnUpdate(ActionEvent event) {
		String quantity = txtPrQuantity.getText();
		boolean isNum = isNumeric(quantity);
		if (quantity.equals("")) {
			scene.popUpMessage("ERROR: You must insert value");
			return;
		} else if (!isNum) {
			scene.popUpMessage("ERROR: You must insert a number!!");
			return;
		}
		int val = Integer.valueOf(quantity);
		if (val <= 0) {
			scene.popUpMessage("ERROR: You must insert a number larger than zero!");
			return;
		}

		int callID = selectedCall.getCallID();
		String deviceName = selectedCall.getDeviceName();
		String productName = selectedCall.getProductName();
		ArrayList<String> fields = new ArrayList<>();
		fields.add(0, deviceName);
		fields.add(1, productName);
		fields.add(2, Integer.toString(val));
		fields.add(3, Integer.toString(callID));

		ClientUI.chat.accept(new Message(Request.UpdateProductQuantityAndCloseCall, fields));
		scene.popUpMessage("The product quantity was update succesfully!\nThe call marked as closed. ");
		scene.back(event, "/clientGUI/InventoryOperationWorker_CallsListView.fxml");

	}

	/**
	 * A helper method to check if a given string is a number
	 * 
	 * @param strNum - the string to check
	 * @return true if the string is a number, false otherwise
	 */
	public static boolean isNumeric(String strNum) {
		double d;
		if (strNum == null) {
			return false;
		}
		try {
			d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Handles the exit button click event. Navigates back to the call list scene.
	 * 
	 * @param event - the button click event
	 */
	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/InventoryOperationWorker_CallsListView.fxml");
	}

}
