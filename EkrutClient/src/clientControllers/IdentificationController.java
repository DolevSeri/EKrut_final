package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import enums.Request;
import client.ChatClient;
import client.ClientUI;
import client.ClientUI;
import entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IdentificationController {
	@FXML
	private TextField txtUsername = null;

	@FXML
	private PasswordField txtPswd = null;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnExit;

	@FXML
	private ImageView logoImage;

	@FXML
	private ImageView QRimage;

	@FXML
	private Label lblUsername;

	@FXML
	private Label lblPswd;

	@FXML
	private Label lblErrorOnDetails;

	public void initialize() {
		lblErrorOnDetails.setVisible(false);
		logoImage.setImage(new Image("/images/FullLogo_Transparent_NoBuffer.png"));
		QRimage.setImage(new Image("/images/QR_Code.png"));
	}

	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

	@FXML
	public void getLoginBtn(ActionEvent event) throws Exception {
		ArrayList<String> usernameAndPsw = new ArrayList<>();
		usernameAndPsw.add(txtUsername.getText());
		usernameAndPsw.add(txtPswd.getText());
		System.out.println("dkdkkd");
		ClientUI.chat.accept(new Message(Request.Login_Request, usernameAndPsw));
		System.out.println("dkdkkd");
		// if user is already loggedin
		if (ChatClient.user.getIsLoggedIn() == true) {
			lblErrorOnDetails.setVisible(true);
			lblErrorOnDetails.setText(Request.LoggedIn_UnsuccsesAlreadyLoggedIn.toString());
		}
		// if user is not found
		else if (ChatClient.user.getUsername() == null) {
			lblErrorOnDetails.setVisible(true);
			lblErrorOnDetails.setText(Request.Unsuccsesful_LogIn.toString());
		}
		// if log in succseed
		else {
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window

		}
	}
}