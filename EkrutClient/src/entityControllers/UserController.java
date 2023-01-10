package entityControllers;

import entities.User;

public class UserController {
	private User user = null;
	private User userToUpdate = null;

	public User getUserToUpdate() {
		return userToUpdate;
	}

	public void setUserToUpdate(User userToUpdate) {
		this.userToUpdate = userToUpdate;
	}

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
	
	public boolean isUserToApproveExist() {
		if(userToUpdate == null)
			return false;
		return true;
	}
}
