// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.ChatIF;
import entities.Costumer;
import entities.CostumersReport;
import entities.Delivery;
import entities.DeliveryReport;
import entities.Device;
import entities.InventoryCall;
import entities.InventoryReport;
import entities.Message;
import entities.Order;
import entities.OrderReport;
import entities.ProductInDevice;
import entities.Sale;
import entities.SalesPattern;
import entities.Subscriber;
import entities.User;
import entityControllers.CartController;
import entityControllers.CostumerController;
import entityControllers.CostumersReportController;
import entityControllers.DeliveryController;
import entityControllers.DeliveryReportController;
import entityControllers.DeviceController;
import entityControllers.InventoryCallController;
import entityControllers.InventoryReportController;
import entityControllers.OrderController;
import entityControllers.OrderReportController;
import entityControllers.ProductCatalogController;
import entityControllers.SaleController;
import entityControllers.SalesPatternController;
import entityControllers.UserController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import ocsf.client.AbstractClient;

/**
 * ChatClient is a class that extends the AbstractClient class from the Open
 * Client-Server Framework (OCSF)
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
	public static OrderReportController orderReportController = new OrderReportController();
	public static DeliveryReportController deliveryReportController = new DeliveryReportController();
	public static InventoryReportController inventoryReportController = new InventoryReportController();
	public static CostumersReportController costumersReportController = new CostumersReportController();
	public static boolean awaitResponse = false;
	public static Object lock = new Object();
	public static CartController cartController = new CartController();
	public static OrderController orderController = new OrderController();
	public static InventoryCallController inventoryCallController = new InventoryCallController();
	public static SalesPatternController salesPatternController = new SalesPatternController();
	public static SaleController salesController = new SaleController();
	public static UserController usersController = null;
	public static DeliveryController deliveryController = new DeliveryController();
	public static String configuration;
	public static Thread checkWindowTimeThread;
	public static Stage primaryStage = null;
	public static ArrayList<Sale> salesForSubscriber = new ArrayList<>();
	public static boolean firstOrderSubscriber = false;
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

	@SuppressWarnings({ "unchecked" })
	public void handleMessageFromServer(Object msg) {

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
			break;

		case Devices_Imported:
			deviceController.setAreaDevices(FXCollections.observableArrayList((ArrayList<Device>) message.getObject()));
			break;

		case OrdersReportData_Imported:
			orderReportController.setOrderReport((OrderReport) message.getObject());
			break;

		case DeliveryReportData_Imported:
			deliveryReportController.setDeliveryReport((DeliveryReport) message.getObject());
			break;

		case InventoryReportData_Imported:
			inventoryReportController.setInventoryReport((InventoryReport) message.getObject());
			break;
		case CostumersReportData_Imported:
			costumersReportController.setCostumersReport((CostumersReport) message.getObject());
			break;

		case Threshold_Updated:
			break;

		case Products_Imported:
			productCatalogController.setProductCatalog(
					FXCollections.observableArrayList((ArrayList<ProductInDevice>) message.getObject()));
			break;
		case Costumer_Imported:
			Costumer costumer = (Costumer) message.getObject();
			costumerController.setCostumer(costumer);
			break;
		case Costumers_Imported:
			costumerController
					.setAreaCostumers(FXCollections.observableArrayList((ArrayList<Costumer>) message.getObject()));
			break;
		case Costumer_Status_Updated:
			break;
		case Inventory_Call_Created:
			inventoryCallController.setCreated((boolean) message.getObject());
			break;
		case Orders_imported:
			List<Order> orders = (ArrayList<Order>) message.getObject();
			orderController.setOrderList(orders);
			break;
		case Order_Saved:
			break;
		case Products_updated_In_Device:
			break;
		case SalesPattern_Saved:
			break;
		case imported_SalesPattern:
			salesPatternController
					.setSalespattern(FXCollections.observableArrayList((ArrayList<SalesPattern>) message.getObject()));
			break;
		case imported_Sales:
			salesController.setSales(FXCollections.observableArrayList((ArrayList<Sale>) message.getObject()));
			break;
		case Sales_Saved:
			break;
		case Inventory_Calls_Imported:
			inventoryCallController
					.setAreaCalls(FXCollections.observableArrayList((ArrayList<InventoryCall>) message.getObject()));
			break;
		case User_Data_Imported:
			userController.setUserToUpdate((User) message.getObject());
			break;
		case TakeAway_Saved:
			break;
		case PickUp_Orders_imported:
			costumerController
					.setPickUpOrders(FXCollections.observableArrayList((ArrayList<Integer>) message.getObject()));
			break;
		case Updated_PickUp_Status:
			break;
		case Delivery_Saved:
			break;
		case Customer_Created:
			break;
		case Customer_Data_Imported:
			costumerController.setCustomerToUpdate((Costumer) message.getObject());
			break;
		case Customer_Updated:
		case Updated_sale_status:
			break;
		case Imported_orderbyname:
			costumerController
					.setOrdersofcostumer((FXCollections.observableArrayList((ArrayList<Order>) message.getObject())));
			break;
		case SaleStatus_Updateddone:
			break;
		case Area_Deliveries_Imported:
			ArrayList<Delivery> deliveries = (ArrayList<Delivery>) message.getObject();
			deliveryController.setAreaDeliveries((FXCollections.observableArrayList(deliveries)));
			break;
		case Area_Deliveries_ToApprove_Imported:
			ArrayList<Delivery> toApprove = (ArrayList<Delivery>) message.getObject();
			deliveryController.setAreaDeliveriesToApprove((FXCollections.observableArrayList(toApprove)));
			break;
		case Delivery_Status_Changed:
			break;
		case Product_quantity_updated_succesfully_call_closed:

			break;
		case Msg_Notification:
			break;
		case New_Notification:
			showMsg((String) message.getObject());
			break;
		case Customer_Deliveries_Imported:
			ArrayList<Delivery> customerDeli = (ArrayList<Delivery>) message.getObject();
			deliveryController.setUserDelivery((FXCollections.observableArrayList(customerDeli)));
			break;
		case Area_manager_UserName_Imported:
			userController.setAreaManagerUserNAme((String) message.getObject());
			break;
		case Delivery_Arrival_Changed:
			break;
		case Customer_Username_Imported:
			costumerController.setGetNameByOrderID((Costumer) message.getObject());
			break;
		case ThresholdTable_Updated:
			break;
		case Arrived_Deliveries_Imported:
			ArrayList<Delivery> arrivedDeliveries = (ArrayList<Delivery>) message.getObject();
			deliveryController.addDeliveriesToAreaDeliv((FXCollections.observableArrayList(arrivedDeliveries)));
			break;
		case NeedToActivateSale_imported:
			salesController
					.setSalesNeedToActivate(FXCollections.observableArrayList((ArrayList<Sale>) message.getObject()));
			break;
		default:
			break;
		}
		synchronized (lock) {
			awaitResponse = false;
			lock.notify();
		}
	}

	private void showMsg(String txt) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText(txt);

			// Set the alert dialog style
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.setStyle("-fx-background-color:  #D0A9F5;");

			alert.showAndWait();
		});
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
			synchronized (lock) {
				// wait for response
				while (awaitResponse) {
					try {
						lock.wait();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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
			synchronized (lock) {
				while (awaitResponse) {
					try {
						lock.wait();

					} catch (InterruptedException e) {
					}
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