package clientControllers;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SalesWorkerMainScreenController {

    @FXML
    private Button btnActivate;

    @FXML
    private Button btnCreatePattern;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnexit1;

	@FXML
	private Label lblWelcome;
	
	@FXML
	private Label lblArea;
	

    @FXML
    private ImageView logoIcon;
	SetSceneController newScreen = new SetSceneController();
	public void initialize() {
		lblWelcome.setText("Welcome Back "+ ChatClient.userController.getUser().getFirstName()
				+" "+ ChatClient.userController.getUser().getLastName()+"!");
		lblArea.setText(ChatClient.userController.getUser().getRegion().toString());
	}
    @FXML
    void ClickOnCreateSalePattern(ActionEvent event) {
    	System.out.println("Sales Worker want to create pattern");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_CreatePattern.fxml");
    }

    @FXML
    void clickOnActivateSale(ActionEvent event) {
    	System.out.println("Sales Worker want to Activate sale");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_ActivateSale.fxml");
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
