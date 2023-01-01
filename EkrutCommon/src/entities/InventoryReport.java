package entities;

import java.io.Serializable;
import java.util.HashMap;

public class InventoryReport implements Serializable {
	private static final long serialVersionUID = 1L; 
	private String month, year, deviceName;
	private HashMap<String, Integer> producsUnderThreshold = new HashMap<>();
	private String mexProductUnderThres;
	
	public InventoryReport(String month, String year, String deviceName, HashMap<String, Integer> producsUnderThreshold,
			String mexProductUnderThres) {
		super();
		this.month = month;
		this.year = year;
		this.deviceName = deviceName;
		this.producsUnderThreshold = producsUnderThreshold;
		this.mexProductUnderThres = mexProductUnderThres;
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
