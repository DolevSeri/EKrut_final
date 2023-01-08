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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class Client_PickUpOrder_FromDeviceController {
	SetSceneController newScreen = new SetSceneController();

	@FXML
	private Button btnBack1;

	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnExit;

	@FXML
	private ComboBox<String> cmbOrderNum;

	@FXML
	private ImageView imageLogo;

	@FXML
	private Label lblError;

	@FXML
	public void initialize() {
		lblError.setVisible(false);
		ClientUI.chat.accept(new Message(Request.Get_PickUp_Orders, ChatClient.costumerController.getCostumer()));
		for (Integer orderID : ChatClient.costumerController.getPickUpOrders()) {
			cmbOrderNum.getItems().add(String.valueOf(orderID));
		}
	}

	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
	}

	@FXML
	void clickOnChooseOrder(ActionEvent event) {

	}

	@FXML
	void clickOnConfirm(ActionEvent event) {
		if (cmbOrderNum.getValue() == null) {
			lblError.setVisible(true);
		} else {
			ClientUI.chat.accept(new Message(Request.Update_PickUp_Status, Integer.valueOf(cmbOrderNum.getValue())));
			newScreen.popUpMessage("Please wait for the order to comeout :)");
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
		}
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

}
