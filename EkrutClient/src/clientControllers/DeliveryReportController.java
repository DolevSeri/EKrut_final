package clientControllers;

import java.text.DecimalFormat;

import client.ChatClient;
import client.ClientUI;
import entities.DeliveryReport;
import enums.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DeliveryReportController {

	@FXML
	private PieChart chrtOrderReport;

	@FXML
	private PieChart chrtIncomes;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnexit1;

	@FXML
	private Label lblDate;

	@FXML
	private Label lblAvg;
	

	SetSceneController scene = new SetSceneController();
	
	 /**
	  * @author Inbar Mizrahi
	  */
   public void initialize() {
	   DeliveryReport deliveryReport = ChatClient.deliveryReportController.getDeliveryReport();
		String year = deliveryReport.getYear() ,month = deliveryReport.getMonth();
		int numOfDeliveries = deliveryReport.getNumOfDeliveries();
		float avg,totalSumIncomes= deliveryReport.getTotalSumIncomes();
		avg = numOfDeliveries / 30;
		
	  	final DecimalFormat df = new DecimalFormat("0.00");
    	String DeliveriesPerDayAvg = df.format(avg);
    	
    	lblAvg.setText(DeliveriesPerDayAvg);
    	lblDate.setText(month+"/"+year);
    	
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> incomesPieChartData = FXCollections.observableArrayList();
        
    	pieChartData.add(new PieChart.Data("Deliveries-"+numOfDeliveries, numOfDeliveries));
    	incomesPieChartData.add(new PieChart.Data("Deliveries-"+totalSumIncomes+"$", totalSumIncomes));
    	
        chrtOrderReport.setData(pieChartData);
        chrtIncomes.setData(incomesPieChartData);

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
