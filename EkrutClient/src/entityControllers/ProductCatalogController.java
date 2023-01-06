package entityControllers;

import entities.ProductInDevice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductCatalogController {
	private static ObservableList<ProductInDevice> productsCatalog = FXCollections.observableArrayList();
	private ObservableList<String> productsInDevicesNames = FXCollections.observableArrayList();

	public ObservableList<ProductInDevice> getProductCatalog() {
		return productsCatalog;
	}

	public void setProductCatalog(ObservableList<ProductInDevice> observableList) {
		productsCatalog = observableList;
	}

	public ObservableList<String> getProductsInDevicesNames() {
		productsInDevicesNames.clear();
		for (ProductInDevice product : productsCatalog) {
			productsInDevicesNames.add(product.getProductName());
		}
		return productsInDevicesNames;
	}
}
