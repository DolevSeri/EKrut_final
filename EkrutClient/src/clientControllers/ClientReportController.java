package clientControllers;

import java.util.HashMap;
import java.util.Map.Entry;

import client.ChatClient;
import client.ClientUI;
import entities.CostumersReport;
import entities.InventoryReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ClientReportController {

    @FXML
    private Button btnexit1;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblAreaField;

    @FXML
    private Label lblReportDate;
    
    @FXML
    private BarChart<String, Number> chrtCustomers;
    
	SetSceneController scene = new SetSceneController();
	
	
//	CategoryAxis xAxis = new CategoryAxis();
//	NumberAxis yAxis = new NumberAxis();
//	BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
//
//	XYChart.Series<String, Number> series1 = new XYChart.Series<>();
//	series1.getData().add(new XYChart.Data<>("Category 1", 10));
//	series1.getData().add(new XYChart.Data<>("Category 2", 20));
//	series1.getData().add(new XYChart.Data<>("Category 3", 30));
//
//	barChart.getData().add(series1);


	/**
	 * @author Eden Bar
	 */
	public void initialize() {
		CostumersReport costumersReport = ChatClient.costumersReportController.getCostumersReport();
		Integer lowActivity = costumersReport.getLowActivity(), medActivity = costumersReport.getMediumActivity(), 
				highActivity = costumersReport.getHighActivity(), veryHighActivity = costumersReport.getVeryHighActivity();
		String area = costumersReport.getArea();
		String month = costumersReport.getMonth();
		String year = costumersReport.getYear();

		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		XYChart.Series<String, Number> barChart = new XYChart.Series<>();
		barChart.getData().add(new XYChart.Data<String, Number>("0-5", lowActivity));
		barChart.getData().add(new XYChart.Data<String, Number>("6-12", medActivity));
		barChart.getData().add(new XYChart.Data<String, Number>("13-20", highActivity));
		barChart.getData().add(new XYChart.Data<String, Number>("20+", veryHighActivity));

		
		lblReportDate.setText(month + "/" + year);
		lblAreaField.setText(area);
		chrtCustomers.setData(barChart);
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
