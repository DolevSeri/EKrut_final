package entities;

import java.io.Serializable;

import enums.ProductStatus;

/**
 * ProductUnderThreshold will contain products that under threshold level in
 * specific device
 * 
 * @author Ron Lahiani
 *
 */
public class ProductUnderThreshold implements Serializable {

	public ProductUnderThreshold(String deviceName, int prCode, String month, String year, int count) {
		this.month = month;
		this.year = year;
		this.count = count;
		this.deviceName = deviceName;
		this.prCode = prCode;
	}

	/**
	 * 
	 */
	private String month;
	private String year;
	private int count;
	private String deviceName;
	private int prCode;

	public String getDeviceName() {
		return deviceName;
	}

	public int getPrCode() {
		return prCode;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setPrCode(int prCode) {
		this.prCode = prCode;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public int getCount() {
		return count;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private static final long serialVersionUID = 1L;

}
