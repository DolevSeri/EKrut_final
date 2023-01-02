package entityControllers;

import entities.Device;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeviceController {
	private ObservableList<Device> areaDevices = FXCollections.observableArrayList();
	private ObservableList<String> areaDevicesNames = FXCollections.observableArrayList();

	public ObservableList<Device> getAreaDevices() {
		return areaDevices;
	}

	public void setAreaDevices(ObservableList<Device> areaDevices) {
		this.areaDevices.clear();
		this.areaDevices = areaDevices;
	}

	public ObservableList<String> getAreaDevicesNames() {
		areaDevicesNames.clear();
		for (Device device : areaDevices) {
			areaDevicesNames.add(device.getDeviceName().toString());
		}
		return areaDevicesNames;
	}
}
