package clientControllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import entities.Delivery;
import entities.Message;
import enums.DeliveryStatus;
import enums.Request;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The class DeliveryOperator_ManageDeliveriesController is responsible for
 * managing the deliveries in the system, it allows the operator to approve or
 * disapprove deliveries and view the deliveries that have been approved or are
 * on the way.
 * 
 * @author Eden Bar
 */
public class DeliveryOperator_ManageDeliveriesController {

	@FXML
	private Button btnBack;

	@FXML
	private Button btnexit;

	@FXML
	private Button btnApproveDelivery;

	@FXML
	private Button btnCloseDelivery;

	@FXML
	private TableView<Delivery> tblStatus;

	@FXML
	private TableColumn<Delivery, Integer> orderNum;

	@FXML
	private TableColumn<Delivery, DeliveryStatus> status;

	@FXML
	private TableView<Delivery> tblToApprove;

	@FXML
	private TableColumn<Delivery, Integer> orderId;

	@FXML
	private TableColumn<Delivery, DeliveryStatus> toApprove;

	@FXML
	private TableColumn<Delivery, String> deliveryAddress;

	@FXML
	private ImageView logo;

	private SetSceneController scene = new SetSceneController();

	private TableViewController statusTable = new TableViewController();
	private TableViewController approveTable = new TableViewController();
	private String area = ChatClient.userController.getUser().getRegion().toString();

	/**
	 * Initializes the scene by setting the selection mode of the table and calling
	 * the setColumns and setTableItems methods.
	 */
	@FXML
	public void initialize() {
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);
		tblStatus.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tblToApprove.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumns();
		setTableItems();
	}

	/**
	 * Sets the columns in the table to display the specified fields.
	 */

	@FXML
	private void setColumns() {
		statusTable.setColumn(orderNum, "orderID");
		statusTable.setColumn(status, "status");
		approveTable.setColumn(orderId, "orderID");
		approveTable.setColumn(deliveryAddress, "costumerAdress");
		approveTable.setColumn(toApprove, "status");
	}

	/**
	 * Clears the items in the table and sets them to the list of not-approved
	 * deliveries for the specified area on tblToApprove and on the way deliveries
	 * for the specified area on tblStatus
	 */
	private void setTableItems() {
		ArrayList<String> data = new ArrayList<>();
		data.add(area);
		data.add("NOTAPPROVED");
		tblToApprove.getItems().clear();
		ClientUI.chat.accept(new Message(Request.Get_Deliveries_ToApprove_By_Area, data));
		tblToApprove.setItems(ChatClient.deliveryController.getAreaDeliveriesToApprove());

		data.remove("NOTAPPROVED");

		tblStatus.getItems().clear();
		data.add("APPROVED");
		ClientUI.chat.accept(new Message(Request.Get_Deliveries_By_Area, data));
		data.remove("APPROVED");
		data.add("ARRIVED");
		ClientUI.chat.accept(new Message(Request.Get_Arrived_Deliveries, data));

		tblStatus.setItems(ChatClient.deliveryController.getAreaDeliveries());

	}

	/**
	 * Method that handles the approve delivery action. Updates the status of
	 * selected deliveries to "APPROVED" and sends a notification to the customer
	 * with the estimated arrival time.
	 * @param event - the ActionEvent that triggered the method
	 * @FXML void clickbtnApproveDelivery(ActionEvent event)
	 */
	@FXML
	void clickbtnApproveDelivery(ActionEvent event) {
		ObservableList<Delivery> selectedDeliveriees = tblToApprove.getSelectionModel().getSelectedItems();
		String[] arrival = null;
		if (selectedDeliveriees.isEmpty()) {
			scene.popUpMessage("ERROR: No deliveries selected to approve!");
		} else {
			ArrayList<Delivery> deliveryToApprove = new ArrayList<>(selectedDeliveriees);
			for (Delivery delivery : deliveryToApprove) {
				ArrayList<String> nameAndMessage = new ArrayList<>();
				delivery.setStatus(DeliveryStatus.APPROVED);
				arrival = estimatedArrival(LocalDateTime.now(), delivery.getCostumerAdress());
				delivery.setArrivalDate(arrival[0]);
				delivery.setArrivalHour(arrival[1]);
				ClientUI.chat.accept(new Message(Request.Get_Customer_Username, delivery.getOrderID()));

				nameAndMessage.addAll(Arrays.asList(ChatClient.costumerController.getGetNameByOrderID().getUsername(),
						"Your delivery has been approved!\nEsstimated arrival time is:" + delivery.getArrivalDate()
								+ " " + delivery.getArrivalHour()));
				ClientUI.chat.accept(new Message(Request.Send_Notification, nameAndMessage));
			}
			ClientUI.chat.accept(new Message(Request.Change_Delivery_Status, deliveryToApprove));
			ClientUI.chat.accept(new Message(Request.Change_Delivery_Arrival, deliveryToApprove));
			setTableItems();
			scene.popUpMessage(
					"Deliveries approved succesfully!\nSMS and Email with estimated arrival time sent to customer");

		}
	}

	/**
	 * Method that handles the close delivery action. Updates the status of selected
	 * deliveries to "DONE" only if the delivery status is "ARRIVED".
	 * @param event - the ActionEvent that triggered the method
	 * @FXML void clickbtnCloseDelivery(ActionEvent event)
	 */
	@FXML
	void clickbtnCloseDelivery(ActionEvent event) {
		ObservableList<Delivery> selectedDeliveriees = tblStatus.getSelectionModel().getSelectedItems();
		if (selectedDeliveriees.isEmpty()) {
			scene.popUpMessage("ERROR: No deliveries selected to approve!");
		} else {
			ArrayList<Delivery> deliveryToApprove = new ArrayList<>(selectedDeliveriees);
			for (Delivery delivery : deliveryToApprove) {
				if (delivery.getStatus().toString().equals("APPROVED")) {
					scene.popUpMessage(
							"You can't close deliveries that not arrived to customer yet!\nPlease choose 'ARRIVED' deliveries only");
					return;
				}
				delivery.setStatus(DeliveryStatus.DONE);
			}
			ClientUI.chat.accept(new Message(Request.Change_Delivery_Status, deliveryToApprove));
			setTableItems();
			scene.popUpMessage("Deliveries closed succesfully!");
		}
	}

	/**
	 * 
	 * Method that calculates the flight time of a delivery. 
	 * For now - return constant
	 * @param wareHouse - the warehouse location
	 * @param clientAddress - the customer delivery address
	 * @return long - the flight time of the delivery long
	 */
	public long calculateFlightTime(String wareHouse, String clientAddress) {
		return 3L;
	}

	/**
	 * Method that calculates the waiting time for the drone
	 * @return long - the drone waiting time long droneWaiting() 
	 * For now - return constant
	 */
	public long droneWaiting() {
		return 2L;
	}

	/**
	 * 
	 * Method that calculates the time required to load the shipment
	 * 
	 * @return long - the shipment load time long calculateShipmantLoad() 
	 * For now - return constant
	 */
	public long calculateShipmantLoad() {
		return 3L;
	}

	/**
	 * 
	 * Method that calculates the total delivery time from warehouse to customer
	 * 
	 * @param cAddress - the customer delivery address
	 * @return long - the total delivery time long calTotalTotalTime(String cAddress)
	 */
	public long calTotalTotalTime(String cAddress) {
		return calculateFlightTime(null, cAddress) + droneWaiting() + calculateShipmantLoad();
	}

	/**
	 * Method that calculates the estimated arrival time for a delivery
	 * @param date - the current date and time
	 * @param cAddress - the customer delivery address
	 * @return String[] - the estimated arrival date and hour in format "dd-MM-yyyy"
	 * and "HH:mm:ss" respectively String[] estimatedArrival(LocalDateTimedate, String cAddress)
	 */
	public String[] estimatedArrival(LocalDateTime date, String cAddress) {
		long totalDeliveryHours = calTotalTotalTime(cAddress);
		LocalDateTime estDate = date.plusHours(totalDeliveryHours);
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		String[] arrivalDate = new String[2];
		arrivalDate[0] = estDate.format(dateFormatter);
		arrivalDate[1] = estDate.format(timeFormatter);

		return arrivalDate;
	}

	/**
	 * Go back to the previous scene
	 * 
	 * @param event the event that triggered the method call
	 */
	@FXML
	void clickBtnBack(ActionEvent event) {
		scene.back(event, "/clientGUI/DeliveryOperator_MeinView.fxml");
	}

	/**
	 * Exit the application
	 * 
	 * @param event the event that triggered the method call
	 */
	@FXML
	void clickBtnExit(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

}
