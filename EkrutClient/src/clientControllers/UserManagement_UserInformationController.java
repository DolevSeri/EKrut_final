package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import entities.Costumer;
import entities.Message;
import entities.User;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserManagement_UserInformationController {

	@FXML
	private Button btnBack;

	@FXML
	private Button btnImport;

	@FXML
	private Button btnSend;

	@FXML
	private Button btnUpdate;

	@FXML
	private Button btnexit;

	@FXML
	private CheckBox chbMembership;

	@FXML
	private Label lblCreditCard;

	@FXML
	private Label lblEmail;

	@FXML
	private Label lblFName;

	@FXML
	private Label lblID;

	@FXML
	private Label lblLName;

	@FXML
	private Label lblPhone;
	
	@FXML
    private Label lblCredit;
	
    @FXML
    private Label lblMember;

	@FXML
	private TextField txtCreditCard;

	@FXML
	private TextField txtUserName;

	@FXML
	private Label lblErrorMsg;

	// private boolean isUpdate;

	UserManagement_MainViewController controller = new UserManagement_MainViewController();
	SetSceneController scene = new SetSceneController();
	User userToApprove = null;
	Costumer customerToUpdate = null;

//	public UserManagement_UserInformationController(boolean isUpdate) {
//		this.isUpdate = isUpdate;
//	}
//	public UserManagement_UserInformationController() {}

	public void initialize() {

		lblErrorMsg.setVisible(false);
		txtUserName.setEditable(true);
		if (controller.isUpdate()) {
			txtCreditCard.setVisible(false);
			lblCreditCard.setVisible(true);
			btnSend.setVisible(false);
			btnSend.setDisable(true);
			btnUpdate.setVisible(true);
			lblMember.setVisible(true);
			lblCredit.setVisible(false);
		} else {
			txtCreditCard.setVisible(true);
			lblCreditCard.setVisible(false);
			btnUpdate.setVisible(false);
			btnSend.setVisible(true);
			btnSend.setDisable(false);
			lblMember.setVisible(false);
			lblCredit.setVisible(true);
		}
	}

	@FXML
	void clickBtnImport(ActionEvent event) {
		lblErrorMsg.setVisible(false);
		ArrayList<String> userInfo = new ArrayList<>();
		userInfo.clear();
		String userName = txtUserName.getText();
		
		//if username to import did not entered
		if (userName.equals("")) {
			lblErrorMsg.setVisible(true);
			lblErrorMsg.setText("You must enter username!");
		}

		else {
			// if update customer case create new object of Costumer
			if (controller.isUpdate()) {
				userInfo.addAll(Arrays.asList(userName, "Costumer"));
				ClientUI.chat.accept(new Message(Request.Get_Customer_Data, userName));
				customerToUpdate = ChatClient.costumerController.getCustomerToUpdate();
			} 
			else {
				userInfo.addAll(Arrays.asList(userName, "NotSignUp"));
			}
			ClientUI.chat.accept(new Message(Request.Get_User_Data, userInfo));
			userToApprove = ChatClient.userController.getUserToUpdate();
			
			// check if create customer case and if username
			// and his status exist on users table
			if (!ChatClient.userController.isUserToApproveExist()) {
				lblErrorMsg.setVisible(true);
				lblErrorMsg.setText("User name not exist or already registered as customer");
			}

			// check if its update user case and if customer is already member
			// if yes - show message
			else if (controller.isUpdate() && !(customerToUpdate.getSubscriberID().equals("-1"))) {
				scene.popUpMessage("Customer is already a member!");
			}

			// check if its update user case and if customer not approved yet
			// if yes - show message
			else if (controller.isUpdate() && customerToUpdate.getStatus().toString().equals("NOTAPPROVED")) {
				scene.popUpMessage(
						"Costumer is not approved yet!\nPlease wait for area manager approval to set this user as a member");
				chbMembership.setDisable(true);
			}

			else {
				lblFName.setText(userToApprove.getFirstName());
				lblLName.setText(userToApprove.getLastName());
				lblEmail.setText(userToApprove.getEmail());
				lblID.setText(userToApprove.getId());
				lblPhone.setText(userToApprove.getPhoneNumber());
				txtCreditCard.setEditable(true);
				if(controller.isUpdate()) {
					lblCreditCard.setText(customerToUpdate.getCreditCard());
					
				}
			}
		}
	}

	@FXML
	void clickBtnSend(ActionEvent event) {
		String credit = txtCreditCard.getText();
		if(credit.length() != 19 || 
				!credit.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")){
			scene.popUpMessage("You must enter valid Credit Card number!");
		}
		else {
		ArrayList<String> details = new ArrayList<>(Arrays.asList(txtUserName.getText(), txtCreditCard.getText()));
		if (chbMembership.isSelected()) {
			details.add("1");
		} else
			details.add("-1");
		
			ClientUI.chat.accept(new Message(Request.Create_Customer_Request, details));
			if (chbMembership.isSelected())
				scene.popUpMessage(
						"Customer created successfully!\nThe customer will recive 20% discount for his first purchase after approval");
			else
				scene.popUpMessage("Customer created successfully!");
		}
	}

	@FXML
	void clickBtnUpdate(ActionEvent event) {
		String username = txtUserName.getText();
		if (!chbMembership.isSelected())
			scene.popUpMessage("There is no update to save");
		else {
			ClientUI.chat.accept(new Message(Request.Update_Customer_Request, username));
			scene.popUpMessage(
					"Customer created successfully!\nThe customer recived 20% discount for his first purchase");
		}

	}

	@FXML
	void clickBtnBack(ActionEvent event) {
		scene.back(event, "/clientGUI/UserManagement_MainView.fxml");
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

}
