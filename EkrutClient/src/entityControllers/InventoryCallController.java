package entityControllers;

import entities.InventoryCall;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryCallController {
	private ObservableList<InventoryCall> areaCalls = FXCollections.observableArrayList();

	public ObservableList<InventoryCall> getAreaCalls() {
		return areaCalls;
	}

	public void setAreaCalls(ObservableList<InventoryCall> areaCalls) {
		this.areaCalls = areaCalls;
	}

}
