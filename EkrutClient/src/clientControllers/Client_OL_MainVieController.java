package clientControllers;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entityControllers.InactivityLogoutController;
import enums.Request;
import enums.SupplyMethod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * 
 * @author ron
 * 
 *         The class Client_OL_MainViewController is the controller class for
 *         the main view of the online ordering system.
 * 
 *         It contains the logic for handling the user's interactions with the
 *         buttons and elements on the main view,
 * 
 *         such as creating a delivery or pickup order, confirming a delivery,
 *         logging out, and exiting the system.
 */
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
	@FXML
	private ImageView imageLogo;

	/**
	 * Initializes the main view by setting the logo image
	 */
	public void initialize() {
		ClientUI.chat.accept(new Message(Request.Get_Costumer, ChatClient.userController.getUser().getId()));
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		imageLogo.setImage(image);
	}
	/**

	Handles the user clicking on the delivery confirmation button
	@param event the ActionEvent that triggered the method call
	*/
	@FXML
	void clickOnDeliveryConfirmation(ActionEvent event) {
		newScreen.setScreen(new Stage(), "/clientGUI/Client_ApproveGettingDelivery.fxml");
	}
	/**

	Handles the user clicking on the delivery order button
	@param event the ActionEvent that triggered the method call
	*/

	@FXML
	void clickOnDeliveryOrder(ActionEvent event) {
		Thread t = new Thread(new InactivityLogoutController());
		ChatClient.checkWindowTimeThread = t;
		t.start();
		ChatClient.costumerController.setSuplyMethod(SupplyMethod.Delivery);
		ChatClient.costumerController.getCostumer().setDevice("Delivery");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}
	/**

	Handles the user clicking on the pickup order button
	@param event the ActionEvent that triggered the method call
	*/
	@FXML
	void clickOnPickUpOrder(ActionEvent event) {
		Thread t = new Thread(new InactivityLogoutController());
		ChatClient.checkWindowTimeThread = t;
		t.start();
		ChatClient.costumerController.setSuplyMethod(SupplyMethod.PickUp);
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/ChooseDevice_PickUp.fxml");
	}
/**
 * exit the client
 * @param event
 * @throws Exception
 */
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		newScreen.exitOrLogOut(event, false);
	}
/**
 * Handle click on logout , client logout and move to login screen
 * @param event
 */
	@FXML
	void clickOnLogout(ActionEvent event) {
		newScreen.exitOrLogOut(event, true);
	}

}
