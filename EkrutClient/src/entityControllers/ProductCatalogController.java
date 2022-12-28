package entityControllers;

import entities.ProductInDevice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductCatalogController {
	private static ObservableList<ProductInDevice> productsCatalog = FXCollections.observableArrayList();

	public ObservableList<ProductInDevice> getProductCatalog() {
		return productsCatalog;
	}

	public void setProductCatalog(ObservableList<ProductInDevice> observableList) {
		productsCatalog = observableList;
	}
}
