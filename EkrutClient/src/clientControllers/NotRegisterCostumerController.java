package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class NotRegisterCostumerController {
	FXMLLoader loader = new FXMLLoader();
    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnSendRequest;

    @FXML
    private ImageView ekrutLogo;

    @FXML
    private Label lblErrorOnDetails;
    SetSceneController newScreen = new SetSceneController();
    @FXML
    void clickOnBtnSendRequest(ActionEvent event) {

    }

    @FXML
    void clickOnLogout(ActionEvent event) {

		newScreen.exitOrLogOut(event, true);
    }

    @FXML
    void getExitBtn(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.exitOrLogOut(event, true);
		System.out.println("exit ConnectForm");
		System.exit(0);
    }

}
