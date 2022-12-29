package entities;

import java.io.Serializable;

import enums.Configuration;
import enums.CostumerStatus;
import enums.Devices;
import enums.Region;
import enums.Role;

/**
 * 
 * @author ron and peleg Class that will save costumer data
 */
public class Costumer extends User implements Serializable {

	private String creditCard;
	private String subscriberID;
	private CostumerStatus status;
	private Devices device;

	public Costumer(String username, String password, String firstName, String lastName, String email,
			String phoneNumber, boolean isLoggedIn, String id, Role role, Region region, Configuration configuration,
			String creditCard, String subscriberID, CostumerStatus status, Devices device) {
		
		
		super(username, password, firstName, lastName, email, phoneNumber, isLoggedIn, id, role, region, configuration);
		this.creditCard = creditCard;
		this.subscriberID = subscriberID;
		this.status = status;
		this.device = device;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public String getSubscriberID() {
		return subscriberID;
	}

	public CostumerStatus getStatus() {
		return status;
	}

	public Devices getDevice() {
		return device;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public void setSubscriberID(String subscriberID) {
		this.subscriberID = subscriberID;
	}

	public void setStatus(CostumerStatus status) {
		this.status = status;
	}

	public void setDevice(Devices device) {
		this.device = device;
	}

}
