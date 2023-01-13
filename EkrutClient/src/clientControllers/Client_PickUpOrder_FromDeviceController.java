package clientControllers;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * 
 * The Client_PickUpOrder_FromDeviceController class is a controller class for
 * the "Client_PickUpOrder_FromDevice.fxml" GUI.
 * 
 * The class handles all the logic and events that occur in the GUI, such as
 * button clicks and displaying the orders that can be picked up.
 * 
 * @author Ron Lahiani
 * 
 * @author Peleg Oanuno
 */
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
	private ImageView logo;

	@FXML
	private Label lblError;

	/**
	 * 
	 * The initialize method is called when the scene is loaded. It initializes the
	 * error label to be hidden, gets the pick-up orders from the server and adds
	 * them to the combo box, and sets the logo image.
	 */
	@FXML
	public void initialize() {
		lblError.setVisible(false);
		ClientUI.chat.accept(new Message(Request.Get_PickUp_Orders, ChatClient.costumerController.getCostumer()));
		for (Integer orderID : ChatClient.costumerController.getPickUpOrders()) {
			cmbOrderNum.getItems().add(String.valueOf(orderID));
		}
		Image image = new Image("/images/PickUpImage.jpeg");
		imageLogo.setImage(image);
		Image imagelogo = new Image("//images/IconOnly_Transparent_NoBuffer.png");
		imageLogo.setImage(imagelogo);
	}

	/**
	 * 
	 * The clickOnBack method is an event handler for the "Back" button. When the
	 * button is clicked, it hides the current window and opens the client's main
	 * view.
	 * 
	 * @param event the event that triggers the method, in this case the click on
	 *              the "Back" button.
	 */
	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
	}

	@FXML
	void clickOnChooseOrder(ActionEvent event) {

	}

	/**
	 * 
	 * The clickOnConfirm method is an event handler for the "Confirm" button. When
	 * the button is clicked, it checks if an order number has been selected in the
	 * combo box. If an order number has been selected, it sends a request to the
	 * server to update the pick-up status of the order. It then displays a message
	 * to the user to wait for the order to come out. Finally, it hides the current
	 * window and opens the client's main view.
	 * 
	 * @param event the event that triggers the method, in this case the click on
	 *              the "Confirm" button.
	 */
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

	/**
	 * 
	 * The getExitBtn method is an event handler for the "Exit" button. When the
	 * button is clicked, it closes the application.
	 * 
	 * @param event the event that triggers the method, in this case the click on
	 *              the "Exit" button.
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

}
