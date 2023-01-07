package entities;

import java.io.Serializable;

public class SalesPattern implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int patternID=0;
   private String discountType;
   private String startDay;
   private String endDay;
   private String startHour;
   private String duration;
   
   public SalesPattern(int patternID,String discountType,String startDay,String endDay,String startHour,String duration) {
	   this.patternID=patternID;
	   this.discountType=discountType;
	   this.startDay=startDay;
	   this.endDay=endDay;
	   this.startHour=startHour;
	   this.duration=duration;
   }

public  int getPatternID() {
	return patternID;
}

public void setPatternID(int patternID) {
	this.patternID= patternID;
}

public String getDiscountType() {
	return discountType;
}

public void setDiscountType(String discountType) {
	this.discountType = discountType;
}

public String getStartDay() {
	return startDay;
}

public void setStartDay(String startDay) {
	this.startDay = startDay;
}

public String getEndDay() {
	return endDay;
}

public void setEndDay(String endDay) {
	this.endDay = endDay;
}

public String getStartHour() {
	return startHour;
}

public void setStartHour(String startHour) {
	this.startHour = startHour;
}

public String getDuration() {
	return duration;
}

public void setDuration(String duration) {
	this.duration = duration;
}
   
	
}
