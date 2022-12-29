package entities;

import java.io.Serializable;

import enums.Devices;

public class Device implements Serializable {
	private static final long serialVersionUID = 1L;

	private String deviceName;
	private String deviceID;
	private int threshold;
	private Devices region;

	public Device(String deviceID, int threshold, Devices region, String deviceName) {
		super();
		this.deviceID = deviceID;
		this.deviceName = deviceName;
		this.threshold = threshold;
		this.region = region;
	}


	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public Devices getRegion() {
		return region;
	}

	public void setRegion(Devices region) {
		this.region = region;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
