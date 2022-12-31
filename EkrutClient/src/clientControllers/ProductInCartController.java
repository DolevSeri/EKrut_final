package clientControllers;

import entities.ProductInDevice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductInCartController {
	private Client_OrderScreenController client_OrderScreenController;
	private ProductInDevice product;
	private ProductController productController;
	private int indexInCart;
	@FXML
	private Button btnMinus;

	@FXML
	private ImageView imgProduct;

	@FXML
	private Label lblName;

	@FXML
	private Label lblPrice;

	@FXML
	private Label lblQuantity;

	public void setData(ProductInDevice product, Client_OrderScreenController client_OrderScreenController) {
		this.product = product;
		lblName.setText(product.getProductName());
		for (ProductController p : client_OrderScreenController.productControllers) {
			if (p.getProductInDevice().getProductName().equals(product.getProductName())) {
				lblPrice.setText(String.valueOf((p.getQuantityInOrder() - 1) * product.getPrice()));
				productController = p;
				break;
			}
		}
		lblQuantity.setText(String.valueOf(client_OrderScreenController.selectedProducts.get(product)));
		Image image = new Image(product.getImagePath());
		imgProduct.setImage(image);
		this.client_OrderScreenController = client_OrderScreenController;
	}

	public ProductController getProductController() {
		return productController;
	}

	public void setProductController(ProductController productController) {
		this.productController = productController;

	}

	public void setIndexInCart(int indexInCart) {
		this.indexInCart = indexInCart;
	}

	public int getIndexInCart() {
		return indexInCart;
	}

	@FXML
	void clickOnMinus(ActionEvent event) {
		
	}

}