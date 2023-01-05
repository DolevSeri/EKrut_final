package clientControllers;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Client_OrderCanceledController {
	SetSceneController newScreen = new SetSceneController();
	@FXML
	private Button btnOK;

	@FXML
	void clickOnOK(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		if (ChatClient.userController.getUser().getConfiguration().toString().equals("EK")) {
			newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
		} else {
			newScreen.setScreen(new Stage(), "/clientGUI/Client_OL_MainView.fxml");
		}
	}

}
