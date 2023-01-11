package clientControllers;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SalesManagerMainScreenController {

    @FXML
    private Button btnCreateSale;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnexit1;

    @FXML
    private Label lblArea;
    @FXML
    private Label lblWelcome;

    @FXML
    private ImageView logoIcon;

    SetSceneController newScreen = new SetSceneController();
	public void initialize() {
		lblWelcome.setText("Welcome Back "+ ChatClient.userController.getUser().getFirstName()
				+" "+ ChatClient.userController.getUser().getLastName()+"!");
		lblArea.setText(ChatClient.userController.getUser().getRegion().toString());
		Image image = new Image("/images/SaleManagerMainScreen.png");
		logoIcon.setImage(image);
	}
    @FXML
    void clickOnCreateSaleBtn(ActionEvent event) {
    	System.out.println("Sales Manager want to create sale");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesManager_CreateSale.fxml");
    }

    @FXML
    void clickOnLogOut(ActionEvent event) {
    	newScreen.exitOrLogOut(event, true);
    }

    @FXML
    void getExitBtn(ActionEvent event) {
    	newScreen.exitOrLogOut(event, false);
    }

}
