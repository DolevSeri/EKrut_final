package entityControllers;

import entities.Delivery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Ron
 * 
 *         This class is responsible for handling the deliveries data in the
 *         system. It contains two lists, one for deliveries that are waiting
 *         for approval and one for approved deliveries. It also contains a list
 *         of deliveries that are made by the current logged in user.
 */
public class DeliveryController {

	private ObservableList<Delivery> areaDeliveriesToApprove = FXCollections.observableArrayList();
	private ObservableList<Delivery> areaDeliveries = FXCollections.observableArrayList();
	private ObservableList<Delivery> userDelivery = FXCollections.observableArrayList();

	public ObservableList<Delivery> getUserDelivery() {
		return userDelivery;
	}

	public void setUserDelivery(ObservableList<Delivery> userDelivery) {
		this.userDelivery = userDelivery;
	}

	/**
	 * Get the list of deliveries that are waiting for approval.
	 * 
	 * @return areaDeliveriesToApprove - the list of deliveries that are waiting for
	 *         approval.
	 */
	public ObservableList<Delivery> getAreaDeliveriesToApprove() {
		return areaDeliveriesToApprove;
	}

	/**
	 * Set the list of deliveries that are waiting for approval.
	 * 
	 * @param areaDeliveriesToApprove - the list of deliveries that are waiting for
	 *                                approval.
	 */
	public void setAreaDeliveriesToApprove(ObservableList<Delivery> areaDeliveriesToApprove) {
		this.areaDeliveries.clear();
		this.areaDeliveriesToApprove = areaDeliveriesToApprove;
	}

	/**
	 * Get the list of approved deliveries.
	 * 
	 * @return areaDeliveries - the list of approved deliveries.
	 */
	public ObservableList<Delivery> getAreaDeliveries() {
		return areaDeliveries;
	}

	/**
	 * Set the list of approved deliveries.
	 * 
	 * @param areaDeliveries - the list of approved deliveries.
	 */
	public void setAreaDeliveries(ObservableList<Delivery> areaDeliveries) {
		this.areaDeliveries.clear();
		this.areaDeliveries = areaDeliveries;
	}

}
