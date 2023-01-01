package clientControllers;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class AreaManager_CostumerApprovalController {
	private SetSceneController scene = new SetSceneController();	

    @FXML
    private Button btnexit1;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<?, ?> userName;

    @FXML
    private TableColumn<?, ?> creditCard;

    @FXML
    private TableColumn<?, ?> subscriberID;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private Button btnApproveCostumer;

    @FXML
    void clickbtnApproveCostumer(ActionEvent event) {

    }

    @FXML
    void getExitBtn(ActionEvent event) {

    }



    void clickBackBtn(ActionEvent event) {
    	scene.back(event, "/clientGUI/AreaManager_MainView.fxml");
    }
}  
