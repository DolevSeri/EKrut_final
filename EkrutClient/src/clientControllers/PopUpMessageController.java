package clientControllers;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PopUpMessageController {
	SetSceneController newScreen = new SetSceneController();

	@FXML
	private Button btnOK;

	@FXML
	void clickOnOk(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		//newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
		
	}
}
