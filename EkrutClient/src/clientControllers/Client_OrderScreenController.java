package clientControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entities.Product;
import entities.ProductInDevice;
import enums.Request;
import javafx.collections.ObservableList;
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
	public ObservableList<ProductInDevice> products;
	public List<ProductController> productControllers = new ArrayList<>();
	public ArrayList<ProductInCartController> productInCartControllers = new ArrayList<>();
	public static double totalPrice = 0;

	public void initialize() {
		ClientUI.chat
				.accept(new Message(Request.Get_Products, ChatClient.costumerController.getCostumer().getDevice()));
		products = ChatClient.productCatalogController.getProductCatalog();
		try {
			setCatalog();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * setCatalog-a method that will set the catalog for the catalgscreen
	 * 
	 * @throws IOException
	 */

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
			gpCatalog.setMinHeight(Region.USE_COMPUTED_SIZE);
			gpCatalog.setPrefHeight(Region.USE_COMPUTED_SIZE);
			gpCatalog.setMaxHeight(Region.USE_COMPUTED_SIZE);
			GridPane.setMargin(anchorPane, new Insets(3));
		}

	}

	/*
	 * public void setCartGrid() throws IOException { for (ProductInDevice p :
	 * selectedProducts.keySet()) { FXMLLoader fxmlLoader = new FXMLLoader();
	 * fxmlLoader.setLocation(getClass().getResource("/clientGUI/ProductInCart.fxml"
	 * )); AnchorPane anchorPane = fxmlLoader.load();
	 * 
	 * ProductInCartController productInCartController = fxmlLoader.getController();
	 * productInCartControllers.add(productInCartController);
	 * productInCartControllers.get(indexForCart++).setData(p, this);
	 * gpCart.add(anchorPane, 0, rowInCart++); GridPane.setMargin(anchorPane, new
	 * Insets(3)); } // Set grid width
	 * gpCart.setMinHeight(Region.USE_COMPUTED_SIZE);
	 * gpCart.setPrefHeight(Region.USE_COMPUTED_SIZE);
	 * gpCart.setMaxHeight(Region.USE_COMPUTED_SIZE);
	 * 
	 * }
	 */
	public void setCartGrid(ProductInDevice productInDevice, ProductController productController) throws IOException {
		if (selectedProducts.get(productInDevice) > 1) {
			for (ProductInCartController p : productInCartControllers) {
				if (p.getProductController().getProductInDevice().getProductName()
						.equals(productController.getProductInDevice().getProductName())) {
					productInCartControllers.get(p.getIndexInCart()).setData(productInDevice, this);
				}
			}
		} else {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/clientGUI/ProductInCart.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();

			ProductInCartController productInCartController = fxmlLoader.getController();
			productInCartControllers.add(productInCartController);
			productInCartController.setIndexInCart(indexForCart);
			productInCartControllers.get(indexForCart++).setData(productInDevice, this);
			gpCart.add(anchorPane, 0, rowInCart++);
			GridPane.setMargin(anchorPane, new Insets(3));
			// Set grid width
			gpCart.setMinHeight(Region.USE_COMPUTED_SIZE);
			gpCart.setPrefHeight(Region.USE_COMPUTED_SIZE);
			gpCart.setMaxHeight(Region.USE_COMPUTED_SIZE);
		}
		setTotalAmount();
	}

	private void setTotalAmount() {
		double totalSum = 0;
		for (ProductInDevice p : selectedProducts.keySet()) {
			totalSum += (p.getPrice() * selectedProducts.get(p));
		}
		lblTotalPrice.setText(String.valueOf(totalSum));
	}

	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
	}

	@FXML
	void clickOnCancel(ActionEvent event) {

	}

	@FXML
	void clickOnExit(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

	@FXML
	void clickOnEndOrder(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderConfirmation.fxml");
	}

}
