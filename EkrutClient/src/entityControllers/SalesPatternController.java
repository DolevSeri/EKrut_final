package entityControllers;

import entities.SalesPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalesPatternController {
	private ObservableList<SalesPattern> salespattern = FXCollections.observableArrayList();

	public ObservableList<SalesPattern> getSalespattern() {
		return salespattern;
	}

	public void setSalespattern(ObservableList<SalesPattern> salespattern) {
		this.salespattern = salespattern;
	}

}
