package clientControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class AreaManager_ReportChooseController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnShowReport;

    @FXML
    private Button btnexit1;

    @FXML
    private ComboBox<?> cmbDevice;

    @FXML
    private ComboBox<?> cmbMonth;

    @FXML
    private ComboBox<?> cmbType;

    @FXML
    private ComboBox<?> cmbYear;
    
	SetSceneController scene = new SetSceneController();

    @FXML
    void clickBtnBack(ActionEvent event) {
    	scene.back(event, "/clientGUI/AreaManager_MainView.fxml");
    }

    @FXML
    void clickBtnShowReport(ActionEvent event) {

    }

    @FXML
    void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
    }

}