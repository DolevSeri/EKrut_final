package entityControllers;

import entities.User;

public interface UserControllerIF {
	public User getUser();
	public void setUser(User user);
	public boolean isUserExist();
}