package clientControllers;

import java.io.IOException;

import client.ChatClient;
import entityControllers.InactivityLogoutController;
import enums.SupplyMethod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Client_EK_MainViewController {
	FXMLLoader loader = new FXMLLoader();
	SetSceneController newScreen = new SetSceneController();
	@FXML
	private Button btnCollect;

	@FXML
	private Button btnCreateOrder;

	@FXML
	private Button btnLogOut;

	@FXML
	private Button btnExit;

	@FXML
	private ImageView logoImage;
	public void initialize() throws IOException{
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		logoImage.setImage(image);
	}

	@FXML
	void clickOnBack(ActionEvent event) {

	}

	@FXML
	void clickOnCollectPickUp(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_PickUpOrder_FromDevice.fxml");
	}

	@FXML
	void clickOnCreateOrder(ActionEvent event) {
		Thread t = new Thread(new InactivityLogoutController());
		ChatClient.checkWindowTimeThread = t;
		t.start();
		ChatClient.costumerController.setSuplyMethod(SupplyMethod.Standart);
		System.out.println("Costumer want to create order");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	@FXML
	void clickOnLogout(ActionEvent event) {
		newScreen.exitOrLogOut(event, true);
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.exitOrLogOut(event, true);
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

}
