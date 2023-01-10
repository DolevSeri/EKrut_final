package clientControllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import client.ChatClient;
import client.ClientUI;
import entities.Device;
import entities.Message;
import entities.Order;
import entities.ProductInDevice;
import entities.SystemMessage;
import enums.MessageStatus;
import enums.ProductStatus;
import enums.Request;
import enums.SupplyMethod;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	private int rowInCart = 3;
	private List<ProductInConfirmationController> productInConfirmationControllers = FXCollections
			.observableArrayList();
	private List<ProductInDevice> products = new ArrayList<>();
	private double totalPrice = 0;
	private int tresholdLevel = 0;
	private int cntUnderTreshold = 0;
	private String deviceName;

	public void setTotalPrice() {
		double totalSum = 0;
		for (ProductInDevice p : ChatClient.cartController.getCart().keySet()) {
			// calculate the total price
			totalSum += (p.getPrice() * ChatClient.cartController.getCart().get(p));
		}
		totalPrice = totalSum;
		lblPrice.setText(String.format("%.2f", totalPrice) + "  ILS");
	}

	public void initialize() throws IOException {
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area,
				ChatClient.costumerController.getCostumer().getRegion().toString()));
		for (Device device : ChatClient.deviceController.getAreaDevices()) {
			System.out.println(device.getDeviceName());
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
	}

	@FXML
	private ImageView orderLogo;

	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	@FXML
	void clickOnCancelOrder(ActionEvent event) {
		// remove all products from cart
		ChatClient.cartController.clearCart();
		newScreen.popUpMessage("Order Cancelled!");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

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
		newScreen.popUpMessage("Payment confirmed!\n Order details will send to you via Email ans SMS!");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		if (ChatClient.configuration.equals("EK")) {
			newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
		} else {
			newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
		}
	}

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
		SystemMessage msg;
		for (ProductInDevice product : products) {
			if (product.getQuantity() <= tresholdLevel) {
				msg = new SystemMessage(0, "am" + ChatClient.costumerController.getCostumer().getRegion().toString(),
						"In " + deviceName + "'s device, product: " + product.getProductName() + " is under threshold!",
						MessageStatus.UnRead);
				ClientUI.chat.accept(new Message(Request.Send_msg_to_system, msg));
			}

		}
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

}