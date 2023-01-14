package entities;

import java.io.Serializable;

import enums.CostumerStatus;
import enums.Region;
import enums.Role;

/**
 * 
 * @author Ron Lahiani
 * @author Peleg Oanuno The Costumer class represents a customer in the system.
 *         It extends the User class and implements the Serializable interface.
 */
public class Costumer extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String creditCard;
	private int subscriberID;
	private CostumerStatus status;
	private String deviceName;

	public Costumer(String username, String password, String firstName, String lastName, String email,
			String phoneNumber, boolean isLoggedIn, String id, Role role, Region region, String creditCard,
			int subscriberID, CostumerStatus status, String deviceName) {

		super(username, password, firstName, lastName, email, phoneNumber, isLoggedIn, id, role, region);
		this.creditCard = creditCard;
		this.subscriberID = subscriberID;
		this.status = status;
		this.deviceName = deviceName;
	}

	public Costumer(String username, String creditCard, int subscriberID, CostumerStatus status) {
		super(username);
		this.creditCard = creditCard;
		this.subscriberID = subscriberID;
		this.status = status;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public int getSubscriberID() {
		return subscriberID;
	}

	public CostumerStatus getStatus() {
		return status;
	}

	public String getDevice() {
		return deviceName;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public void setSubscriberID(int subscriberID) {
		this.subscriberID = subscriberID;
	}

	public void setStatus(CostumerStatus status) {
		this.status = status;
	}

	public void setDevice(String deviceName) {
		this.deviceName = deviceName;
	}

}
