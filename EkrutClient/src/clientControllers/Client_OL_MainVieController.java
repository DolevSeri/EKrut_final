package clientControllers;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import enums.Request;
import enums.SupplyMethod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Client_OL_MainVieController {
	FXMLLoader loader = new FXMLLoader();
	SetSceneController newScreen = new SetSceneController();

	@FXML
	Label lblWelcome = null;

	@FXML
	Button btnCreatePickUp;

	@FXML
	Button btnCreateDelivery;

	@FXML
	Button btnConfirmDelivery;

	@FXML
	Button btnLogOut;

	@FXML
	Button btnExit;

	public void initialize() {
		ClientUI.chat.accept(new Message(Request.Get_Costumer, ChatClient.userController.getUser().getId()));
	}

	@FXML
	void clickOnDeliveryConfirmation(ActionEvent event) {

	}

	@FXML
	void clickOnDeliveryOrder(ActionEvent event) {
		ChatClient.costumerController.setSuplyMethod(SupplyMethod.Delivery);
		ChatClient.costumerController.getCostumer().setDevice("Delivery");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	@FXML
	void clickOnPickUpOrder(ActionEvent event) {
		ChatClient.costumerController.setSuplyMethod(SupplyMethod.PickUp);
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/ChooseDevice_PickUp.fxml");
	}

	@FXML
	private ImageView imageLogo;

	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		newScreen.exitOrLogOut(event, false);
	}

	@FXML
	void clickOnLogout(ActionEvent event) {
		newScreen.exitOrLogOut(event, true);
	}

}
