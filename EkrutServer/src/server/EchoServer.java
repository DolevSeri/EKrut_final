// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.IOException;

import entities.ClientConnected;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */

public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	private static ObservableList<ClientConnected> clientList = FXCollections.observableArrayList();

	public ObservableList<ClientConnected> getClientList() {

		return clientList;
	}

	public EchoServer(int port) {
		super(port);
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof String) {
			String messageFromClient = String.valueOf(msg);

			String[] result = messageFromClient.split("\\s");
			System.out.println("Message received: " + result[0] +" from " + client);
			//System.out.println("Message received: " + messageFromClient + " from " + client);
			switch (result[0]) {

			case "Connect":
				updateClientList(client, "Connected");
				try {
					client.sendToClient("Connected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Could not send message to client.");
				}
				break;
			case "Disconnect":
				updateClientList(client, "Disconnected");
				try {
					client.sendToClient("Disconnected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Could not send message to client.");
				}
				break;
			case "View":
				try {
					//sub = MySqlController.viewUser(result[1]);
					//ArrayList<String> arr = new ArrayList<>();
					//arr.add(sub.toString());
					client.sendToClient("View "+MySqlController.viewUser(result[1]));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Could not send message to client.");
				}
				break;
			case "Update":
				try {
					MySqlController.updateSubscriberTable(result);
					client.sendToClient("Update succeeded");
				} catch (IOException e) {
					System.out.println("Could not send message to client.");
				}
			default:
				break;
			}
		}
	}

	private void updateClientList(ConnectionToClient client, String connectionStatus) {
		//if (connectionStatus.equals("Disconnect")) {
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getIP().equals(client.getInetAddress().getHostAddress()))
				clientList.remove(i);
			}
		
		clientList.add(new ClientConnected(client.getInetAddress().getHostAddress(),
				client.getInetAddress().getHostAddress(), connectionStatus));
		// System.out.println(clientList);
	}
	/*
	 * private void updateClientList() {
	 * 
	 * (new clientConnected("1312312","dsadasdsad","SAdad")); (new
	 * clientConnected("131212","adasdsad","Sdad")); (new
	 * clientConnected("1","ds","S")); }
	 */

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}
}
//End of EchoServer class
