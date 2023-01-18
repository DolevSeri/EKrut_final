package server;

import java.util.ArrayList;

import entities.User;

public interface LoginInterfaceDB {
	public User LoginCheckAndUpdateLoggedIn(ArrayList<String> userANDpassword);
}
