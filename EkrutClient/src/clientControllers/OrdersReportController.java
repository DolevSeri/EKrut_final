package clientControllers;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map.Entry;

import client.ChatClient;
import client.ClientUI;
import entities.OrderReport;
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
    private PieChart chrtIncomes;

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
    private Label lblAvg;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblArea;

	SetSceneController scene = new SetSceneController();
	
	
	 /**
	  * @author Inbar Mizrahi
	  */
    public void initialize() {
    	OrderReport orderReport = ChatClient.orderReportController.getOrderReport();
    	HashMap<String, Integer> deviceAndAmountHashMap = orderReport.getDeviceAndAmountHashMap();
    	HashMap<String,Float> deviceAndIncomesHashMap = orderReport.getDeviceAndIncomeHashMap();
    	Integer totalOrdersCount = orderReport.getTotalOrdersCount();
    	Integer numOfPickUpOrders = orderReport.getNumOfPickUpOrders();
  
    	final DecimalFormat df = new DecimalFormat("0.00");
    	String ordersPerDayAvg = df.format(orderReport.getOrdersPerDayAvg());

    	
    	String area = orderReport.getArea();
    	String month = orderReport.getMonth();
    	String year = orderReport.getYear();
    	String mostSellingDevice =  orderReport.getMostSellingDevice();
    	
    	
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> incomesPieChartData = FXCollections.observableArrayList();
        
        for(Entry<String, Integer> device: deviceAndAmountHashMap.entrySet()) {
        	String deviceName = device.getKey();
        	Integer ordersCount = device.getValue();
        	pieChartData.add(new PieChart.Data(deviceName+"-"+ordersCount, ordersCount));
        	
        }
        for(Entry<String, Float> deviceIncome: deviceAndIncomesHashMap.entrySet()) {
        	String deviceName = deviceIncome.getKey();
        	Float incomesSum = deviceIncome.getValue();
        	incomesPieChartData.add(new PieChart.Data(deviceName+"-"+incomesSum+"$", incomesSum));
        	
        }
        System.out.println("pick up- "+numOfPickUpOrders +" most selling "+mostSellingDevice+" total- "+totalOrdersCount);
        
        lblArea.setText(area);
        lblAvg.setText(ordersPerDayAvg);
        lblDate.setText(month+"/"+year);
        chrtOrderReport.setData(pieChartData);
        chrtIncomes.setData(incomesPieChartData);
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
