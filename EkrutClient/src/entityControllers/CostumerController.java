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
	private Costumer costumer = null;
	private  CostumerControllerIF costomercontroller;
	
	public CostumerController() {
		costomercontroller=new Costumerimplem();
	}
	public CostumerController(CostumerControllerIF costomercontroller) {
		this.costomercontroller=costomercontroller;
	}
	
	
	
	
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

	

	

	public CostumerController(Costumer costumer) {
		this.costumer = costumer;
	}

	public Costumer getCostumer() {
		return  costomercontroller.getCostumer();
	}

	public void setCostumer(Costumer costumer) {
		costomercontroller.setCostumer(costumer);
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
	
	public class Costumerimplem implements CostumerControllerIF{

		@Override
		public Costumer getCostumer() {
			
			return costumer;
		}
		public void setCostumer(Costumer costumer1) {
			costumer = costumer1;
		}
		
	}
}