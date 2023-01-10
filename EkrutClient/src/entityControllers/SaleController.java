package entityControllers;

import entities.Sale;
import entities.SalesPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaleController {
	private ObservableList<Sale> sales = FXCollections.observableArrayList();

	public ObservableList<Sale> getSales() {
		return sales;
	}

	public void setSales(ObservableList<Sale> sales) {
		this.sales = sales;
	}
	
	
}
