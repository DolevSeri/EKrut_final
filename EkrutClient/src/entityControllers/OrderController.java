package entityControllers;

import java.util.ArrayList;
import java.util.List;

import entities.Order;

/**
 * 
 * OrderController class is responsible for keeping a list of orders
 * 
 * and providing methods to set and get the list of orders.
 * 
 * @author Ron
 */
public class OrderController {
	private List<Order> orders = new ArrayList<>();

	public void setOrderList(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrdersList() {
		return this.orders;
	}
}
