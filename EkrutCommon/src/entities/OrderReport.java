package entities;

import java.io.Serializable;
import java.util.HashMap;

public class OrderReport implements Serializable {
	private static final long serialVersionUID = 1L; 
	private HashMap<String, Integer> deviceAndAmountHashMap = new HashMap<String, Integer>();
	private HashMap<String, Float> deviceAndIncomeHashMap = new HashMap<String, Float>();
	private int totalOrdersCount = 0, numOfPickUpOrders=0;
	private float ordersPerDayAvg = 0;
	
	String area, month, year, mostSellingDevice;
	
	public OrderReport(HashMap<String, Integer> deviceAndAmountHashMap, 
			int totalOrdersCount, float ordersPerDayAvg, int numOfPickUpOrders,
			String area, String month, String year, String mostSellingDevice, HashMap<String, Float> deviceAndIncomeHashMap ) {
		this.deviceAndAmountHashMap = deviceAndAmountHashMap;
		this.deviceAndIncomeHashMap = deviceAndIncomeHashMap;
		this.totalOrdersCount = totalOrdersCount;
		this.ordersPerDayAvg = ordersPerDayAvg;
		this.area = area;
		this.month = month;
		this.year = year;
		this.mostSellingDevice = mostSellingDevice;
		this.numOfPickUpOrders = numOfPickUpOrders;
	}


	public HashMap<String, Float> getDeviceAndIncomeHashMap() {
		return deviceAndIncomeHashMap;
	}


	public void setDeviceAndIncomeHashMap(HashMap<String, Float> deviceAndIncomeHashMap) {
		this.deviceAndIncomeHashMap = deviceAndIncomeHashMap;
	}


	public float getOrdersPerDayAvg() {
		return ordersPerDayAvg;
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


	public void setOrdersPerDayAvg(float ordersPerDayAvg) {
		this.ordersPerDayAvg = ordersPerDayAvg;
	}
	public void setMostSellingDevice(String mostSellingDevice) {
		this.mostSellingDevice = mostSellingDevice;
	}

	public HashMap<String, Integer> getDeviceAndAmountHashMap() {
		return deviceAndAmountHashMap;
	}
	public void setDeviceAndAmountHashMap(HashMap<String, Integer> deviceAndAmountHashMap) {
		this.deviceAndAmountHashMap = deviceAndAmountHashMap;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getTotalOrdersCount() {
		return totalOrdersCount;
	}
	public void setTotalOrdersCount(int count) {
		totalOrdersCount = count;
	}
	public String getMostSellingDevice() {
		return mostSellingDevice;
	}


	public int getNumOfPickUpOrders() {
		return numOfPickUpOrders;
	}


	public void setNumOfPickUpOrders(int numOfPickUpOrders) {
		this.numOfPickUpOrders = numOfPickUpOrders;
	}
}
