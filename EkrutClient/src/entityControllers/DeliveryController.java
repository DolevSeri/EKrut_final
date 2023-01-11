package entityControllers;

import entities.Delivery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeliveryController {
	
	private ObservableList<Delivery> areaDeliveriesToApprove = FXCollections.observableArrayList();
	private ObservableList<Delivery> areaDeliveries = FXCollections.observableArrayList();
	
	
	public ObservableList<Delivery> getAreaDeliveriesToApprove() {
		return areaDeliveriesToApprove;
	}
	
	public void setAreaDeliveriesToApprove(ObservableList<Delivery> areaDeliveriesToApprove) {
		this.areaDeliveries.clear();
		this.areaDeliveriesToApprove = areaDeliveriesToApprove;
	}
	
	public ObservableList<Delivery> getAreaDeliveries() {
		return areaDeliveries;
	}
	
	public void setAreaDeliveries(ObservableList<Delivery> areaDeliveries) {
		this.areaDeliveries.clear();
		this.areaDeliveries = areaDeliveries;
	}
	

}
