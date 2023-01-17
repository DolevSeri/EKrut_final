package clientControllers;

import java.io.IOException;

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
 * 
 * @author Ron Lahiani This class is used as the main view controller for the
 *         client side of the application. It handles the interactions with the
 *         FXML file that defines the user interface and the logic behind it.
 */
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

	@FXML
	private Label lblWelcome;

	/**
	 * Initialize the view by setting the logo image.
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		logoImage.setImage(image);
		lblWelcome.setText("Welcome Back " + ChatClient.userController.getUser().getFirstName() + " "
				+ ChatClient.userController.getUser().getLastName() + "!");
	}

	@FXML
	void clickOnBack(ActionEvent event) {

	}

	/**
	 * Handles the event of clicking on the Collect Pick Up button. This method
	 * hides the primary window and opens the Client_PickUpOrder_FromDevice.fxml
	 * window.
	 * 
	 * @param event
	 */
	@FXML
	void clickOnCollectPickUp(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_PickUpOrder_FromDevice.fxml");
	}

	/**
	 * Handles the event of clicking on the Create Order button. This method starts
	 * an InactivityLogoutController thread and changes the SupplyMethod to
	 * Standart. Then it hides the primary window and opens the
	 * Client_OrderScreen.fxml window.
	 * 
	 * @param event
	 */
	@FXML
	void clickOnCreateOrder(ActionEvent event) {
		// get Orders from DB
		ClientUI.chat.accept(new Message(Request.getOrders, null));
		Thread t = new Thread(new InactivityLogoutController());
		ChatClient.checkWindowTimeThread = t;
		t.start();
		ChatClient.costumerController.setSuplyMethod(SupplyMethod.Standart);
		System.out.println("Costumer want to create order");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	/**
	 * Handles the event of clicking on the Log Out button. This method calls
	 * exitOrLogOut method and passing true as parameter
	 * 
	 * @param event
	 */
	@FXML
	void clickOnLogout(ActionEvent event) {
		ChatClient.salesForSubscriber.clear();
		ChatClient.firstOrderSubscriber = false;
		newScreen.exitOrLogOut(event, true);

	}

	/**
	 * Handles the event of clicking on the Exit button. This method hides the
	 * primary window, calls exitOrLogOut method and passing true as parameter, it
	 * also prints "exit ConnectForm
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		ChatClient.salesForSubscriber.clear();
		ChatClient.firstOrderSubscriber = false;
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.exitOrLogOut(event, true);
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

}
