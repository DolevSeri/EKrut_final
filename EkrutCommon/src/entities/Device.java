package entities;

import java.io.Serializable;

import enums.Region;

/**
 * 
 * @author Eden Bar
 * 
 *         The Device class represents a device in the system. It implements the
 *         Serializable interface.
 * 
 */
public class Device implements Serializable {
	private static final long serialVersionUID = 1L;

	private String deviceName;
	private int threshold;
	private Region region;

	public Device(int threshold, Region region, String deviceName) {
		super();
		this.deviceName = deviceName;
		this.threshold = threshold;
		this.region = region;
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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
