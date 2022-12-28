package entities;

import enums.Devices;
import enums.ProductStatus;

public class ProductInDevice extends Product {
	private int quantity;
	private ProductStatus status;
	private Devices device;

	public ProductInDevice(int productCode, String productName, float price, String imagePath, int quantity,
			ProductStatus status, Devices device) {
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

	public Devices getDevice() {
		return device;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public void setDevice(Devices device) {
		this.device = device;
	}

}
