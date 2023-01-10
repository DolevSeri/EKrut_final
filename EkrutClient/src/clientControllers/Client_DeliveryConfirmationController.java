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
import entities.Device;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
	}

	public void setTotalPrice() {
		double totalSum = 0;
		for (ProductInDevice p : ChatClient.cartController.getCart().keySet()) {
			// calculate the total price
			totalSum += (p.getPrice() * ChatClient.cartController.getCart().get(p));
		}
		totalPrice = totalSum;
		lblPrice.setText(String.format("%.2f",totalPrice) + "  ILS");
	}

	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	@FXML
	void clickOnCancel(ActionEvent event) {
		// remove all products from cart
		ChatClient.cartController.clearCart();
		newScreen.popUpMessage("Order Cancelled!");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	@FXML
	void clickOnChooseArea(ActionEvent event) {
		areaName = cmbArea.getValue();
		delivery.setRegion(Region.valueOf(areaName));
	}

	@FXML
	void clickOnConfirm(ActionEvent event) {
		if (cmbArea.getValue() == null || txtAddress.getText().isEmpty()) {
			lblError.setVisible(true);
		} else {
			delivery.setCostumerAdress(txtAddress.getText());
			updateOrderInDB();
			// save delivery in DB
			ClientUI.chat.accept(new Message(Request.Save_New_Delivery, delivery));
			ChatClient.cartController.clearCart();

			newScreen.popUpMessage(
					"The Delivery's Payment confirmed!\n Order details will send to you via Email ans SMS!\nAnd your order code is: "
							+ delivery.getOrderID());
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
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

}
