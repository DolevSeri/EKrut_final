package entityControllers;

import entities.Device;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeviceController {
	private static ObservableList<Device> areaDevices = FXCollections.observableArrayList();
	private static ObservableList<String> areaDevicesNames = FXCollections.observableArrayList();

	public ObservableList<Device> getAreaDevices() {
		return areaDevices;
	}

	public void setAreaDevices(ObservableList<Device> areaDevice) {
		areaDevices.clear();
		System.out.println(areaDevices.toString()+"mmmmmm");
		areaDevices = areaDevice;
	}

	public ObservableList<String> getAreaDevicesNames() {
		areaDevicesNames.clear();
		for (Device device : areaDevices) {
			areaDevicesNames.add(device.getDeviceName().toString());
		}
		return areaDevicesNames;
	}
}
