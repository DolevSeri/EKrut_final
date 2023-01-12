package clientControllers;

import client.ChatClient;
import entities.ProductInDevice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * The ProductInCartController class is responsible for handling the logic and
 * event handling for the Product in Cart view.
 * 
 * It is connected to the corresponding FXML file and the
 * Client_OrderScreenController class.
 * 
 * @author Ron
 */
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

	/**
	 * 
	 * The setData method sets the product data for the Product in Cart view. It
	 * sets the name, quantity, price, and product image.
	 * 
	 * It also receives the client_OrderScreenController as a parameter to access
	 * its methods and fields.
	 * 
	 * @param product                      the ProductInDevice object that
	 *                                     represents the product in the cart.
	 * 
	 * @param client_OrderScreenController the Client_OrderScreenController object
	 *                                     that controls the Order Screen.
	 */
	public void setData(ProductInDevice product, Client_OrderScreenController client_OrderScreenController) {
		this.product = product;
		lblName.setText(product.getProductName());
		// check if product already in cart
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
		// set price of products in cart
		lblPrice.setText(String.format("%.2f", priceOfAmount));
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

	/**
	 * 
	 * The clickOnMinus method is an event handler for the 'minus' button in the
	 * ProductInCartController class. When the 'minus' button is clicked, the method
	 * decrements the quantity of the product in the cart and updates the GUI to
	 * reflect the change. If the product's quantity in the order is 0, the method
	 * returns without doing anything. If the product's quantity in the store is 0,
	 * it will reset the error label. It then decrements the quantity of the product
	 * in the order and increments the quantity of the product in the store. If the
	 * product's quantity in the order is now 0, the product is removed from the
	 * cart and the GUI is updated to reflect this. Otherwise, the GUI is updated to
	 * reflect the new quantity of the product in the cart. The method also updates
	 * the total amount of the order and checks if the cart is now empty. If so, it
	 * disables the 'end order' button.
	 * 
	 * @param event the ActionEvent object that triggered the method
	 */
	@FXML
	void clickOnMinus(ActionEvent event) {
		// if product is not in cart anymore.
		if (productController.getQuantityInOrder() == 0)
			return;
		if (product.getQuantity() == 0)
			// mark the client the product is now available for sale
			productController.resetErrorLabel();
		productController.setQuantityInOrder(productController.getQuantityInOrder() - 1);
		product.setQuantity(product.getQuantity() + 1);
		if (productController.getQuantityInOrder() == 0) {
			client_OrderScreenController.selectedProducts.remove(product);
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
		// if there are no products in cart anymore
		if (client_OrderScreenController.selectedProducts.size() == 0) {
			client_OrderScreenController.changeEndOrder(true);
		}
	}

	/**
	 * 
	 * The setProductInCartController method is used to set the product, product
	 * controller, and client_OrderScreenController fields of the
	 * ProductInCartController class.
	 * 
	 * @param product           the ProductInDevice object representing the product
	 *                          in the cart
	 * @param productController the ProductController object representing the
	 *                          controller of the product in the cart
	 * @param screen            the Client_OrderScreenController object representing
	 *                          the controller of the order screen
	 */
	public void setProductInCartController(ProductInDevice product, ProductController productController,
			Client_OrderScreenController screen) {
		this.client_OrderScreenController = screen;
		this.product = product;
		this.productController = productController;
	}

}