package entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * 
 * @author Ron Lahiani
 * 
 *         The InventoryReport class represents a report of the inventory of a
 *         certain device for a certain month and year. It includes information
 * 
 *         about products that are below the threshold and the most extreme
 *         product that is below the threshold. It implements the Serializable
 *         interface.
 * 
 */
public class InventoryReport implements Serializable {
	private static final long serialVersionUID = 1L;
	private String month, year, deviceName;
	private HashMap<String, Integer> producsUnderThreshold = new HashMap<>();
	private String mexProductUnderThres;
	private Integer deviceThres;
	
	@Override
	public int hashCode() {
		return Objects.hash(deviceName, deviceThres, mexProductUnderThres, month, producsUnderThreshold, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryReport other = (InventoryReport) obj;
		return Objects.equals(deviceName, other.deviceName) && Objects.equals(deviceThres, other.deviceThres)
				&& Objects.equals(mexProductUnderThres, other.mexProductUnderThres)
				&& Objects.equals(month, other.month)
				&& Objects.equals(producsUnderThreshold, other.producsUnderThreshold)
				&& Objects.equals(year, other.year);
	}



	public InventoryReport(String month, String year, String deviceName, HashMap<String, Integer> producsUnderThreshold,
			String mexProductUnderThres, Integer deviceThres) {
		super();
		this.month = month;
		this.year = year;
		this.deviceName = deviceName;
		this.producsUnderThreshold = producsUnderThreshold;
		this.mexProductUnderThres = mexProductUnderThres;
		this.deviceThres = deviceThres;
	}

	public Integer getDeviceThres() {
		return deviceThres;
	}

	public void setDeviceThres(Integer deviceThres) {
		this.deviceThres = deviceThres;
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

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public HashMap<String, Integer> getProducsUnderThreshold() {
		return producsUnderThreshold;
	}

	public void setProducsUnderThreshold(HashMap<String, Integer> producsUnderThreshold) {
		this.producsUnderThreshold = producsUnderThreshold;
	}

	public String getMexProductUnderThres() {
		return mexProductUnderThres;
	}

	public void setMexProductUnderThres(String mexProductUnderThres) {
		this.mexProductUnderThres = mexProductUnderThres;
	}

}
