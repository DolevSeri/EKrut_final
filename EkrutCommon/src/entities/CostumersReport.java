package entities;

import java.io.Serializable;

import enums.Region;

public class CostumersReport implements Serializable {
	private static final long serialVersionUID = 1L; 
	private int lowActivity, mediumActivity, highActivity, veryHighActivity;
	private String month, year;
	private Region area;
	
	public CostumersReport(int lowActivity, int mediumActivity, int highActivity, int veryHighActivity, String month,
			String year, Region area) {
		super();
		this.lowActivity = lowActivity;
		this.mediumActivity = mediumActivity;
		this.highActivity = highActivity;
		this.veryHighActivity = veryHighActivity;
		this.month = month;
		this.year = year;
		this.area = area;
	}

	public int getLowActivity() {
		return lowActivity;
	}

	public void setLowActivity(int lowActivity) {
		this.lowActivity = lowActivity;
	}

	public int getMediumActivity() {
		return mediumActivity;
	}

	public void setMediumActivity(int mediumActivity) {
		this.mediumActivity = mediumActivity;
	}

	public int getHighActivity() {
		return highActivity;
	}

	public void setHighActivity(int highActivity) {
		this.highActivity = highActivity;
	}

	public int getVeryHighActivity() {
		return veryHighActivity;
	}

	public void setVeryHighActivity(int veryHighActivity) {
		this.veryHighActivity = veryHighActivity;
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

	public Region getArea() {
		return area;
	}

	public void setArea(Region area) {
		this.area = area;
	}

}
