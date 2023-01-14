package entityControllers;

import entities.Costumer;
import entities.Order;
import enums.SupplyMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**

@author Ron Lahiani and Peleg Oanuno
The class CostumerController is responsible for handling the costumers data.
*/
public class CostumerController {
	private ObservableList<Costumer> areaCostumers = FXCollections.observableArrayList();
	private SupplyMethod suplyMethod;
	private ObservableList<Integer> pickUpOrders = FXCollections.observableArrayList();
	private Costumer customerToUpdate = null;
	private Costumer getNameByOrderID = null;
	
	
	public Costumer getGetNameByOrderID() {
		return getNameByOrderID;
	}

	public void setGetNameByOrderID(Costumer getNameByOrderID) {
		this.getNameByOrderID = getNameByOrderID;
	}

	public Costumer getCustomerToUpdate() {
		return customerToUpdate;
	}

	public void setCustomerToUpdate(Costumer customerToUpdate) {
		this.customerToUpdate = customerToUpdate;
	}
	private ObservableList<Order> ordersofcostumer = FXCollections.observableArrayList();

	public ObservableList<Integer> getPickUpOrders() {
		return pickUpOrders;
	}

	public void setPickUpOrders(ObservableList<Integer> pickUpOrders) {
		this.pickUpOrders = pickUpOrders;
	}

	public ObservableList<Order> getOrdersofcostumer() {
		return ordersofcostumer;
	}

	public void setOrdersofcostumer(ObservableList<Order> ordersofcostumer) {
		this.ordersofcostumer = ordersofcostumer;
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
