package entityControllers;

import entities.Costumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CostumerController {
	private ObservableList<Costumer> areaCostumers = FXCollections.observableArrayList();

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
