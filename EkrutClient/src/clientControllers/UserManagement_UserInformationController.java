package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
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
	private TextField txtCreditCard;

	@FXML
	private TextField txtUserName;
	
    @FXML
    private Label lblErrorMsg;

	private boolean isUpdate;

	SetSceneController scene = new SetSceneController();
	User userToApprove = null;


	public UserManagement_UserInformationController(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	public UserManagement_UserInformationController() {}
	
	public void initialize() {
		lblErrorMsg.setVisible(false);
		txtUserName.setEditable(true);
		if (isUpdate) {
			txtCreditCard.setVisible(false);
			lblCreditCard.setVisible(true);
		}
		else {
			txtCreditCard.setVisible(true);
			lblCreditCard.setVisible(false);
		}
	}

	@FXML
	void clickBtnImport(ActionEvent event) {
		lblErrorMsg.setVisible(false);
		String userName = txtUserName.getText();
		ArrayList<String> userInfo = new ArrayList<>();
		if(isUpdate) {
			userInfo.addAll(Arrays.asList(userName, "Costumer"));
		}
		else {
			userInfo.addAll(Arrays.asList(userName, "NotSignUp"));
		}
		if(userName.equals("")) {
			lblErrorMsg.setVisible(true);
			lblErrorMsg.setText("You must enter username!");
		}
		else {
			ClientUI.chat.accept(new Message(Request.Get_User_Data, userInfo));
			userToApprove = ChatClient.userCreateUpdate.getUser();
			if (!ChatClient.userCreateUpdate.isUserExist()) {
				lblErrorMsg.setVisible(true);
				lblErrorMsg.setText("User name not exist or already registered as customer");
			}
			else {
				lblFName.setText(userToApprove.getFirstName());
				lblLName.setText(userToApprove.getLastName());
				lblEmail.setText(userToApprove.getEmail());
				lblID.setText(userToApprove.getId());
				lblPhone.setText(userToApprove.getPhoneNumber());
			}

		
		}
		
	}

	@FXML
	void clickBtnSend(ActionEvent event) {

	}

	@FXML
	void clickBtnUpdate(ActionEvent event) {

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
