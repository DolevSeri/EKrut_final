package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Client_OL_MainVieController {
	FXMLLoader loader = new FXMLLoader();
	SetSceneController newScreen = new SetSceneController();
	@FXML
	Label lblWelcome = null;

	@FXML
	Button btnCreatePickUp;

	@FXML
	Button btnCreateDelivery;

	@FXML
	Button btnConfirmDelivery;

	@FXML
	Button btnLogOut;

	@FXML
	Button btnExit;

	@FXML
	private ImageView imageLogo;

	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

	@FXML
	void clickOnLogout(ActionEvent event) {
		newScreen.exitOrLogOut(event, true);
	}

}
