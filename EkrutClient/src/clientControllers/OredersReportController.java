package clientControllers;

import java.util.ArrayList;

import client.ClientUI;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OredersReportController {

    @FXML
    private PieChart chrtOrderReport;

    @FXML
    private Button btnexit1;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblTotalOrders;

    @FXML
    private Label lblBestSaller;

    @FXML
    private Label lblDeliveryCounter;

    @FXML
    private Label lblPickUpCounter;

    @FXML
    private Label lblDevice;

    @FXML
    private Label lblDate;

	SetSceneController scene = new SetSceneController();
	ArrayList<String> fields =  ChooseReportController.fields;
	
	
    public void initialize() {

    }

	
    @FXML
    void clickBackBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.back(event, "/clientGUI/ChooseReport.fxml");
    }

    @FXML
    void getExitBtn(ActionEvent event) {
    	ClientUI.chat.accept("Disconnect");
		System.exit(0);

    }

}
