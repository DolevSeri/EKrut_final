
package clientControllers;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map.Entry;

import client.ChatClient;
import client.ClientUI;
import entities.InventoryReport;
import entities.OrderReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InventoryReportController {

	@FXML
	private PieChart chrtInventory;

	@FXML
	private Button btnexit1;

	@FXML
	private Button btnBack;

	@FXML
	private Label lblDeviceField;

	@FXML
	private Label lblAreaField;

	@FXML
	private Label lblTresholdField;

	@FXML
	private Label lblReportDate;

	@FXML
	private Label lblItemUnderTreshold;

	SetSceneController scene = new SetSceneController();

	/**
	 * @author Eden Bar
	 */
	public void initialize() {
		InventoryReport inventoryReport = ChatClient.inventoryReportController.getInventoryReport();
		HashMap<String, Integer> producsUnderThreshold = inventoryReport.getProducsUnderThreshold();
		String mexProductUnderThres = inventoryReport.getMexProductUnderThres();
		Integer deviceThres = inventoryReport.getDeviceThres();
		String device = inventoryReport.getDeviceName();
		String month = inventoryReport.getMonth();
		String year = inventoryReport.getYear();

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

		for (Entry<String, Integer> pr : producsUnderThreshold.entrySet()) {
			String product = pr.getKey();
			Integer underThresCount = pr.getValue();
			pieChartData.add(new PieChart.Data(product, underThresCount));
		}
		
		lblDeviceField.setText(device);
		lblTresholdField.setText(deviceThres.toString());
		lblReportDate.setText(month + "/" + year);
		lblItemUnderTreshold.setText(mexProductUnderThres);
		chrtInventory.setData(pieChartData);

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
