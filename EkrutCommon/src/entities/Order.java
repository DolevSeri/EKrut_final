package entities;

import java.io.Serializable;

import enums.SupplyMethod;

/**
 * Order class that implements Serializable. this class hold the order when
 * client is making order, and use for import orders from DB
 * 
 * @author Peleg Oanuno
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private String deviceName;
	private int orderID;
	private float orderPrice;
	private String username;
	private String day;
	private String month;
	private String year;
	private SupplyMethod supplyMethod;
	private String orderProducts;

	public Order(String deviceName, int orderID, float orderPrice, String username, String day, String month,
			String year, SupplyMethod supplyMethod, String orderProducts) {
		this.deviceName = deviceName;
		this.orderID = orderID;
		this.orderPrice = orderPrice;
		this.username = username;
		this.day = day;
		this.month = month;
		this.year = year;
		this.supplyMethod = supplyMethod;
		this.orderProducts = orderProducts;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(String orderProducts) {
		this.orderProducts = orderProducts;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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

	public String getCostumerUserName() {
		return username;
	}

	public void setCostumerID(String username) {
		this.username = username;
	}

	public SupplyMethod getSupplyMethod() {
		return supplyMethod;
	}

	public void setSupplyMethod(SupplyMethod supplyMethod) {
		this.supplyMethod = supplyMethod;
	}

}
