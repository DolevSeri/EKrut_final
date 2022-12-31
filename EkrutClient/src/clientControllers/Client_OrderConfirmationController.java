package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client_OrderConfirmationController {
	FXMLLoader loader = new FXMLLoader();
	SetSceneController newScreen = new SetSceneController();
	@FXML
	private Button btnBack;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnExit;

	@FXML
	private GridPane gpRecipte;

	@FXML
	private ImageView orderLogo;

	@FXML
	private TextField txtTotalPrice;

	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderScreen.fxml");
	}

	@FXML
	void clickOnCancelOrder(ActionEvent event) {

	}

	@FXML
	void clickOnConfirm(ActionEvent event) {

	}

	@FXML
	void getExitBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

}