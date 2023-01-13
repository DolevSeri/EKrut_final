package entityControllers;

import java.util.HashMap;

import entities.ProductInDevice;

/**
 * 
 * The class CartController holds the cart of the client, where the client can
 * add and remove products in the cart.
 * 
 * @author Ron Lahiani and Peleg Oanuno
 */
public class CartController {
	private static HashMap<ProductInDevice, Integer> cart = new HashMap<>();

	public void setCart(HashMap<ProductInDevice, Integer> cart) {
		CartController.cart = cart;
	}

	public HashMap<ProductInDevice, Integer> getCart() {
		return cart;
	}

	public void clearCart() {
		this.cart.clear();
	}

}
