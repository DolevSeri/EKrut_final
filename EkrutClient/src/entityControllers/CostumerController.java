package entityControllers;

import entities.Costumer;
import enums.SupplyMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CostumerController {
	private ObservableList<Costumer> areaCostumers = FXCollections.observableArrayList();
	private SupplyMethod suplyMethod;
	private ObservableList<Integer> pickUpOrders = FXCollections.observableArrayList();

	public ObservableList<Integer> getPickUpOrders() {
		return pickUpOrders;
	}

	public void setPickUpOrders(ObservableList<Integer> pickUpOrders) {
		this.pickUpOrders = pickUpOrders;
	}

	public SupplyMethod getSuplyMethod() {
		return suplyMethod;
	}

	public void setSuplyMethod(SupplyMethod suplyMethod) {
		this.suplyMethod = suplyMethod;
	}

	private Costumer costumer = null;

	// Constructors
	public CostumerController() {
	}

	public CostumerController(Costumer costumer) {
		this.costumer = costumer;
	}

	public Costumer getCostumer() {
		return costumer;
	}

	public void setCostumer(Costumer costumer) {
		this.costumer = costumer;
	}

	public boolean isCostumerExist() {
		if (costumer == null)
			return false;
		return true;
	}

	public ObservableList<Costumer> getAreaCostumers() {
		return areaCostumers;
	}

	public void setAreaCostumers(ObservableList<Costumer> areaCostumers) {
		this.areaCostumers = areaCostumers;
	}
}
