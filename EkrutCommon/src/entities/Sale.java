package entities;

import java.io.Serializable;

import enums.Region;
import enums.SaleStatus;

public class Sale extends SalesPattern implements Serializable{
    private Region region;
    private int saleID;
    private SaleStatus status;
	public Sale(int patternID, String discountType, String startDay, String endDay, String startHour, String duration,Region region,int saleID,SaleStatus status) {
		super(patternID, discountType, startDay, endDay, startHour, duration);
		this.region=region;
		this.saleID=saleID;
		this.status=status;
		// TODO Auto-generated constructor stub
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public int getSaleID() {
		return saleID;
	}
	public void setSaleID(int saleID) {
		this.saleID = saleID;
	}
	public SaleStatus getStatus() {
		return status;
	}
	public void setStatus(SaleStatus status) {
		this.status = status;
	}
	

}
