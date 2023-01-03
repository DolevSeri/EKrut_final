package clientControllers;

import client.ChatClient;
import entities.CostumersReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
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

	/**
	 * @author Eden Bar
	 */
	public void initialize() {
		CostumersReport costumersReport = ChatClient.costumersReportController.getCostumersReport();
		Integer lowActivity = costumersReport.getLowActivity(), medActivity = costumersReport.getMediumActivity(),
				highActivity = costumersReport.getHighActivity(),
				veryHighActivity = costumersReport.getVeryHighActivity();
		String area = costumersReport.getArea();
		String month = costumersReport.getMonth();
		String year = costumersReport.getYear();

		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Activity");
		yAxis.setLabel("Count");
		XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		series1.getData().add(new XYChart.Data<>("0-5", lowActivity));
		series1.getData().add(new XYChart.Data<>("6-12", medActivity));
		series1.getData().add(new XYChart.Data<>("13-20", highActivity));
		series1.getData().add(new XYChart.Data<>("20+", veryHighActivity));
		
		chrtCustomers.getData().add(series1);

		lblReportDate.setText(month + "/" + year);
		lblAreaField.setText(area);
	}

	@FXML
	void clickBackBtn(ActionEvent event) {
		scene.back(event, "/clientGUI/ChooseReport.fxml");

	}

	@FXML
	void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

}
