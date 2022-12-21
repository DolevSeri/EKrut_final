package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.SetScene;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class IdentificationController {
	FXMLLoader loader = new FXMLLoader();
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
	
	private SetScene newScreen = new SetScene();

	public void initialize() {
		lblErrorOnDetails.setVisible(false);
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
		ClientUI.chat.accept(new Message(Request.Login_Request, usernameAndPsw));
		// if user is already loggedin
		if(!ChatClient.userController.isUserExist()) {
			//In case the user login input was invalid (username/password) - error label will be shown
			lblErrorOnDetails.setVisible(true);
			lblErrorOnDetails.setText("Wrong username OR password! Try again!");
		}
		else {
			if(ChatClient.userController.getUser().isLoggedIn() == true) {
				lblErrorOnDetails.setVisible(true);
				lblErrorOnDetails.setText("User is already logged in!");
			}
			else {
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				Stage primaryStage = new Stage();
				AnchorPane root = loader.load(getClass().getResource("/clientGUI/"+ChatClient.userController.getUser().getRole().toString()));
				Scene scene = new Scene(root);	
				primaryStage.setTitle("Costumer Menu");
				primaryStage.setScene(scene);		
				primaryStage.show();
			}
		}
	}
}