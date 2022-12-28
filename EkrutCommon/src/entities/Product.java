package entities;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private int productCode;
	private String productName;
	private double price;
	private String imagePath;

	public Product(int productCode, String productName, double price, String imagePath) {

		this.productCode = productCode;
		this.productName = productName;
		this.price = price;
		this.imagePath = imagePath;
	}

	public int getProductCode() {
		return productCode;
	}

	public String getProductName() {
		return productName;
	}

	public double getPrice() {
		return price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * 
	 */
}
