package entityControllers;

import entities.ProductInDevice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * The ProductCatalogController class is responsible for managing the product
 * catalog.
 * 
 * It holds an observable list of productInDevice that contains all the products
 * in the catalog.
 * 
 * It also holds an observable list of product names that contains all the
 * product names in the catalog.
 * 
 * @author Ron
 * 
 * @author Peleg
 */
public class ProductCatalogController {

	/**
	 * 
	 * ObservableList of products in the catalog.
	 */
	private static ObservableList<ProductInDevice> productsCatalog = FXCollections.observableArrayList();
	/**
	 * 
	 * ObservableList of product names in the catalog.
	 */
	private ObservableList<String> productsInDevicesNames = FXCollections.observableArrayList();

	/**
	 * 
	 * Getter for productsCatalog.
	 * 
	 * @return an observable list of all the products in the catalog.
	 */
	public ObservableList<ProductInDevice> getProductCatalog() {
		return productsCatalog;
	}

	/**
	 * 
	 * Setter for productsCatalog.
	 * 
	 * @param observableList of products to set as the catalog.
	 */
	public void setProductCatalog(ObservableList<ProductInDevice> observableList) {
		productsCatalog = observableList;
	}

	/**
	 * 
	 * Getter for productsInDevicesNames.
	 * 
	 * @return an observable list of all the product names in the catalog.
	 */
	public ObservableList<String> getProductsInDevicesNames() {
		productsInDevicesNames.clear();
		for (ProductInDevice product : productsCatalog) {
			productsInDevicesNames.add(product.getProductName());
		}
		return productsInDevicesNames;
	}
}