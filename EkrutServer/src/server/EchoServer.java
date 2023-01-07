// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.ClientConnected;
import entities.Costumer;
import entities.Device;
import entities.InventoryCall;
import entities.Message;
import entities.Order;
import entities.ProductInDevice;
import entities.Sale;
import entities.SalesPattern;
import entities.SystemMessage;
import entities.User;
import enums.Request;
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
	final public static int DEFAULT_PORT = 5555;

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
	@SuppressWarnings("unchecked")
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message messageFromClient = (Message) msg;
		System.out.println("Message received: " + ((Message) msg).getRequest().toString() + " from " + client);
		switch (messageFromClient.getRequest()) {
		case Connect_request:
			updateClientList(client, "Connected");
			try {
				client.sendToClient(new Message(Request.Connected, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Could not send message to client.");
			}
			break;
		case Disconnect_request:
			updateClientList(client, "Disconnected");
			try {
				client.sendToClient(new Message(Request.Disconnected, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Could not send message to client.");
			}
			break;
		case Login_Request:

			ArrayList<String> userANDpassword = (ArrayList<String>) messageFromClient.getObject();
			User user = MySqlController.LoginCheckAndUpdateLoggedIn(userANDpassword);
			Message msgToClient;
			if (user != null) {
				if (user.isLoggedIn() == false) {
					System.out.println("Username: " + user.getUsername() + " Password: " + user.getPassword());
					System.out.println("User details were imported successfully");
					msgToClient = new Message(Request.LoggedIn_Succses, user);
				} else {
					System.out.println("User already logged in");
					msgToClient = new Message(Request.LoggedIn_UnsuccsesAlreadyLoggedIn, user);
				}
			} else {
				System.out.println("User login failed");
				msgToClient = new Message(Request.Unsuccsesful_LogIn, null);
			}
			// Send message to the client that everything went well
			try {
				client.sendToClient(msgToClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Logout_request:
			User userLogout = (User) messageFromClient.getObject();
			try {
				MySqlController.UserLogoutAndUpdateDB(userLogout);
				try {
					client.sendToClient(new Message(Request.LoggedOut, null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Get_Devices_By_Area:
			String deviceArea = (String) messageFromClient.getObject();
			try {
				client.sendToClient(
						new Message(Request.Devices_Imported, MySqlController.getAllDevicesByArea(deviceArea)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case GetDeliveryReportData:
			ArrayList<String> deliveryFields = (ArrayList<String>) messageFromClient.getObject();
			try {
				client.sendToClient(new Message(Request.DeliveryReportData_Imported,
						MySqlController.getDeliveryReportData(deliveryFields)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case GetOrdersReportData:
			ArrayList<String> ordersFields = (ArrayList<String>) messageFromClient.getObject();
			try {
				client.sendToClient(new Message(Request.OrdersReportData_Imported,
						MySqlController.getOrdersReportData(ordersFields)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case GetInventoryReportData:
			ArrayList<String> inventoryFields = (ArrayList<String>) messageFromClient.getObject();
			try {
				client.sendToClient(new Message(Request.InventoryReportData_Imported,
						MySqlController.getInventoryReportData(inventoryFields)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case GetCostumersReportData:
			ArrayList<String> costumersFields = (ArrayList<String>) messageFromClient.getObject();
			try {
				client.sendToClient(new Message(Request.CostumersReportData_Imported,
						MySqlController.getCostumersReportData(costumersFields)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case Threshold_Update_Request:
			MySqlController.updateDeviceThreshold((ArrayList<Device>) messageFromClient.getObject());
			try {
				client.sendToClient(new Message(Request.Threshold_Updated, null));
			} catch (IOException e) {
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Products:
			String deviceName = (String) messageFromClient.getObject();
			try {
				client.sendToClient(
						new Message(Request.Products_Imported, MySqlController.getProductsFromDevice(deviceName)));
			} catch (IOException e) {
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Costumer:
			String userID = (String) messageFromClient.getObject();
			try {
				client.sendToClient(new Message(Request.Costumer_Imported, MySqlController.getCostumerData(userID)));
			} catch (IOException e) {
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Not_Approved_Costumers_By_Area:
			String costumerArea = (String) messageFromClient.getObject(); //
			try {
				client.sendToClient(new Message(Request.Costumers_Imported,
						MySqlController.getNotApprovedCostumersByArea(costumerArea)));
			} catch (IOException e) {
				System.out.println("Could not send message to client.");
			}
			break;
		case Costumer_Update_Status_Request:
			MySqlController.updateCostumerStatus((ArrayList<Costumer>) messageFromClient.getObject());
			try {
				client.sendToClient(new Message(Request.Costumer_Status_Updated, null));
			} catch (IOException e) {
				System.out.println("Could not send message to client.");
			}
			break;

		case Create_Inventory_Call:
			MySqlController.createInventoryCall((ArrayList<String>) messageFromClient.getObject());
			try {
				client.sendToClient(new Message(Request.Inventory_Call_Created, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
		case getOrders:
			try {
				client.sendToClient(new Message(Request.Orders_imported, MySqlController.importOrders()));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
		case SaveOrder:
			Order order = (Order) messageFromClient.getObject();
			MySqlController.updateOrder(order);
			try {
				client.sendToClient(new Message(Request.Order_Saved, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Update_Products_In_Device:
			List<ProductInDevice> productsInDevice = (List<ProductInDevice>) messageFromClient.getObject();
			MySqlController.updateProductsInDevice(productsInDevice);
			try {
				client.sendToClient(new Message(Request.Products_updated_In_Device, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case get_Msg_In_System:
			try {
				client.sendToClient(new Message(Request.imported_Msg_In_System, MySqlController.getMessagesInSystem()));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;

		case Get_Inventory_Calls_By_Area:
			ArrayList<String> callsAreaAndStatus = (ArrayList<String>) messageFromClient.getObject();

			try {
				client.sendToClient(new Message(Request.Inventory_Calls_Imported,
						MySqlController.getInventoryCallsByRegionAndStatus(callsAreaAndStatus)));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			

		case Update_SalesPattern:
			SalesPattern sp= (SalesPattern) messageFromClient.getObject();
			MySqlController.salesPatternToDB(sp);
			try {
				client.sendToClient(new Message(Request.SalesPattern_Saved, null));
			}catch(IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case import_SalesPattern:
			try {
				client.sendToClient(new Message(Request.imported_SalesPattern, MySqlController.importSalesPattern()));
			}catch(IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case import_Sales:
			try {
				client.sendToClient(new Message(Request.imported_Sales, MySqlController.importSales()));
			}catch(IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Update_Sales:
			Sale sale= (Sale) messageFromClient.getObject();
			MySqlController.updateSaleInDB(sale);
			try {
				client.sendToClient(new Message(Request.Sales_Saved, null));
			}catch(IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Send_msg_to_system:
			SystemMessage systemMsg = (SystemMessage) messageFromClient.getObject();
			MySqlController.updateSystemMessageTable(systemMsg);
			try {
				client.sendToClient(new Message(Request.System_msg_updated, null));
			}catch(IOException e) {
				e.printStackTrace();
        }
        break;
		case Inventory_Calls_To_Close:
			MySqlController.closeInventoryCalls((ArrayList<InventoryCall>)messageFromClient.getObject());
			try {
				client.sendToClient(new Message(Request.Inventory_Calls_Closed, null));
			} catch (IOException e) {
				System.out.println("Could not send message to client.");
			}
			break;
		default:
			break;
		}
	}

	private void updateClientList(ConnectionToClient client, String connectionStatus) {
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getIP().equals(client.getInetAddress().getHostAddress()))
				clientList.remove(i);
		}

		clientList.add(new ClientConnected(client.getInetAddress().getHostAddress(),
				client.getInetAddress().getHostAddress(), connectionStatus));
	}

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
