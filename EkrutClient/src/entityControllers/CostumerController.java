package entityControllers;

import entities.Costumer;
import entities.User;

public class CostumerController {
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
}
