package clientControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductController {

	@FXML
	private Button addToCart;

	@FXML
	private Label lblName;

	@FXML
	private Label lblPrice;

	@FXML
	private Label lblSale;

	@FXML
	private ImageView productLogo;

	public void setData(String name, double price, String sale, String imagePath) {
		lblName.setText(name);
		lblPrice.setText(String.valueOf(price));
		lblSale.setText(sale);
		Image image = new Image(imagePath);
		productLogo.setImage(image);
	}
}
