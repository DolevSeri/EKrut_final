package clientControllers;

//import javafx.scene.layout.Region;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import enums.Region;
import client.ChatClient;
import client.ClientUI;
import entities.Delivery;

import entities.Message;
import entities.Order;
import entities.ProductInDevice;
import enums.DeliveryStatus;
import enums.Request;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author Peleg This class represents the controller for handling the
 *         confirmation of an delivery order in the client application. It
 *         updates the order in the database, updates the products in the device
 *         and handles the logic for canceling the order, going back to the
 *         previous screen, and confirming the order.
 */
public class Client_DeliveryConfirmationController {
	FXMLLoader loader = new FXMLLoader();
	SetSceneController newScreen = new SetSceneController();
	@FXML
	private Button btnBack;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnExit;

	@FXML
	private GridPane gpRecipte;

	@FXML
	private Label lblError;
	@FXML
	private ImageView imageDelivery;

	@FXML
	private ComboBox<String> cmbArea;
	@FXML
	private Label lblAddressConfirmation;

	@FXML
	private Label lblPrice;

	@FXML
	private TextField txtAddress;

	@FXML
	private Button btnSubscriber;

	@FXML
	private Label lableSubscriber;
	// a veriable that will help us to know if the user click on the "Deferred
	// payment" button
	private boolean clickonDeferredPayment = false;

	private List<ProductInConfirmationController> productInConfirmationControllers = FXCollections
			.observableArrayList();
	private List<ProductInDevice> products = new ArrayList<>();
	private Delivery delivery;
	private String areaName;
	private double totalPrice = 0;
	private int rowInCart = 3;

	public void initialize() throws IOException {
		lblError.setVisible(false);
		delivery = new Delivery(null, DeliveryStatus.NOTAPPROVED, 0, null);

		ArrayList<String> areas = new ArrayList<String>();
		areas.addAll(Arrays.asList("SOUTH", "NORTH", "UAE"));
		cmbArea.getItems().addAll(areas);
		for (ProductInDevice p : ChatClient.cartController.getCart().keySet()) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/clientGUI/ProductInConfirmation.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();

			ProductInConfirmationController productInConfirmationController = fxmlLoader.getController();
			productInConfirmationController.setData(p);
			productInConfirmationControllers.add(productInConfirmationController);

			gpRecipte.add(anchorPane, 0, rowInCart++);
			GridPane.setMargin(anchorPane, new Insets(3));
		}
		setTotalPrice();
		Image image = new Image("/images/DeliveryConfirmation.png");
		imageDelivery.setImage(image);
		if (ChatClient.costumerController.getCostumer().getSubscriberID() == -1) {
			btnSubscriber.setVisible(false);
			lableSubscriber.setVisible(false);
		}
	}

	/**
	 * This method sets the total price of the order by calculating the price of all
	 * products in the cart.
	 */
	public void setTotalPrice() {
		double totalSum = 0;
		for (ProductInDevice p : ChatClient.cartController.getCart().keySet()) {
			// calculate the total price
			totalSum += (p.getPrice() * ChatClient.cartController.getCart().get(p));
		}
		totalPrice = totalSum;
		lblPrice.setText(String.format("%.2f", totalPrice) + "  ILS");
	}

	/**
	 * This method handles the click event on the "Back" button. It hides the
	 * current window and opens the "Client_OrderScreen.fxml" screen.
	 *
	 * @param event - the event that triggered this method.
	 */
	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	/**
	 * This method handles the click event on the "Cancel" button. It removes all
	 * products from the cart, displays a message to the user and goes back to the
	 * "Client_OrderScreen.fxml" screen.
	 *
	 * @param event - the event that triggered this method.
	 */
	@FXML
	void clickOnCancel(ActionEvent event) {
		// remove all products from cart
		ChatClient.cartController.clearCart();
		newScreen.popUpMessage("Order Cancelled!");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	/**
	 * 
	 * The clickOnChooseArea method is called when a user selects an area from the
	 * combo box and sets the selected area to the delivery object's region.
	 * 
	 * @param event The ActionEvent that occurs when the area is selected from the
	 *              combo box
	 */

	@FXML
	void clickOnChooseArea(ActionEvent event) {
		areaName = cmbArea.getValue();
		delivery.setRegion(Region.valueOf(areaName));
	}

	/**
	 * 
	 * The clickOnConfirm method is called when the user confirms their order. It
	 * checks if the user has selected an area and provided an address, if not, an
	 * error message is displayed. If the user has provided the required
	 * information, the order is updated in the database, a delivery object is
	 * created and saved to the database, and a message is displayed to the user
	 * with their order details.
	 */
	@FXML
	void clickOnConfirm(ActionEvent event) {
		if (cmbArea.getValue() == null || txtAddress.getText().isEmpty()) {
			lblError.setVisible(true);
		} else {
			ChatClient.checkWindowTimeThread.interrupt();
			delivery.setCostumerAdress(txtAddress.getText());
			updateOrderInDB();
			// save delivery in DB
			ClientUI.chat.accept(new Message(Request.Save_New_Delivery, delivery));
			ChatClient.cartController.clearCart();
			if (clickonDeferredPayment == true) {
				newScreen.popUpMessage("The order has been placed!\n The payment will decrease next month (:\n"
						+ "\n Order details will send to you via Email ans SMS!\nAnd your order code is: "
						+ delivery.getOrderID());
			} else {
				newScreen.popUpMessage(
						"The Delivery's Payment confirmed!\n Order details will send to you via Email ans SMS!\nAnd your order code is: "
								+ delivery.getOrderID());
			}

			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
		}
		ChatClient.salesForSubscriber.clear();
		ChatClient.firstOrderSubscriber=false;
	}

	/*
	 * This method updates the order information in the database. It first gets the
	 * current date and formats it. Then it retrieves all the products in the cart
	 * and formats them in a specific way. It then creates a new order with all the
	 * information retrieved from the above steps and sends it to the server to be
	 * saved. If the supply method is set to pick-up, it also sends a save take-away
	 * message to the server.
	 */
	public void updateOrderInDB() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = sdf.format(date);
		String[] time = strDate.split("-");

		StringBuilder productsInOrder = new StringBuilder();
		Map<ProductInDevice, Integer> selectedProducts = ChatClient.cartController.getCart();
		for (Map.Entry<ProductInDevice, Integer> entery : selectedProducts.entrySet()) {
			productsInOrder.append(entery.getKey().getProductName() + ",");
			productsInOrder.append(entery.getValue() + ",");
		}
		productsInOrder.deleteCharAt(productsInOrder.lastIndexOf(","));
		Order order = new Order(ChatClient.costumerController.getCostumer().getDevice(),
				ChatClient.orderController.getOrdersList().size() + 1, (float) totalPrice,
				ChatClient.userController.getUser().getUsername(), time[0], time[1], time[2],
				ChatClient.costumerController.getSuplyMethod(), productsInOrder.toString());
		delivery.setOrderID(order.getOrderID());
		ClientUI.chat.accept(new Message(Request.SaveOrder, order));

		if (ChatClient.costumerController.getSuplyMethod().toString().equals("PickUp")) {
			ClientUI.chat.accept(new Message(Request.Save_TakeAway, order));
		}
	}

	@FXML

	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

	/**
	 * clickOnDeferredPayment- will do the action aftar a subscriber will press the
	 * button of "Deferred payment"
	 * 
	 * @param event-click on button "Deferred payment"
	 */
	@FXML
	void clickOnDeferredPayment(ActionEvent event) {
		clickonDeferredPayment = true;
		clickOnConfirm(event);
	}

}
