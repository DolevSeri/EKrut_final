package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
//da2

public class CEO_MainViewController {

    @FXML
    private Button btnexit1;

    @FXML
    private Button btnReportView;

    @FXML
    private Button btnLogOut;
	SetSceneController scene = new SetSceneController();


    @FXML
    void getExitBtn(ActionEvent event)  throws Exception  {
    	scene.exitOrLogOut(event, false);
    }

    @FXML
    void ClickLogOutBtn(ActionEvent event) throws Exception {
    	scene.exitOrLogOut(event, true);
    }

    @FXML
    void ViewMonthllyReport(ActionEvent event) throws Exception {
    	
    	scene.setScreen(new Stage(), "/clientGUI/AreaManager_ReportChoose.fxml");
    }
    


}
