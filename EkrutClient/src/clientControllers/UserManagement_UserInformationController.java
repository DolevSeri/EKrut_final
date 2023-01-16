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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * The UserManagement_UserInformationController class is responsible for
 * handling the user information view. This class contains the functionality of
 * the buttons and fields in the user information view, and is responsible for
 * handling the import, update, and send actions of the user. The class is used
 * to create new customer or update an existing one.
 * 
 * @author Eden Bar and Dolev Seri
 */
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
	
	@FXML
	private ImageView picture;
	
	@FXML
	private ImageView logo;

	UserManagement_MainViewController controller = new UserManagement_MainViewController();
	SetSceneController scene = new SetSceneController();
	User userToApprove = null;
	Costumer customerToUpdate = null;

	/**
	 * This method is called when the class is initialized. It initializes the view
	 * by setting the visibility of certain elements based on whether the user is
	 * updating or creating a new account.
	 */
	public void initialize() {
		Image image = new Image("/images/usersInfoView.jpg");
		picture.setImage(image);
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);

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

	/**
	 * This method is triggered when the "Import" button is clicked. It retrieves
	 * the user data by sending a message to the server with the user's name. If the
	 * user is being updated, a new instance of the Costumer object is created, and
	 * the customer's data is retrieved. The method then checks if the user's name
	 * is valid, and if the user is already a member. If so, an error message is
	 * displayed. If the user's data is valid, the fields are filled with the user's
	 * data.
	 *
	 * @param event the event that triggers this method when the button is clicked
	 */
	@FXML
	void clickBtnImport(ActionEvent event) {

		lblErrorMsg.setVisible(false);
		ArrayList<String> userInfo = new ArrayList<>();
		userInfo.clear();
		String userName = txtUserName.getText();

		// if username to import did not entered
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
			} else {
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
			else if (controller.isUpdate() && !(customerToUpdate.getSubscriberID() == -1)) {
				scene.popUpMessage("Customer is already a member!");

			}

			// check if its update user case and if customer not approved yet
			// if yes - show message
			else if (controller.isUpdate() && customerToUpdate.getStatus().toString().equals("NOTAPPROVED")) {
				scene.popUpMessage(
						"Costumer is not approved yet!\nPlease wait for area manager approval to set this user as a member");
			}

			else {
				lblFName.setText(userToApprove.getFirstName());
				lblLName.setText(userToApprove.getLastName());
				lblEmail.setText(userToApprove.getEmail());
				lblID.setText(userToApprove.getId());
				lblPhone.setText(userToApprove.getPhoneNumber());
				txtCreditCard.setEditable(true);
				if (controller.isUpdate()) {
					lblCreditCard.setText(customerToUpdate.getCreditCard());

				}
			}
		}
	}

	/**
	 * 
	 * This method is responsible for sending the customer's details to the server
	 * for creating a new customer. It is triggered when the user clicks the "Send"
	 * button. The method checks if the credit card number is valid and if the
	 * "Membership" checkbox is selected. It then sends a message to the server with
	 * the user's details and shows a confirmation message to the user.
	 * 
	 * @param event - The event that triggered the method.
	 */
	@FXML
	void clickBtnSend(ActionEvent event) {
		boolean flag = true;
		String credit = txtCreditCard.getText();
		if (credit.length() != 19 || !credit.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
			scene.popUpMessage("You must enter valid Credit Card number!");
			flag = false;
		} else {
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
		if (flag)
			clearField();
	}

	/**
	 * 
	 * This method handles the event when the user clicks the "Update" button. If
	 * the checkbox for membership is not selected, a message is displayed to the
	 * user indicating that there is no update to save. Otherwise, the method sends
	 * a request to the server to update the customer and displays a message to the
	 * user indicating that the customer was updated successfully and will receive a
	 * 20% discount for their first purchase. Finally, if the update was successful,
	 * the method clears the fields.
	 * 
	 * @author Eden Bar and Dolev Seri
	 * @param event - the event that triggered the method
	 */
	@FXML
	void clickBtnUpdate(ActionEvent event) {
		String username = txtUserName.getText();
		boolean flag = true;
		ArrayList<String>nameAndMessageMember = new ArrayList<>();
		if (!chbMembership.isSelected()) {
			scene.popUpMessage("There is no update to save");
			flag = false;
		} else {
			ClientUI.chat.accept(new Message(Request.Update_Customer_Request, username));
			scene.popUpMessage(
					"Customer updated successfully!\nThe customer recived 20% discount for his first purchase");
			nameAndMessageMember.addAll(Arrays.asList(username, "Congragulation!\nYou are finally a member at EKrut!\n"
					+ "You have 20% discount on your first purchase"));
			ClientUI.chat.accept(new Message(Request.Send_Notification, nameAndMessageMember));

		}
		if (flag)
			clearField();
	}

	/**
	 * 
	 * This method clears all the fields in the view.
	 */
	void clearField() {
		lblID.setText("");
		lblEmail.setText("");
		lblFName.setText("");
		lblLName.setText("");
		lblPhone.setText("");
		chbMembership.setSelected(false);
		txtUserName.clear();
		if (controller.isUpdate())
			lblCreditCard.setText("");
		else
			txtCreditCard.clear();
	}

	/**
	 * 
	 * This method handles the event when the back button is clicked. It navigates
	 * back to the UserManagement_MainView.fxml view.
	 * 
	 * @param event The event that triggered the method.
	 */
	@FXML
	void clickBtnBack(ActionEvent event) {
		scene.back(event, "/clientGUI/UserManagement_MainView.fxml");
	}

	/**
	 * 
	 * This method handles the event when the exit button is clicked. It exits the
	 * program or log out the user based on the given boolean value.
	 * 
	 * @param event The event that triggered the method.
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

}
