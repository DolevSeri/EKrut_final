package clientControllers;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entities.ProductInDevice;
import entities.Sale;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Ron Lahiani , Peleg Oanuno The ProductController class is responsible
 *         for handling the interactions and logic related to the product
 *         catalog screen.
 *
 *         This class sets the data for each product in the catalog, handles
 *         button clicks for adding products to the cart, and updates the prices
 *         of products based on any active sales or subscriber discounts.
 */
public class ProductController {
	private Client_OrderScreenController client_OrderScreenController;

	@FXML
	private Button addToCart;

	@FXML
	private Label lblError;

	@FXML
	private Label lblName;

	@FXML
	private Label lblPrice;

	@FXML
	private ImageView imageSale;

	@FXML
	private ImageView productLogo;

	private ProductInDevice product;
	private int quantityInOrder = 0;
	private ArrayList<Sale> sales = new ArrayList<>();
	// if the order is the first order of the subscriber flag will be true
	boolean flag = false;

	public void initialize() {
		Image image = new Image("/images/sale.png");
		imageSale.setImage(image);
		imageSale.setVisible(false);

	}

	public void resetErrorLabel() {
		lblError.setText(null);
	}

	/**
	 * 
	 * SetData-a method that set the fxml of product in the product catalog screen
	 * 
	 * @param product-contain              the product that we want to set data for
	 *                                     him.
	 * @param client_OrderScreenController - contain the main order screen
	 *                                     controller/
	 */

	public void setData(ProductInDevice product, Client_OrderScreenController client_OrderScreenController) {
		int price = 0;
		this.product = product;
		lblName.setText(this.product.getProductName());
		// if the cart is full thats means that the user press the back button in the
		// confirmation screen so we dont want to do the same discounts twice
		if (ChatClient.cartController.getCart().size() == 0) {
			// if there are any sales
			if (checkSale() == true) {
				imageSale.setVisible(true);
				for (Sale s : sales) {
					// initialize the product price after discount
					product.setPrice(salePrecent(product.getPrice(), s));
					for (ProductInDevice pro : ChatClient.productCatalogController.getProductCatalog()) {
						if (pro.equals(product)) {
							pro.setPrice(product.getPrice());
						}
					}

				}

			}
			// if thats the first order of the subscriber
			if (flag == true) {
				product.setPrice(product.getPrice() - product.getPrice() * 0.2);
				for (ProductInDevice pro : ChatClient.productCatalogController.getProductCatalog()) {
					if (pro.equals(product)) {
						pro.setPrice(product.getPrice());
					}
				}
				imageSale.setVisible(true);
			}
		}

		if (ChatClient.salesForSubscriber.size() != 0 || ChatClient.firstOrderSubscriber == true) {
			Image image = new Image("/images/sale.png");
			imageSale.setImage(image);
			imageSale.setVisible(true);
		}

		lblPrice.setText(String.format("%.2f", this.product.getPrice()));
		Image image = new Image(product.getImagePath());
		productLogo.setImage(image);
		this.client_OrderScreenController = client_OrderScreenController;
	}

	@FXML
	void clickOnAdd(ActionEvent event) throws IOException {
		addToCart();
	}

	public void addToCart() throws IOException {
		if (product.getQuantity() == 0) {
			lblError.setText("Out of\n stock!");
		} else {
			client_OrderScreenController.changeEndOrder(false);
			product.setQuantity(product.getQuantity() - 1); // update the quantity in device.
			client_OrderScreenController.selectedProducts.put(product, ++quantityInOrder);
			client_OrderScreenController.setCartGrid(product, this);
		}
	}

	public void addToCartEdit() throws IOException {
		client_OrderScreenController.changeEndOrder(false);
		quantityInOrder++;
		client_OrderScreenController.selectedProducts.put(product, quantityInOrder);
		client_OrderScreenController.setCartGrid(product, this);
	}

	public int getQuantityInOrder() {
		return quantityInOrder;
	}

	public void setQuantityInOrder(int quantity) {
		this.quantityInOrder = quantity;
	}

	public ProductInDevice getProductInDevice() {
		return this.product;
	}

	public void setProductInDevice(ProductInDevice product) {
		this.product = product;
	}

	public void setProductController(ProductController p, Client_OrderScreenController screen) {
		this.quantityInOrder = p.getQuantityInOrder();
		this.product = getProductInDevice();
		client_OrderScreenController = screen;

	}

	/**
	 * chackSale- a method that check if there are any sales in the order
	 * 
	 * @return true if there are any sales anf fals if there are not
	 */
	public boolean checkSale() {
		// the area of the user that he order from
		String area = ChatClient.userController.getUser().getRegion().toString();
		ClientUI.chat.accept(
				new Message(Request.Import_orderbyname, ChatClient.costumerController.getCostumer().getUsername()));
		// if the user not a subscriber there is not sales
		if (ChatClient.costumerController.getCostumer().getSubscriberID() == -1) {
			return false;
		}
		// if there is the first order of the subscriber there is more 20% discount
		if (ChatClient.costumerController.getOrdersofcostumer().size() == 0) {
			flag = true;
			ChatClient.firstOrderSubscriber = true;
		}

		// ClientUI.chat.accept(new Message(Request.import_Sales, null));
		for (Sale sale : ChatClient.salesController.getSales()) {
			if (sale.getRegion().toString().equals(area)) {
				if (compareTime(sale.getStartHour(), sale.getEndHour())
						&& isCurrentDayInRange(sale.getStartDay(), sale.getEndDay())) {
					// if there are any sale in the area that valid adding to the sale list of this
					// order
					sales.add(sale);
					ChatClient.salesForSubscriber.add(sale);
				}
			}
		}

		if (sales.size() != 0)
			return true;
		return false;
	}

	/**
	 * Calculates the sale price of a product based on the discount type specified
	 * in the Sale object.
	 * 
	 * @param price the original price of the product
	 * @param sale  the Sale object containing the discount type
	 * @return the sale price of the product
	 */
	public double salePrecent(double price, Sale sale) {
		if (sale.getDiscountType().equals("20%")) {
			return price - price * 0.20;
		}
		if (sale.getDiscountType().equals("30%")) {
			return price - price * 0.30;
		}
		if (sale.getDiscountType().equals("50%")) {
			return price - price * 0.50;
		}
		return price;

	}

	/**
	 * Compares the current time to the start and end hours specified in the method
	 * parameters.
	 * 
	 * @param starthour the start hour of the sale
	 * @param endhour   the end hour of the sale
	 * @return true if the current time is between the start and end hours, false
	 *         otherwise
	 */
	public static boolean compareTime(String starthour, String endhour) {
		LocalTime currentTime = LocalTime.now();
		LocalTime start = LocalTime.parse(starthour);
		LocalTime end = LocalTime.parse(endhour);

		if (currentTime.isBefore(start)) {
			return false;
		} else if (currentTime.isAfter(end)) {
			return false;
		}
		return true;

	}

	/**
	 * Compares the current day to the start and end days specified in the method
	 * parameters.
	 * 
	 * @param startDayName the start day of the sale
	 * @param endDayName   the end day of the sale
	 * @return true if the current day is between the start and end days, false
	 *         otherwise
	 */
	public static boolean isCurrentDayInRange(String startDayName, String endDayName) {
		DayOfWeek start = DayOfWeek.valueOf(startDayName.toUpperCase());
		DayOfWeek end = DayOfWeek.valueOf(endDayName.toUpperCase());
		DayOfWeek now = java.time.LocalDate.now().getDayOfWeek();
		if (start.compareTo(end) > 0) {
			return (start.compareTo(now) <= 0 || now.compareTo(end) <= 0);
		} else {
			return (start.compareTo(now) <= 0 && now.compareTo(end) <= 0);
		}

	}

}
