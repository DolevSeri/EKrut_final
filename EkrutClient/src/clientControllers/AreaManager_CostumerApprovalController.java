package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import entities.Costumer;
import entities.Message;
import enums.CostumerStatus;
import enums.Request;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * This class represents the Area Manager Costumer Approval controller in the
 * application. It allows the area manager to approve or reject costumers in
 * their region.
 * 
 * @author Inbar Mizrahi
 */
public class AreaManager_CostumerApprovalController {
	private SetSceneController scene = new SetSceneController();

	@FXML
	private Button btnexit;

	@FXML
	private Button btnBack;

	@FXML
	private TableView<Costumer> tblCostumers;

	@FXML
	private TableColumn<Costumer, String> userName;

	@FXML
	private TableColumn<Costumer, String> creditCard;

	@FXML
	private TableColumn<Costumer, String> subscriberID;

	@FXML
	private TableColumn<Costumer, CostumerStatus> status;

	@FXML
	private Button btnApproveCostumer;

	@FXML
	private ImageView picture;

	@FXML
	private ImageView logo;
	@FXML
	private Label lblError;

	private TableViewController myTable = new TableViewController();
	private String area = ChatClient.userController.getUser().getRegion().toString();

	/**
	 * Initializes the scene by setting the selection mode of the table and calling
	 * the setColumns and setTableItems methods.
	 */
	@FXML
	public void initialize() {
		Image image = new Image("images/newusers.png");
		picture.setImage(image);
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);

		tblCostumers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumns();
		setTableItems();
	}

	/**
	 * This method is called when the user clicks the btnApproveCostumer button. It
	 * gets the list of selected costumers from the table and sets their status to
	 * APPROVED. It then updates the table and displays a success message to the
	 * user.
	 * 
	 * @param event the action event that triggered the method call
	 */
	@FXML
	void clickbtnApproveCostumer(ActionEvent event) {
		ObservableList<Costumer> selectedCostumers = tblCostumers.getSelectionModel().getSelectedItems();
		if (selectedCostumers.isEmpty()) {
			lblError.setText(" Error: No costumers selected");
			lblError.setVisible(true);
		} else {
			lblError.setVisible(false);
			ArrayList<Costumer> costumersToUpdate = new ArrayList<>(selectedCostumers);
			for (Costumer costumer : costumersToUpdate) {
				costumer.setStatus(CostumerStatus.APPROVED);
			}
			ClientUI.chat.accept(new Message(Request.Costumer_Update_Status_Request, costumersToUpdate));
			setTableItems();
			scene.popUpMessage("Costumer approved succesfully!\nMessage sent to relevant customers");

			for (Costumer c : costumersToUpdate) {
				ArrayList<String> nameAndMessage = new ArrayList<>();
				ArrayList<String> nameAndMessageMember = new ArrayList<>();
				if (c.getSubscriberID() == -1) {
					nameAndMessage.addAll(Arrays.asList(c.getUsername(),
							"Congragulation!\nYou are finally a customer at EKrut!\n Please Loggout and Loggin again!"));
					ClientUI.chat.accept(new Message(Request.Send_Notification, nameAndMessage));
				} else {
					nameAndMessageMember
							.addAll(Arrays.asList(c.getUsername(), "Congragulation!\nYou are finally a member at EKrut!"
									+ "You have 20% discount on your first purchase!\n Please Loggout and Loggin again!"));
					ClientUI.chat.accept(new Message(Request.Send_Notification, nameAndMessageMember));

				}
			}
		}
	}

	/**
	 * 
	 * Displays a message with instructions on how to use the application.
	 * 
	 * @param event the action event that triggered the method call
	 */
	@FXML
	void clickBtnHelp(ActionEvent event) {
		scene.popUpMessage("1. Click on the customer you want to approve\n"
				+ "2. You can choose multiple customers using CTRL button!\n" + "3. Click Approve coustomer ");
	}

	/**
	 * 
	 * Navigates the user back to the previous screen.
	 * 
	 * @param event the action event that triggered the method call
	 */
	@FXML
	void clickBackBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/AreaManager_MainView.fxml");

	}

	/**
	 * 
	 * Exits the application.
	 * 
	 * @param event the action event that triggered the method call
	 */
	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);

	}

	/**
	 * Sets the columns in the table to display the specified fields.
	 */

	@FXML
	private void setColumns() {
		myTable.setColumn(userName, "username");
		myTable.setColumn(creditCard, "creditCard");
		myTable.setColumn(subscriberID, "subscriberID");
		myTable.setColumn(status, "status");
	}

	/**
	 * Clears the items in the table and sets them to the list of not-approved
	 * costumers for the specified area.
	 */
	private void setTableItems() {
		tblCostumers.getItems().clear();
		ClientUI.chat.accept(new Message(Request.Get_Not_Approved_Costumers_By_Area, area));
		tblCostumers.setItems(ChatClient.costumerController.getAreaCostumers());
	}

}
