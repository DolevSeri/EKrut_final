package clientControllers;

import entities.InventoryCall;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

	private InventoryCall selectedCall;
	private SetSceneController scene = new SetSceneController();

	public void initData(InventoryCall call) {
		selectedCall = call;
		lblDeviceName.setText(selectedCall.getDeviceName());
		lblProductName.setText(selectedCall.getProductName());
	}

	@FXML
	void clickBtnUpdate(ActionEvent event) {
		String quantity = txtPrQuantity.getText();
		boolean isNum = isNumeric(quantity);
		if (quantity.equals("")) {
			scene.popUpMessage("ERROR: You must insert value");
			return;
		}
		else if (!isNum) {
			scene.popUpMessage("You must insert a number!!");
			return;
		}
		int val = Integer.valueOf(quantity);
		if (val < 0) {
			scene.popUpMessage("ERROR: You must insert a number larger than zero!");
			return;
		}

	}

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

	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/InventoryOperationWorker_CallsListView.fxml");
	}

}
