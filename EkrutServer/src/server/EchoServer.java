// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Costumer;
import entities.Delivery;
import entities.Device;
import entities.Message;
import entities.Order;
import entities.ProductInDevice;
import entities.Sale;
import entities.SalesPattern;
import entities.User;
import enums.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * EchoServer is a class that extends the AbstractServer class from the Open
 * Client-Server Framework (OCSF) and implements the Singleton pattern to ensure
 * that only one instance of the EchoServer is created. It is also thread-safe
 * to create the instance in multi-thread environment.
 * 
 * @author peleg
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
	// instance-will help us to do singleton implementation for the EchoServer
	private static EchoServer instance = null;
	// an object that will help us to synchronized in case there are some threads
	private static final Object lock = new Object();

	/**
	 * Creates a new EchoServer object with the specified port.
	 *
	 * @param port the server port
	 */
	protected EchoServer(int port) {
		super(port);
	   // mysql=new LoginFromDB();
	}

	public ObservableList<ClientConnected> getClientList() {

		return clientList;
	}

	/**
	 * Returns the singleton instance of the EchoServer class. If an instance does
	 * not exist, it creates a new one.
	 *
	 * @param port the server port
	 * @return the singleton instance of the EchoServer class
	 */
	public static EchoServer getInstance(int port) {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new EchoServer(port);
				}
			}
		}
		return instance;
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
					setUserNameForClient(client, user.getUsername());
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
		case Get_Products_under_thres:
			String device = (String) messageFromClient.getObject();
			try {
				client.sendToClient(new Message(Request.Products_Imported,
						MySqlController.getProductsUnderThresholdFromDevice(device)));
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
				client.sendToClient(new Message(Request.Inventory_Call_Created,
						MySqlController.createInventoryCall((ArrayList<String>) messageFromClient.getObject())));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case getOrders:
			try {
				client.sendToClient(new Message(Request.Orders_imported, MySqlController.importOrders()));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
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

		case Get_Inventory_Calls_By_Area:
			ArrayList<String> callsAreaAndStatus = (ArrayList<String>) messageFromClient.getObject();

			try {
				client.sendToClient(new Message(Request.Inventory_Calls_Imported,
						MySqlController.getInventoryCallsByRegionAndStatus(callsAreaAndStatus)));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Update_SalesPattern:
			SalesPattern sp = (SalesPattern) messageFromClient.getObject();
			MySqlController.salesPatternToDB(sp);
			try {
				client.sendToClient(new Message(Request.SalesPattern_Saved, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case import_SalesPattern:
			try {
				client.sendToClient(new Message(Request.imported_SalesPattern, MySqlController.importSalesPattern()));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case import_Sales:
			try {
				client.sendToClient(new Message(Request.imported_Sales, MySqlController.importSales()));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Update_Sales:
			Sale sale = (Sale) messageFromClient.getObject();
			MySqlController.updateSaleInDB(sale);
			try {
				client.sendToClient(new Message(Request.Sales_Saved, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case importNeedToActivateSale:
			try {
				client.sendToClient(
						new Message(Request.NeedToActivateSale_imported, MySqlController.importNEEDTOACTIVATESales()));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Update_Customer_Request:
			MySqlController.updateCostumerToMember((String) messageFromClient.getObject());
			try {
				client.sendToClient(new Message(Request.Customer_Updated, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Customer_Data:
			try {
				client.sendToClient(new Message(Request.Customer_Data_Imported,
						MySqlController.importCustomerDataToUpdate((String) messageFromClient.getObject())));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_User_Data:
			User user1 = MySqlController.importUserData((ArrayList<String>) messageFromClient.getObject());
			try {
				client.sendToClient(new Message(Request.User_Data_Imported, user1));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Save_TakeAway:
			Order pickUpOrder = (Order) messageFromClient.getObject();
			MySqlController.savePickUpOrderInDB(pickUpOrder.getOrderID());
			try {
				client.sendToClient(new Message(Request.TakeAway_Saved, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_PickUp_Orders:
			Costumer costumerPickUp = (Costumer) messageFromClient.getObject();
			try {
				client.sendToClient(new Message(Request.PickUp_Orders_imported,
						MySqlController.importPickUpOrders(costumerPickUp.getUsername())));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Update_PickUp_Status:
			Integer orderToUpdate = (Integer) messageFromClient.getObject();
			MySqlController.updatePickUPasCollected(orderToUpdate);
			try {
				client.sendToClient(new Message(Request.Updated_PickUp_Status, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Save_New_Delivery:
			Delivery delivery = (Delivery) messageFromClient.getObject();
			MySqlController.saveDeliveryInOrders(delivery);
			try {
				client.sendToClient(new Message(Request.Delivery_Saved, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Create_Customer_Request:
			ArrayList<String> userData = (ArrayList<String>) messageFromClient.getObject();
			MySqlController.updateUserToCustomer(userData);
			try {
				client.sendToClient(new Message(Request.Customer_Created, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Update_sale_status:
			Sale saleToUpdate = (Sale) messageFromClient.getObject();
			MySqlController.updateSaleStatusInDB(saleToUpdate);
			try {
				client.sendToClient(new Message(Request.Updated_sale_status, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Import_orderbyname:
			String username = (String) messageFromClient.getObject();
			try {
				client.sendToClient(
						new Message(Request.Imported_orderbyname, MySqlController.getOrdersDataOfUSER(username)));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Update_SaleStatusdone:
			Sale saleToUpdatedone = (Sale) messageFromClient.getObject();
			MySqlController.updateSaleStatusdoneInDB(saleToUpdatedone);
			try {
				client.sendToClient(new Message(Request.SaleStatus_Updateddone, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Deliveries_By_Area:
			try {
				client.sendToClient(new Message(Request.Area_Deliveries_Imported,
						MySqlController.getDeliveriesByArea((ArrayList<String>) messageFromClient.getObject())));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Deliveries_ToApprove_By_Area:
			try {
				client.sendToClient(new Message(Request.Area_Deliveries_ToApprove_Imported,
						MySqlController.getDeliveriesByArea((ArrayList<String>) messageFromClient.getObject())));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Change_Delivery_Status:
			MySqlController.updateDeliveryStatus((ArrayList<Delivery>) messageFromClient.getObject());
			try {
				client.sendToClient(new Message(Request.Delivery_Status_Changed, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Customer_Deliveries:
			try {
				client.sendToClient(new Message(Request.Customer_Deliveries_Imported,
						MySqlController.getDeliveriesByCustomer((String) messageFromClient.getObject())));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Send_Notification:
			ArrayList<String> nameAndMessage = (ArrayList<String>) messageFromClient.getObject();
			ConnectionToClient clientToSendMsg = getClientByUserName(nameAndMessage.get(0));
			try {
				if (clientToSendMsg != null) {
					clientToSendMsg.sendToClient(new Message(Request.New_Notification, nameAndMessage.get(1)));
				}
				client.sendToClient(new Message(Request.Msg_Notification, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Area_manager_UserName:
			String area = (String) messageFromClient.getObject();
			try {
				client.sendToClient(new Message(Request.Area_manager_UserName_Imported,
						MySqlController.getAreaManagerUsername(area)));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case UpdateProductQuantityAndCloseCall:
			ArrayList<String> data = (ArrayList<String>) messageFromClient.getObject();
			MySqlController.UpdateProductQuantityAndCloseCall(data);
			try {
				client.sendToClient(new Message(Request.Product_quantity_updated_succesfully_call_closed, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Change_Delivery_Arrival:
			MySqlController.updateDeliveryArrivalTime((ArrayList<Delivery>) messageFromClient.getObject());
			try {
				client.sendToClient(new Message(Request.Delivery_Arrival_Changed, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Customer_Username:
			try {
				client.sendToClient(new Message(Request.Customer_Username_Imported,
						MySqlController.getCustomerByOrderID((int) messageFromClient.getObject())));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Update_ThresholdTable:
			ProductInDevice productToUpdate = (ProductInDevice) messageFromClient.getObject();
			MySqlController.updateProductInProductsUnderThreshold(productToUpdate);
			try {
				client.sendToClient(new Message(Request.ThresholdTable_Updated, null));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		case Get_Arrived_Deliveries:
			try {
				client.sendToClient(new Message(Request.Arrived_Deliveries_Imported,
						MySqlController.getDeliveriesByArea((ArrayList<String>) messageFromClient.getObject())));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not send message to client.");
			}
			break;
		default:
			break;
		}
	}

	private void updateClientList(ConnectionToClient client, String connectionStatus) {
		// Create a new instance of the ClientConnected class
		ClientConnected newClient = new ClientConnected(client.getInetAddress().getHostAddress(),
				client.getInetAddress().getHostAddress(), connectionStatus, client);
		// Remove an existing client with the same IP address
		clientList.removeIf(c -> c.getIP().equals(newClient.getIP()));
		// Add the new client to the list
		clientList.add(newClient);
	}

	private void setUserNameForClient(ConnectionToClient client, String userName) {
		ClientConnected matchedClientConnected = clientList.stream()
				.filter(cc -> cc.getIP().equals(client.getInetAddress().getHostAddress())).findFirst().get();
		matchedClientConnected.setUserName(userName);
	}

	private ConnectionToClient getClientByUserName(String username) {
		ClientConnected matchedClientConnected = clientList.stream().filter(cc -> cc.getUserName().equals(username))
				.findFirst().orElse(null);
		if (matchedClientConnected == null) {
			return null;
		}
		return matchedClientConnected.getClient();
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
