package clientControllers;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
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

/**
 * 
 * Client_OrderScreenController is a class that handles the events and updates
 * information for the client's shopping cart screen.
 * 
 * It contains methods for setting the catalog, handling button events, and
 * calculating the total price of the selected products.
 * 
 * @author Ron
 */
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
	public boolean flaghasSale;

	/**
	 * setCatalog-a method that will set the catalog for the catalgscreen
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		logoImage.setImage(image);

		String msg = "";
		selectedProducts = ChatClient.cartController.getCart();
		// if costumer wasn't in the middle of order
		if (ChatClient.cartController.getCart().size() == 0) {
			// get products from db
			ClientUI.chat
					.accept(new Message(Request.Get_Products, ChatClient.costumerController.getCostumer().getDevice()));
			products = ChatClient.productCatalogController.getProductCatalog();
			ClientUI.chat.accept(new Message(Request.import_Sales, null));
			for (Sale sales : ChatClient.salesController.getSales()) {
				saledone(sales.getEndDay(), sales.getEndHour(), sales);
			}
			setCatalog();
			changeEndOrder(true);
			btnCancel.setDisable(true);
			// check sales in this area
			for (Sale sale : ChatClient.salesController.getSales()) {
				if (sale.getRegion().toString().equals(ChatClient.userController.getUser().getRegion().toString())
						&& ProductController.compareTime(sale.getStartHour(), sale.getEndHour())
						&& ProductController.isCurrentDayInRange(sale.getStartDay(), sale.getEndDay())
						&& ChatClient.costumerController.getCostumer().getSubscriberID() != -1) {
					msg += sale.getDiscountType() + " ";
					flaghasSale = true;
				}
			}
			if (ChatClient.costumerController.getOrdersofcostumer().size() == 0
					&& ChatClient.costumerController.getCostumer().getSubscriberID() != -1) {

				msg += "\n for your first order you get more: 20% discount!\n\nNOTICE: *The prices that are shown are after discount*";
				flaghasSale = true;

			}

			if (flaghasSale == true)
				newScreen.popUpMessage("The dicounts for this order:" + msg);
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

	/**
	 * 
	 * This method sets the catalog by iterating through a list of products and
	 * adding them to a grid pane. The FXMLLoader is used to load the Product.fxml
	 * file, which is then added to the grid pane. A ProductController is also
	 * created and added to a list of product controllers. The data for the product
	 * and this class is set for the controller. The grid pane's width is also set
	 * and margins are added to each product in the grid.
	 * 
	 * @throws IOException if there is an error with loading the FXML file
	 */
	private void setCatalog() throws IOException {
		int column = 0;
		int row = 3;
		int i = 0;
		// load all products from products
		for (ProductInDevice p : products) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/clientGUI/Product.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();
			ProductController productController = fxmlLoader.getController();
			productControllers.add(productController);
			productControllers.get(i++).setData(p, this);
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

	/**
	 * 
	 * This method sets the grid for the cart by adding a product to it. If the
	 * product is already in the cart, the data for the product and this class is
	 * updated for the corresponding controller. Otherwise, the FXMLLoader is used
	 * to load the ProductInCart.fxml file, which is then added to the grid pane. A
	 * ProductInCartController is also created and added to a list of product in
	 * cart controllers. The data for the product and this class is set for the
	 * controller. The grid pane's width is also set and margins are added to each
	 * product in the grid. The total amount is also updated.
	 * 
	 * @param productInDevice   the product to be added to the cart
	 * @param productController the controller for the product
	 * @throws IOException if there is an error with loading the FXML file
	 */
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

	/**
	 * 
	 * This method changes the state of the end order button. If the given parameter
	 * is true, the button is set to disabled. Otherwise, the button is set to
	 * enabled.
	 */
	public void changeEndOrder(boolean btn) {
		if (btn)
			btnEndOrder.setDisable(true);
		else
			btnEndOrder.setDisable(false);
	}

	/**
	 * 
	 * This method sets the total amount for the cart by calculating the total price
	 * for all the products in the cart. The total sum is stored in the totalPrice
	 * variable and is displayed on the label lblTotalPrice.
	 */
	public void setTotalAmount() {
		double totalSum = 0;
		if (selectedProducts.size() > 0) {
			for (ProductInDevice p : selectedProducts.keySet()) {
				// calculate the total price
				totalSum += (p.getPrice() * selectedProducts.get(p));
			}
		}
		totalPrice = totalSum;
		lblTotalPrice.setText(String.format("%.2f", totalPrice) + " ILS");
	}

	/**
	 * 
	 * This method handles the event of clicking the back button. It clears all the
	 * lists and maps, hides the current window, and opens the appropriate main view
	 * based on the configuration.
	 */
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

	/**
	 * 
	 * This method handles the event of clicking the cancel button. It clears the
	 * cart grid and catalog grid, clears all the lists and maps, requests the
	 * products from the server again, and sets the catalog and total amount.
	 * 
	 * @param event the action event of clicking the cancel button
	 * @throws IOException if there is an error with loading the FXML file
	 */
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

	/**
	 * 
	 * This method handles the event of clicking the exit button. It calls the
	 * method exitOrLogOut() of newScreen, which closes the current window and exits
	 * the application.
	 * 
	 * @param event the action event of clicking the exit button
	 */
	@FXML
	void clickOnExit(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

	/**
	 * 
	 * This method handles the event of clicking the end order button.
	 * 
	 * It sets the cart and products in the cart and product catalog controllers,
	 * 
	 * saves the chosen products for the client, hides the current window, and opens
	 * the appropriate confirmation screen based on the supply method.
	 * 
	 * @param event the action event of clicking the end order button
	 */
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

	/**
	 * 
	 * The saledone method is used to check if a sale has ended and if so, it
	 * updates the status of the sale in the database.
	 * 
	 * @param endDayName the name of the day the sale ends
	 * @param endHour    the hour the sale ends
	 * @param sale       the sale object
	 */
	public static void saledone(String endDayName, String endHour, Sale sale) {
		LocalTime endtime = LocalTime.parse(endHour);
		DayOfWeek endDay = DayOfWeek.valueOf(endDayName.toUpperCase());
		DayOfWeek now = java.time.LocalDate.now().getDayOfWeek();
		LocalTime currentTime = LocalTime.now();
		if (currentTime.isAfter(endtime) && now.compareTo(endDay) >= 0) {
			ClientUI.chat.accept(new Message(Request.Update_SaleStatusdone, sale));
		}

	}

}
