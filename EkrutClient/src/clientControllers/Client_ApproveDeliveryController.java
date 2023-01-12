package clientControllers;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import entities.Delivery;
import entities.Message;
import enums.DeliveryStatus;
import enums.Request;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Client_ApproveDeliveryController {

	@FXML
	private Button btnApproveDelivery;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnexit1;

	@FXML
	private ImageView deliveryConfirm;

	@FXML
	private ImageView logo;

	@FXML
	private TableView<Delivery> orders;

	@FXML
	private TableColumn<Delivery, DeliveryStatus> status;

	@FXML
	private TableColumn<Delivery, Integer> orderID;

	private SetSceneController scene = new SetSceneController();

	private TableViewController approveTable = new TableViewController();
	private String username = ChatClient.userController.getUser().getUsername();

	
	/**
	 * Initializes the scene by setting the selection mode of the table and calling
	 * the setColumns and setTableItems methods.
	 */
	@FXML
	public void initialize() {
		Image image = new Image("/images/DeliveryConfirmation.png");
		deliveryConfirm.setImage(image);
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);
		orders.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumns();
		setTableItems();
	}

	/**
	 * Sets the columns in the table to display the specified fields.
	 */

	@FXML
	private void setColumns() {
		approveTable.setColumn(orderID, "orderID");
		approveTable.setColumn(status, "status");
	}
	
	/**
	 * Clears the items in the table and sets them 
	 * to the list of not-approved deliveries for the specified area on tblToApprove
	 * and on the way deliveries for the specified area on tblStatus
	 */
	private void setTableItems() {
		orders.getItems().clear();
		ClientUI.chat.accept(new Message(Request.Get_Customer_Deliveries, username));		
		orders.setItems(ChatClient.deliveryController.getUserDelivery());	
	
	}
	
	@FXML
	void clickbtnApproveDelivery(ActionEvent event) {
    	ObservableList<Delivery> selectedDeliveriees = orders.getSelectionModel().getSelectedItems();
		if (selectedDeliveriees.isEmpty()) {
			scene.popUpMessage("ERROR: No deliveries selected to approve!");
		} else {
			ArrayList<Delivery> deliveryToApprove = new ArrayList<>(selectedDeliveriees);
			for (Delivery delivery : deliveryToApprove) {
				delivery.setStatus(DeliveryStatus.DONE);
			}
			ClientUI.chat.accept(new Message(Request.Change_Delivery_Status, deliveryToApprove));
			setTableItems();
			scene.popUpMessage("Deliveries approved succesfully! ");
		}

	}

	@FXML
	void clickBtnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		if (ChatClient.configuration.equals("EK")) {
			scene.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
		} 
		else {
			scene.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
		}
	}

	@FXML
	void clickBtnExit(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}
}
