package entities;

import enums.SupplyMethod;

public class Order {
	private String deviceID;
	private int orderID;
	private float orderPrice;
	private String costumerID;
	private String orderDate;
	private SupplyMethod supplyMethod;
	private String orderProducts;
	

	
	public Order(String deviceID, int orderID, float orderPrice, String costumerID, String orderDate,
			SupplyMethod supplyMethod, String orderProducts) {
		this.deviceID = deviceID;
		this.orderID = orderID;
		this.orderPrice = orderPrice;
		this.costumerID = costumerID;
		this.orderDate = orderDate;
		this.supplyMethod = supplyMethod;
		this.orderProducts = orderProducts;
	}
	

	public String getOrderProducts() {
		return orderProducts;
	}


	public void setOrderProducts(String orderProducts) {
		this.orderProducts = orderProducts;
	}


	public String getOrdetMonth() {
		return orderDate.substring(5, 7);
	}
	
	public String getOrderYear() {
		return orderDate.substring(0, 4);
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public float getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getCostumerID() {
		return costumerID;
	}

	public void setCostumerID(String costumerID) {
		this.costumerID = costumerID;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public SupplyMethod getSupplyMethod() {
		return supplyMethod;
	}

	public void setSupplyMethod(SupplyMethod supplyMethod) {
		this.supplyMethod = supplyMethod;
	}
	
	
	
	

}
