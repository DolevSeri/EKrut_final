package clientControllers;

import client.ChatClient;
import entities.ProductInDevice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

/**
 * Controller for chosen product in order to the final confirmation screen
 * 
 * @author user
 *
 */
public class ProductInConfirmationController {

	@FXML
	private Label lblName;

	@FXML
	private Label lblPrice;

	@FXML
	private Label lblQuantity;
	private ProductInDevice product;

	public void setData(ProductInDevice product) {
		this.product = product;
		lblName.setText(product.getProductName());

		int amountOfProduct = ChatClient.cartController.getCart().get(product);
		double priceOfAmount = amountOfProduct * product.getPrice();
		lblQuantity.setText(String.valueOf(amountOfProduct));
		lblPrice.setText(String.valueOf(priceOfAmount));
	}
}
