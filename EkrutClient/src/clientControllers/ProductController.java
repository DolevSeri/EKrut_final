package clientControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import entities.ProductInDevice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

	/**
	 * SetData-a method that set the fxml of product in the product catalog screen
	 * 
	 * @param name-will                    contain the name of the products
	 * @param price-will                   contain the price of thr products
	 * @param sale-will                    contain the sale that the region have
	 * @param imagePath-                   will contain the path of the product
	 *                                     image
	 * @param client_OrderScreenController - contain the main order screen
	 *                                     controller/
	 */
	public void initialize() {
		imageSale.setVisible(false);
	}

	public void resetErrorLabel() {
		lblError.setText(null);
	}

	public void setData(ProductInDevice product, String sale,
			Client_OrderScreenController client_OrderScreenController) {
		this.product = product;
		lblName.setText(this.product.getProductName());
		lblPrice.setText(String.valueOf(this.product.getPrice()));
		// lblSale.setText(sale);
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
			product.setQuantity(product.getQuantity() - 1); // update the quantity in device.
			client_OrderScreenController.selectedProducts.put(product, ++quantityInOrder);
			client_OrderScreenController.setCartGrid(product, this);
		}
	}

	public void addToCartEdit() throws IOException {

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
}
