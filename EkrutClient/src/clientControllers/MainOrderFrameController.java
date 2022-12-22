package clientControllers;

import java.io.IOException;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainOrderFrameController {
	 FXMLLoader loader = new FXMLLoader();
	 
	@FXML
	Label lblWelcome = null;

	@FXML
	Button btnLocalOrder;

	@FXML
	Button btnDistantOrder;

	@FXML
	Button btnLogout;

	@FXML
	Button btnBack;

	@FXML
	Button btnExit;

    @FXML
    private ImageView imgLogo;
    
    private SetSceneController newScreen = new SetSceneController();
    @FXML
    void getLogoutButton(ActionEvent event) {
    	newScreen.exitOrLogOut(event, true);
    }
	public void initialize() {
		lblWelcome.setText("Welcome Back "+ ChatClient.userController.getUser().getFirstName() +" "+ ChatClient.userController.getUser().getLastName()+"!");
	}
	@FXML
	public void clickOnLocaclOrder(ActionEvent event) throws IOException {
		System.out.println("Costumer want loacl order");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
	}
	@FXML
	public void clickOnDistantOrder(ActionEvent event) throws IOException {
		System.out.println("Costumer want distant order");
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
	}
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("exit ConnectForm");
		System.exit(0);
	}
	
}
