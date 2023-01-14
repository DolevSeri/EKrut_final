package clientControllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import client.ChatClient;
import client.ClientUI;
import entities.Device;
import entities.Message;
import entities.Order;
import entities.ProductInDevice;
import enums.ProductStatus;
import enums.Request;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Controller class that responsible for handling with Confirmation order.
 * 
 * @author ron
 *
 */
public class Client_OrderConfirmationController {
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
	private Label lblPrice;

	@FXML
	private ImageView orderLogo;

	@FXML
	private Button btnSubscriber;

	@FXML
	private Label lableSubscriber;
	// a veriable that will help us to know if the user click on the "Deferred
	// payment" button
	private boolean clickonDeferredPayment = false;

	private int rowInCart = 3;
	private List<ProductInConfirmationController> productInConfirmationControllers = FXCollections
			.observableArrayList();
	private List<ProductInDevice> products = new ArrayList<>();
	private double totalPrice = 0;
	private int tresholdLevel = 0;
	private int cntUnderTreshold = 0;
	private String deviceName;

	/**
	 * 
	 * Sets the total price of the order by iterating over all the items in the cart
	 * and adding their prices together
	 * 
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
	 * 
	 * Initializes the confirmation GUI, by retrieving the devices from the server,
	 * then locating the device that the order is going to be delivered to
	 *
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area,
				ChatClient.costumerController.getCostumer().getRegion().toString()));
		for (Device device : ChatClient.deviceController.getAreaDevices()) {
			if (ChatClient.costumerController.getCostumer().getDevice().equals(device.getDeviceName())) {
				deviceName = device.getDeviceName();
				tresholdLevel = device.getThreshold();
				break;
			}
		}
		for (ProductInDevice p : ChatClient.cartController.getCart().keySet()) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/clientGUI/ProductInConfirmation.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();

			ProductInConfirmationController productInConfirmationController = fxmlLoader.getController();
			productInConfirmationController.setData(p);
			productInConfirmationControllers.add(productInConfirmationController);

			gpRecipte.add(anchorPane, 0, rowInCart++);
			GridPane.setMargin(anchorPane, new Insets(3));
			// Set grid width
			gpRecipte.setMinHeight(Region.USE_COMPUTED_SIZE);
			gpRecipte.setPrefHeight(Region.USE_COMPUTED_SIZE);
			gpRecipte.setMaxHeight(Region.USE_COMPUTED_SIZE);
		}
		setTotalPrice();
		Image image = new Image("/images/Confirmation.jpeg");
		orderLogo.setImage(image);
		if (ChatClient.costumerController.getCostumer().getSubscriberID() == -1) {
			btnSubscriber.setVisible(false);
			lableSubscriber.setVisible(false);
		}
	}

	/**
	 * 
	 * Handles the back button press event, it closes the confirmation window and
	 * opens the order screen
	 * 
	 * @param event - The event that fired the function
	 */
	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	/**
	 * 
	 * Handles the cancel button press event, it removes all the items from the
	 * cart, closes the confirmation window and opens the order screen
	 * 
	 * @param event - The event that fired the function
	 */
	@FXML
	void clickOnCancelOrder(ActionEvent event) {
		// remove all products from cart
		ChatClient.cartController.clearCart();
		newScreen.popUpMessage("Order Cancelled!");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	/**
	 * 
	 * Handles the confirm button press event, it stop the thread that counts the 15
	 * minutes for an order, updates the products and order in the database, display
	 * a message to the user according to the payment method, and finally, it closes
	 * the confirmation window and opens the main view screen
	 * 
	 * @param event - The event that fired the function
	 */
	@FXML
	void clickOnConfirm(ActionEvent event) {
		// stop counting 15 minutes for order
		ChatClient.checkWindowTimeThread.interrupt();

		updateProductsInDevice();
		updateOrderInDB();
		// if there are any products under treshold
		if (cntUnderTreshold > 0)
			updateSystemProductsUnderThreshold(tresholdLevel, deviceName);
		ChatClient.cartController.clearCart();
		if (clickonDeferredPayment == true) {
			newScreen.popUpMessage("The order has been placed!\n The payment will decrease next month\n"
					+ "\n Order details will send to you via Email ans SMS!");
		} else {
			newScreen.popUpMessage("Payment confirmed!\n Order details will send to you via Email ans SMS!");
		}

		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		if (ChatClient.configuration.equals("EK")) {
			newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
		} else {
			newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
		}
		ChatClient.salesForSubscriber.clear();
		ChatClient.firstOrderSubscriber = false;
	}

	/**
	 * 
	 * updateOrderInDB is a method that creates and saves an order.
	 * 
	 * When the customer confirms an order, the method is called.
	 * 
	 * The order is saved in the database through a message that is sent to the
	 * server.
	 * 
	 * The order contains information about the device, the total price, username,
	 * time, and products in the order.
	 * 
	 * An order can be either Delivery or PickUp and accordingly it is saved in the
	 * correct table in the database.
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
		ClientUI.chat.accept(new Message(Request.SaveOrder, order));

		if (ChatClient.costumerController.getSuplyMethod().toString().equals("PickUp")) {
			ClientUI.chat.accept(new Message(Request.Save_TakeAway, order));
		}
	}

	/**
	 * 
	 * updateProductsInDevice is a method that updates the products in a device.
	 * When the customer confirms an order, the method is called. The products'
	 * availability status is updated through a message that is sent to the server.
	 * If the quantity of the product is 0, the availability status is set to
	 * NOTAVAILABLE, otherwise, the availability status is set to AVAILABLE.
	 */
	public void updateProductsInDevice() {
		for (ProductInDevice p : ChatClient.productCatalogController.getProductCatalog()) {
			if (p.getQuantity() <= tresholdLevel)
				cntUnderTreshold++;
			products.add(p);
		}
		for (ProductInDevice p : products) {
			if (p.getQuantity() == 0) {
				p.setStatus(ProductStatus.NOTAVAILABLE);
			} else {
				p.setStatus(ProductStatus.AVAILABLE);
			}
		}
		ClientUI.chat.accept(new Message(Request.Update_Products_In_Device, products));
	}

	/**
	 * updateSystem - method that update in DB if there are products less then
	 * threshold level in device.
	 * 
	 * @param tresholdLevel- the tresholdlevel of the device
	 * @param deviceName-    the name of the device
	 */
	public void updateSystemProductsUnderThreshold(int tresholdLevel, String deviceName) {
		// SystemMessage msg;
		for (ProductInDevice product : products) {
			if (product.getQuantity() <= tresholdLevel) {
				ClientUI.chat.accept(new Message(Request.Get_Area_manager_UserName,
						ChatClient.costumerController.getCostumer().getRegion().toString()));

				ArrayList<String> nameAndMessage = new ArrayList<>();
				nameAndMessage.addAll(Arrays.asList(ChatClient.userController.getAreaManagerUserNAme(), "In "
						+ deviceName + "'s device, product: " + product.getProductName() + " is under threshold!"));
				ClientUI.chat.accept(new Message(Request.Send_Notification, nameAndMessage));

			}

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