package entityControllers;

import java.util.HashMap;

import entities.ProductInDevice;

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
