package entityControllers;

import entities.SalesPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * SalesPatternController is a class that holds the list of the sales pattern
 * that is used in the system.
 * 
 * @author Ron
 * 
 * @author Peleg
 */

public class SalesPatternController {

	/**
	 * 
	 * salespattern is an observable list that holds all the sales patterns
	 */
	private ObservableList<SalesPattern> salespattern = FXCollections.observableArrayList();

	/**
	 * 
	 * getSalespattern() is a method that returns the list of sales patterns
	 * 
	 * @return an observable list of SalesPattern
	 */
	public ObservableList<SalesPattern> getSalespattern() {
		return salespattern;
	}

	/**
	 * 
	 * setSalespattern() is a method that set the list of sales patterns
	 * 
	 * @param salespattern - an observable list of SalesPattern
	 */
	public void setSalespattern(ObservableList<SalesPattern> salespattern) {
		this.salespattern = salespattern;
	}
}
