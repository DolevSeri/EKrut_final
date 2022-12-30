// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;
import java.util.ArrayList;

import common.ChatIF;
import entities.Costumer;
import entities.Device;
import entities.Product;
import entities.ProductInDevice;
import entities.Message;
import entities.Subscriber;
import entities.User;
import entityControllers.CostumerController;
import entityControllers.DeviceController;
import entityControllers.ProductCatalogController;
import entityControllers.UserController;
import javafx.collections.FXCollections;
import ocsf.client.AbstractClient;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	public static Subscriber s1 = new Subscriber(null, null, null, null, null, null, null);
	public static UserController userController = new UserController();
	public static DeviceController deviceController = new DeviceController();
	public static ProductCatalogController productCatalogController = new ProductCatalogController();
	public static CostumerController costumerController = new CostumerController();
	public static boolean awaitResponse = false;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String ip, int port, ChatIF clientUI) throws IOException {
		super(ip, port); // Call the superclass constructor
		this.clientUI = clientUI;
		// openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */

	@SuppressWarnings({ "unchecked"})
	public void handleMessageFromServer(Object msg) {
		awaitResponse = false;
		Message message = (Message) msg;
		System.out.println("Message received: " + ((Message) msg).getRequest().toString() + " from server");

		switch (message.getRequest()) {
		case Connected:
			System.out.println("Client connected to Server!");
			break;
		case Disconnected:
			break;
		case LoggedIn_Succses:
			userController.setUser((User) message.getObject());
			break;
		case LoggedIn_UnsuccsesAlreadyLoggedIn:
			userController.setUser((User) message.getObject());
			break;
		case Unsuccsesful_LogIn:
			userController.setUser(null);
			break;
		case LoggedOut:
			userController.setUser(null);
		case Devices_Imported:
			deviceController.setAreaDevices(FXCollections.observableArrayList((ArrayList<Device>) message.getObject()));
		case Threshold_Updated:
			break;
		case Products_Imported:
			productCatalogController.setProductCatalog(
					FXCollections.observableArrayList((ArrayList<ProductInDevice>) message.getObject()));
		case Costumer_Imported:
			Costumer costumer = (Costumer) message.getObject();
			costumerController.setCostumer(costumer);
		default:
			break;
		}
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(String message) {

		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);

			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
	}

	public void handleMessageFromClientUI(Message message) {
		try {
			openConnection();
			awaitResponse = true;
			sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			clientUI.display("Could not send message to server.  Terminating client." + e);
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class