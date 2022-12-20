package entities;

public class User {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private boolean isLoggedIn;
	private String id;

	public User(String username, String password, String firstName, String lastName, String email, String phoneNumber,
			boolean isLoggedIn, String id) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.isLoggedIn = isLoggedIn;
		this.id = id;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return this.username;
	}

	public String getPswd() {
		return this.password;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String userId) {
		this.id = userId;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}
}