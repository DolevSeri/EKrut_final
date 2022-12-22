package clientControllers;

import common.SetScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AreaManager_UsersConfirmationFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnexit1;

    @FXML
    private AnchorPane gpUsers;

    @FXML
    private GridPane gpUsersList;

    @FXML
    void clickBackBtn(ActionEvent event) {
    	SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/AreaManager_MainView.fxml");
    }
    
    @FXML
    void getExitBtn(ActionEvent event) {
		//ClientUI.chat.accept("Disconnect");
		System.exit(0);	
    }

}