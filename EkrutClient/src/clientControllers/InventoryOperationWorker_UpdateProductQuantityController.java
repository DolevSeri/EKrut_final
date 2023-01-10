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
private  SetSceneController scene  = new SetSceneController();

	public void initData(InventoryCall call) {
		selectedCall = call;
		lblDeviceName.setText(selectedCall.getDeviceName());
		lblProductName.setText(selectedCall.getProductName());
	}

	  @FXML
	    void clickBtnUpdate(ActionEvent event) {
		  
	    }

	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/InventoryOperationWorker_CallsListView.fxml");
	}

}
