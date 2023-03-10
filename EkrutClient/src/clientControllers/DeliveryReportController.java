package clientControllers;

import client.ChatClient;
import entities.DeliveryReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Inbar Mizrahi DeliveryReportController class is responsible for
 *         displaying the delivery report to the client. It initializes the
 *         scene by setting the images, labels, and populating the pie charts
 *         with data. The class has several fields including PieChart, Button,
 *         Label, and ImageView that are used to display the report's data.
 */
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

	@FXML
	private ImageView picture;

	@FXML
	private ImageView logo;

	SetSceneController scene = new SetSceneController();

	/**
	 * The initialize method is called when the scene is first loaded. It sets the
	 * images and labels, populates the pie charts with data and format the decimal
	 * value
	 */
	public void initialize() {
		Image image = new Image("/images/PickUpImage.jpeg");
		picture.setImage(image);
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);
		DeliveryReport deliveryReport = ChatClient.deliveryReportController.getDeliveryReport();
		String year = deliveryReport.getYear(), month = deliveryReport.getMonth();
		int numOfDeliveries = deliveryReport.getNumOfDeliveries();
		double avg;
		float totalSumIncomes = deliveryReport.getTotalSumIncomes();
		avg = (double)numOfDeliveries / (double)30;

		lblAvg.setText(String.format("%.2f", avg));
		lblDate.setText(month + "/" + year);

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		ObservableList<PieChart.Data> incomesPieChartData = FXCollections.observableArrayList();

		pieChartData.add(new PieChart.Data("Deliveries-" + numOfDeliveries, numOfDeliveries));
		incomesPieChartData.add(new PieChart.Data("Deliveries-" + totalSumIncomes + "$", totalSumIncomes));

		chrtOrderReport.setData(pieChartData);
		chrtIncomes.setData(incomesPieChartData);

	}

	/**
	 *
	 * This method is called when the user clicks on the "Back" button. It hides the
	 * current window and opens the ChooseReport scene.
	 *
	 * @param event - ActionEvent that is triggered by the button click
	 */
	@FXML
	void clickBackBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.back(event, "/clientGUI/ChooseReport.fxml");

	}

	/**
	 * Exit the application
	 * 
	 * @param event the event that triggered the method call
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

}
