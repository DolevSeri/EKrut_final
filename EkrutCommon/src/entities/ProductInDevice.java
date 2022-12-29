package entities;

import enums.Devices;
import enums.ProductStatus;

public class ProductInDevice extends Product {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int quantity;
	private ProductStatus status;
	private String device;

	public ProductInDevice(int productCode, String productName, double price, String imagePath, int quantity,
			ProductStatus status, String device) {
		super(productCode, productName, price, imagePath);
		this.status = status;
		this.device = device;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public String getDevice() {
		return device;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public void setDevice(String device) {
		this.device = device;
	}

}
