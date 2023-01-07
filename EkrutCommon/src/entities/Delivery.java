package entities;

import java.io.Serializable;

import enums.DeliveryStatus;
import enums.Region;

public class Delivery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String costumerAdress;
	private DeliveryStatus status;
	private int orderID;
	private Region region;

	public Delivery(String costumerAdress, DeliveryStatus status, int orderID, Region region) {
		super();
		this.costumerAdress = costumerAdress;
		this.status = status;
		this.orderID = orderID;
		this.region = region;
	}

	public String getCostumerAdress() {
		return costumerAdress;
	}

	public DeliveryStatus getStatus() {
		return status;
	}

	public int getOrderID() {
		return orderID;
	}

	public Region getRegion() {
		return region;
	}

	public void setCostumerAdress(String costumerAdress) {
		this.costumerAdress = costumerAdress;
	}

	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
}
