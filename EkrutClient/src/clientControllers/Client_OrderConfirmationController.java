package clientControllers;

import java.io.IOException;
import java.util.List;

import client.ChatClient;
import client.ClientUI;
import entities.Device;
import entities.Message;
import entities.MessageInSystem;
import entities.ProductInDevice;
import enums.Request;
import enums.Role;
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
 * Controller that responsible for Confirmation order.
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
	private List<ProductInDevice> products = ChatClient.productCatalogController.getProductCatalog();
	private double totalPrice = 0;

	public void setTotalPrice() {
		double totalSum = 0;
		for (ProductInDevice p : ChatClient.cartController.getCart().keySet()) {
			// calculate the total price
			totalSum += (p.getPrice() * ChatClient.cartController.getCart().get(p));
		}
		totalPrice = totalSum;
		lblPrice.setText(String.valueOf(totalPrice) + "  ILS");
	}

	public void initialize() throws IOException {
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
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	@FXML
	void clickOnConfirm(ActionEvent event) {
		updateSystem();
		updateProductsInDevice();
	}

	public void updateProductsInDevice() {

	}

	public void updateSystem() {
		int tresholdLevel = 0;
		ClientUI.chat.accept(
				new Message(Request.Get_Devices_By_Area, ChatClient.userController.getUser().getRegion().toString()));
		String deviceName = "";
		for (Device device : ChatClient.deviceController.getAreaDevices()) {
			if (ChatClient.costumerController.getCostumer().getDevice().equals(device.getDeviceName()))
				deviceName = ChatClient.costumerController.getCostumer().getDevice();
			tresholdLevel = device.getThreshold();
		}
		String productsUnderTreshold = "In Device: " + deviceName + " Product is under thershold level: ";
		for (ProductInDevice product : products) {
			if (product.getQuantity() <= tresholdLevel)
				productsUnderTreshold = productsUnderTreshold + "," + String.valueOf(product.getProductCode());
		}
		MessageInSystem msg = new MessageInSystem(1, Role.AreaManager, productsUnderTreshold);
		ClientUI.chat.accept(new Message(Request.Send_msg_to_system, msg));
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

}