package entities;

import java.io.Serializable;

import enums.CallStatus;

public class InventoryCall implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String deviceNeme, productName;
	private int callID;
	CallStatus isOpen;
	
	public InventoryCall(String deviceNeme, String productName, int callID, CallStatus isOpen) {
		super();
		this.deviceNeme = deviceNeme;
		this.productName = productName;
		this.callID = callID;
		this.isOpen = isOpen;
	}

	public String getDeviceNeme() {
		return deviceNeme;
	}

	public void setDeviceNeme(String deviceNeme) {
		this.deviceNeme = deviceNeme;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public CallStatus getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(CallStatus isOpen) {
		this.isOpen = isOpen;
	}
	
	
}
