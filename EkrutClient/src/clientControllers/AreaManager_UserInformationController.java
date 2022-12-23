package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AreaManager_UserInformationController {

    @FXML
    private Button btnApprove;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnexit;

    @FXML
    private ComboBox<String> cmbIsMember;

    @FXML
    private TextField txtCreditCard;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLName;

    @FXML
    private TextField txtPhoneNumbber;
    
	SetSceneController scene = new SetSceneController();
	User userToApprove;
	
	 public void initialize() {
		 ArrayList<String> membership = new ArrayList<String>(Arrays.asList("No", "Yes"));
		 cmbIsMember.getItems().addAll(membership);
	 }

    @FXML
    void clickBtnApprove(ActionEvent event) {

    }

    @FXML
    void clickBtnBack(ActionEvent event) {
    	scene.back(event, "/clientGUI/AreaManager_UserConfirmationForm.fxml");
    }

    @FXML
    void getExitBtn(ActionEvent event) {
    	scene.exitOrLogOut(event, false);

    }

}
