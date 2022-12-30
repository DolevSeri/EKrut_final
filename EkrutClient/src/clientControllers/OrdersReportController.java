package clientControllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entities.OrderReport;
import enums.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OrdersReportController {

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
	 
	
    public void initialize() {
    	OrderReport orderReport = ChatClient.orderReportController.getOrderReport();
    	HashMap<String, Integer> deviceAndAmountHashMap = orderReport.getDeviceAndAmountHashMap();
    	Integer totalOrdersCount = orderReport.getTotalOrdersCount();
    	Integer numOfPickUpOrders = orderReport.getNumOfPickUpOrders();
    	float ordersPerDayAvg = orderReport.getOrdersPerDayAvg();
    	
    	String area = orderReport.getArea();
    	String month = orderReport.getMonth();
    	String year = orderReport.getYear();
    	String mostSellingDevice =  orderReport.getMostSellingDevice();
    	
    	
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        
        for(Entry<String, Integer> device: deviceAndAmountHashMap.entrySet()) {
        	String deviceName = device.getKey();
        	Integer ordersCount = device.getValue();
        	pieChartData.add(new PieChart.Data(deviceName, ordersCount));
        	
        }
        lblDevice.setText(area);
        lblDate.setText(month+"/"+year);
        chrtOrderReport.setData(pieChartData);
        lblBestSaller.setText(mostSellingDevice);
        lblPickUpCounter.setText(numOfPickUpOrders.toString());
        lblTotalOrders.setText(totalOrdersCount.toString());
    	

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
