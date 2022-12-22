package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class AreaManager_UsersConfirmationFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnexit1;

    @FXML
    private AnchorPane gpUsers;

    @FXML
    private GridPane gpUsersList;
    
	SetSceneController scene = new SetSceneController();

    @FXML
    void clickBackBtn(ActionEvent event) {
    	scene.back(event, "/clientGUI/AreaManager_MainView.fxml");
    }
    
    @FXML
    void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);	
    }

}