package entities;

import java.io.Serializable;

import enums.CallStatus;

public class InventoryCall implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String deviceName, productName;
	private int callID;
	CallStatus status;
	
	public InventoryCall(int callID, CallStatus status,  String deviceName, String productName) {
		this.deviceName = deviceName;
		this.productName = productName;
		this.callID = callID;
		this.status = status;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public CallStatus getStatus() {
		return status;
	}

	public void setStatus(CallStatus status) {
		this.status = status;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getCallID() {
		return callID;
	}

	public void setCallID(int callID) {
		this.callID = callID;
	}
	
	
}
