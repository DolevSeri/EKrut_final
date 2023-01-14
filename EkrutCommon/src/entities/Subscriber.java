package entities;

import java.io.Serializable;

/**
 * Subscriber is class that contain costumers that registered as subscribers and
 * have their own details
 * 
 * @author Ron Lahiani
 *
 */
public class Subscriber implements Serializable {
	private String ID, firstName, lastName, phoneNumber, emailAddress, creditCardNumber, subscriberNumber;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getSubscriberNumber() {
		return subscriberNumber;
	}

	public void setSubscriberNumber(String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
	}

	public String getID() {
		return ID;
	}

	public Subscriber(String ID, String firstName, String lastName, String phoneNumber, String emailAddress,
			String creditCardNumber, String subscriberNumber) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.creditCardNumber = creditCardNumber;
		this.subscriberNumber = subscriberNumber;

	}

}
