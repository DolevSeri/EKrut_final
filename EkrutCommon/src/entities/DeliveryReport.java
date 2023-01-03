package entities;

import java.io.Serializable;

public class DeliveryReport implements Serializable {
	private static final long serialVersionUID = 1L; 

	String month, year;
	int numOfDeliveries=0;
	float totalSumIncomes=0;
	
	public DeliveryReport(String month, String year, int numOfDeliveries, float totalSumIncomes) {
		super();
		this.month = month;
		this.year = year;
		this.numOfDeliveries = numOfDeliveries;
		this.totalSumIncomes = totalSumIncomes;
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
	public int getNumOfDeliveries() {
		return numOfDeliveries;
	}
	public void setNumOfDeliveries(int numOfDeliveries) {
		this.numOfDeliveries = numOfDeliveries;
	}
	public float getTotalSumIncomes() {
		return totalSumIncomes;
	}
	public void setTotalSumIncomes(float totalSumIncomes) {
		this.totalSumIncomes = totalSumIncomes;
	}


}
