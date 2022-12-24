package entities;

import java.util.HashMap;

public class MonthlyOrderReport {
	private HashMap<String, Integer> itemsAndAmountHashMap = new HashMap<String, Integer>();
	private int totalOrdersCount = 0;
	private float ordersPerDayAvg = 0;
	
	String device, date, mostWantedItemName;
	
	public MonthlyOrderReport(HashMap<String, Integer> itemsAndAmountHashMap, int totalOrdersCount, float ordersPerDayAvg, String store, String date, String mostWantedItemName) {
		this.itemsAndAmountHashMap = itemsAndAmountHashMap;
		this.totalOrdersCount = totalOrdersCount;
		this.ordersPerDayAvg = ordersPerDayAvg;
		this.device = store;
		this.date = date;
		this.mostWantedItemName = mostWantedItemName;
	}


	public float getOrdersPerDayAvg() {
		return ordersPerDayAvg;
	}

	public void setOrdersPerDayAvg(float ordersPerDayAvg) {
		this.ordersPerDayAvg = ordersPerDayAvg;
	}
	public void setMostWantedItemName(String mostWantedItemName) {
		this.mostWantedItemName = mostWantedItemName;
	}

	public HashMap<String, Integer> getItemsAndAmountHashMap() {
		return itemsAndAmountHashMap;
	}
	public void setItemsAndAmountHashMap(HashMap<String, Integer> itemsAndAmountHashMap) {
		this.itemsAndAmountHashMap = itemsAndAmountHashMap;
	}
	
	public String getStore() {
		return device;
	}
	public String getDate() {
		return date;
	}
	public void setStore(String store) {
		this.device = store;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTotalOrdersCount() {
		return totalOrdersCount;
	}
	public void setTotalOrdersCount(int count) {
		totalOrdersCount = count;
	}
	public String getMostWantedItemName() {
		return mostWantedItemName;
	}
}
