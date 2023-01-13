package client;

import entities.Costumer;
import entities.Message;
import entities.User;
/**
 * ChatClientIF-An interface that will help us to inject dependencies, we can use an object
 *  that will implement it and thus in the test we can return in the functions that the connection depends on what
 *   we will need for the test
 * @author peleg
 *
 */
public interface ChatClientIF {
    public boolean isUserExist();
    public User getUser();
    public Costumer getCostumer();
    public void accept(Message msg);
    public boolean isLoggedIn();
    public String getRole();
    public String getStatus();
    public String getConfiguration();
}
