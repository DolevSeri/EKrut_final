package entityControllers;

import entities.SalesPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaleController {
	private ObservableList<SalesPattern> sales = FXCollections.observableArrayList();

	public ObservableList<SalesPattern> getSales() {
		return sales;
	}

	public void setSales(ObservableList<SalesPattern> sales) {
		this.sales = sales;
	}
	
	
}
