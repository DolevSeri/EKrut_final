package clientControllers;

import client.ChatClient;
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
		if (client_OrderScreenController.selectedProducts.get(product) == 1) {
			for (ProductController p : client_OrderScreenController.productControllers) {
				if (p.getProductInDevice().getProductCode() == product.getProductCode()) {
					this.productController = p;
					break;
				}
			}
		}
		int amountOfProduct = client_OrderScreenController.selectedProducts.get(product);
		double priceOfAmount = amountOfProduct * product.getPrice();
		lblQuantity.setText(String.valueOf(amountOfProduct));
		lblPrice.setText(String.format("%.2f",priceOfAmount));
		Image image = new Image(product.getImagePath());
		imgProduct.setImage(image);
		this.client_OrderScreenController = client_OrderScreenController;
	}

	public ProductController getProductController() {
		return this.productController;
	}

	public void setIndexInCart(int indexInCart) {
		this.indexInCart = indexInCart;
	}

	public ProductInDevice getProduct() {
		return product;
	}

	public int getIndexInCart() {
		return indexInCart;
	}

	@FXML
	void clickOnMinus(ActionEvent event) {
		if (productController.getQuantityInOrder() == 0)
			return;
		if (product.getQuantity() == 0  )
			productController.resetErrorLabel();
		productController.setQuantityInOrder(productController.getQuantityInOrder() - 1);
		product.setQuantity(product.getQuantity() + 1);
		if (productController.getQuantityInOrder() == 0) {
			client_OrderScreenController.selectedProducts.remove(product);
			// client_OrderScreenController.removeFromCart(product, null);
			lblQuantity.setText(String.valueOf(0));
			lblPrice.setText(String.valueOf(0));
		} else {
			client_OrderScreenController.selectedProducts.put(product, productController.getQuantityInOrder());
			int amountOfProduct = ChatClient.cartController.getCart().get(product);
			double priceOfAmount = amountOfProduct * product.getPrice();
			lblQuantity.setText(String.valueOf(amountOfProduct));
			lblPrice.setText(String.valueOf(priceOfAmount));
		}
		client_OrderScreenController.setTotalAmount();
		if (client_OrderScreenController.selectedProducts.size() == 0) {
			client_OrderScreenController.changeEndOrder(true);
		}
	}
	

	public void setProductInCartController(ProductInDevice product, ProductController productController,
			Client_OrderScreenController screen) {
		this.client_OrderScreenController = screen;
		this.product = product;
		this.productController = productController;
	}

}