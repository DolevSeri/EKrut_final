package entityControllers;

import entities.InventoryCall;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryCallController {
	private boolean isCreated;


	private ObservableList<InventoryCall> areaCalls = FXCollections.observableArrayList();

	public ObservableList<InventoryCall> getAreaCalls() {
		return areaCalls;
	}

	public void setAreaCalls(ObservableList<InventoryCall> areaCalls) {
		this.areaCalls = areaCalls;
	}
	
	public boolean isCreated() {
		return isCreated;
	}

	public void setCreated(boolean isCreated) {
		this.isCreated = isCreated;
	}
}
