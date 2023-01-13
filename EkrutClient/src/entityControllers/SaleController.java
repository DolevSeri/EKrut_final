package entityControllers;

import entities.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * The SaleController class is responsible for handling the sale data.
 * 
 * It contains an ObservableList of Sale objects, which can be accessed and
 * manipulated through the getter and setter methods.
 * 
 * @author Ron
 * 
 * @author Peleg
 */
public class SaleController {

	/**
	 * 
	 * ObservableList of Sale objects that stores the sale data
	 */
	private ObservableList<Sale> sales = FXCollections.observableArrayList();

	/**
	 * 
	 * Returns the ObservableList of Sale objects
	 * 
	 * @return the sales
	 */
	public ObservableList<Sale> getSales() {
		return sales;
	}

	/**
	 * 
	 * Sets the ObservableList of Sale objects
	 * 
	 * @param sales the sales to set
	 */
	public void setSales(ObservableList<Sale> sales) {
		this.sales = sales;
	}

}
