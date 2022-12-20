package entityControllers;

import entities.User;

public class UserController {
	private User user = null;

	// Constructors
	public UserController() {
	}

	public UserController(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isUserExist() {
		if(user == null)
			return false;
		return true;
	}
}
