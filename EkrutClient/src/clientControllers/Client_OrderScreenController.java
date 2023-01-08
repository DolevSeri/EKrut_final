package clientControllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entities.ProductInDevice;
import entities.Sale;
import enums.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class Client_OrderScreenController {
	FXMLLoader loader = new FXMLLoader();
	SetSceneController newScreen = new SetSceneController();

	@FXML
	private Button btnBack;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnEndOrder;

	@FXML
	private Button btnExit;

	@FXML
	private GridPane gpCart;

	@FXML
	private GridPane gpCatalog;

	@FXML
	private Label lblTotalPrice;

	@FXML
	private ImageView logoImage;
	private int rowInCart = 3, indexForCart = 0;

	public HashMap<ProductInDevice, Integer> selectedProducts = new HashMap<>();
	public static ObservableList<ProductInDevice> products;
	public ObservableList<ProductController> productControllers = FXCollections.observableArrayList();
	public List<ProductInCartController> productInCartControllers = FXCollections.observableArrayList();
	public static double totalPrice = 0;
    public Sale sale;
	/**
	 * setCatalog-a method that will set the catalog for the catalgscreen
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		String msg="";
		selectedProducts = ChatClient.cartController.getCart();
		if (ChatClient.cartController.getCart().size() == 0) {
			ClientUI.chat
					.accept(new Message(Request.Get_Products, ChatClient.costumerController.getCostumer().getDevice()));
			products = ChatClient.productCatalogController.getProductCatalog();
			setCatalog();
			changeEndOrder(true);
			btnCancel.setDisable(true);
			
			for(Sale sale:ChatClient.salesController.getSales()) {
				msg+=sale.getDiscountType()+" ";
			}
			if(ChatClient.costumerController.getOrdersofcostumer().size() == 0) {
				msg+="\n for your first order you get more: 20% discount!!(:";
			}
			
			if(ChatClient.salesController.getSales().size()>0||ChatClient.costumerController.getOrdersofcostumer().size() == 0)
			     newScreen.popUpMessage("The dicounts for this order:"+msg);
		} else {
			products = ChatClient.productCatalogController.getProductCatalog();
			setCatalog();
			System.out.println(selectedProducts);
			for (ProductController p : productControllers) {
				for (ProductInDevice pid : ChatClient.cartController.getCart().keySet()) {
					if (p.getProductInDevice().equals(pid)) {
						int cnt = ChatClient.cartController.getCart().get(pid);
						for (int i = 0; i < cnt; i++)
							p.addToCartEdit();
						break;
					}
				}
			}
		}
		
	}

	private void setCatalog() throws IOException {
		int column = 0;
		int row = 3;
		int i = 0;
		for (ProductInDevice p : products) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/clientGUI/Product.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();
			ProductController productController = fxmlLoader.getController();
			productControllers.add(productController);
			productControllers.get(i++).setData(p, null, this);
			gpCatalog.add(anchorPane, column++, row);// (child column,row)
			if (column == 3) {
				row++;
				column = 0;
				// Set grid width
			}
			GridPane.setMargin(anchorPane, new Insets(3));
			gpCatalog.setMinHeight(Region.USE_COMPUTED_SIZE);
			gpCatalog.setPrefHeight(Region.USE_COMPUTED_SIZE);
			gpCatalog.setMaxHeight(Region.USE_COMPUTED_SIZE);
		}
	}

	public void setCartGrid(ProductInDevice productInDevice, ProductController productController) throws IOException {
		for (ProductInCartController p : productInCartControllers) {
			if (p.getProduct().equals(productInDevice)) {
				p.setData(productInDevice, this);
				setTotalAmount();
				return;
			}
		}
		changeEndOrder(false);
		btnCancel.setDisable(false);
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/clientGUI/ProductInCart.fxml"));
		AnchorPane anchorPane = fxmlLoader.load();

		ProductInCartController productInCartController = fxmlLoader.getController();
		productInCartController.setIndexInCart(indexForCart);
		productInCartController.setData(productInDevice, this);
		productInCartControllers.add(productInCartController);

		gpCart.add(anchorPane, 0, rowInCart++);
		GridPane.setMargin(anchorPane, new Insets(3));
		// Set grid width
		gpCart.setMinHeight(Region.USE_COMPUTED_SIZE);
		gpCart.setPrefHeight(Region.USE_COMPUTED_SIZE);
		gpCart.setMaxHeight(Region.USE_COMPUTED_SIZE);
		setTotalAmount();
	}

	public void changeEndOrder(boolean btn) {
		if (btn)
			btnEndOrder.setDisable(true);
		else
			btnEndOrder.setDisable(false);
	}

	public void setTotalAmount() {
		double totalSum = 0;
		if (selectedProducts.size() > 0) {
			for (ProductInDevice p : selectedProducts.keySet()) {
				// calculate the total price
				totalSum += (p.getPrice() * selectedProducts.get(p));
			}
		}
		totalPrice = totalSum;
		lblTotalPrice.setText(String.format("%.2f",totalPrice)+" ILS");
	}

	@FXML
	void clickOnBack(ActionEvent event) {
		productInCartControllers.clear();
		productControllers.clear();
		selectedProducts.clear();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		if (ChatClient.configuration.equals("EK")) {
			newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
		} else {
			newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
		}
	}

	@FXML
	void clickOnCancel(ActionEvent event) throws IOException {
		gpCart.getChildren().clear();
		gpCatalog.getChildren().clear();
		rowInCart = 3;
		indexForCart = 0;
		ClientUI.chat
				.accept(new Message(Request.Get_Products, ChatClient.costumerController.getCostumer().getDevice()));
		products = ChatClient.productCatalogController.getProductCatalog();
		selectedProducts.clear();
		productControllers.clear();
		productInCartControllers.clear();
		setCatalog();
		setTotalAmount();
	}

	@FXML
	void clickOnExit(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

	@FXML
	void clickOnEndOrder(ActionEvent event) {
		ChatClient.cartController.setCart(selectedProducts);
		ChatClient.productCatalogController.setProductCatalog(products);
		// saving the chosen products for client.

		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		switch (ChatClient.costumerController.getSuplyMethod()) {
		case Standart:
			newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderConfirmation.fxml");
			break;
		case Delivery:
			newScreen.setScreen(new Stage(), "/clientGUI/Client_DeliveryConfirmation.fxml");
			break;
		case PickUp:
			newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderConfirmation.fxml");
			break;
		default:
			break;
		}
	}

}
