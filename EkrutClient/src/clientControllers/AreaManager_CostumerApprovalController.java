package clientControllers;

import java.util.ArrayList;

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
    private Label lblError;
    
	private TableViewController myTable = new TableViewController();
	private String area = ChatClient.userController.getUser().getRegion().toString();

	@FXML
	public void initialize() {
		tblCostumers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumns();
		setTableItems();
	}

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
			ClientUI.chat.accept(new Message(Request.Costumer_Update_Status_Request,costumersToUpdate));
			setTableItems();
			
		}
	}

	@FXML
	void clickBackBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/AreaManager_MainView.fxml");

	}

	@FXML
	void clickExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);

	}

	@FXML
	private void setColumns() {
		myTable.setColumn(userName, "username");
		myTable.setColumn(creditCard, "creditCard");
		myTable.setColumn(subscriberID, "subscriberID");
		myTable.setColumn(status, "status");
	}

	private void setTableItems() {
		tblCostumers.getItems().clear();
		ClientUI.chat.accept(new Message(Request.Get_Not_Approved_Costumers_By_Area, area));
		tblCostumers.setItems(ChatClient.costumerController.getAreaCostumers());
	}

}
