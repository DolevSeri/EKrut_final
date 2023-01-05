package entityControllers;

import java.util.ArrayList;
import java.util.List;

import entities.Order;

public class OrderController {
	private List<Order> orders = new ArrayList<>();
	
	public void setOrderList(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrdersList() {
		return this.orders;
	}
}
