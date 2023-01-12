package clientControllers;

import client.ChatClient;
import entities.ProductInDevice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * ProductInConfirmationController is a class that shows the product information
 * in the order confirmation screen.
 * 
 * @author Peleg
 * 
 * @version
 */
public class ProductInConfirmationController {

	@FXML
	private Label lblName;

	@FXML
	private Label lblPrice;

	@FXML
	private Label lblQuantity;
	@FXML
	private ImageView productLogo;

	private ProductInDevice product;

	/**
	 * 
	 * setData - a method that sets the product information in the order
	 * confirmation screen.
	 * 
	 * @param product - an instance of ProductInDevice that represent the product
	 */
	public void setData(ProductInDevice product) {
		this.product = product;
		lblName.setText(product.getProductName());
		Image image = new Image(product.getImagePath());
		productLogo.setImage(image);
		int amountOfProduct = ChatClient.cartController.getCart().get(product);
		double priceOfAmount = amountOfProduct * product.getPrice();
		lblQuantity.setText(String.valueOf(amountOfProduct));
		lblPrice.setText(String.format("%.2f", priceOfAmount));
	}
}
