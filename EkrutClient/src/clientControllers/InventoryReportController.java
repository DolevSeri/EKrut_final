
package clientControllers;

import java.util.HashMap;
import java.util.Map.Entry;

import client.ChatClient;
import entities.InventoryReport;
import enums.Role;
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
 * Controller class for the Inventory Report scene.
 * 
 * @author Eden Bar
 */
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

	@FXML
	private ImageView logo;

	SetSceneController scene = new SetSceneController();
	public InventoryReportController inventoryReportController;
	
	public void setInventoryReportController(InventoryReportController inventoryReportController) {
		this.inventoryReportController = inventoryReportController;
	}
	
	/**
	 * Initializes the fields in the Inventory Report scene with data from the
	 * InventoryReport object.
	 */
	public void initialize() {
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);
		InventoryReport inventoryReport = ChatClient.inventoryReportController.getInventoryReport();
		HashMap<String, Integer> producsUnderThreshold = inventoryReport.getProducsUnderThreshold();
		String mexProductUnderThres = inventoryReport.getMexProductUnderThres();
		Integer deviceThres = inventoryReport.getDeviceThres();
		String device = inventoryReport.getDeviceName();
		String month = inventoryReport.getMonth();
		String year = inventoryReport.getYear();
		String areaCeo = ChatClient.inventoryReportController.getAreaForCEO();
		String areaManager = ChatClient.userController.getUser().getRegion().toString();

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

		for (Entry<String, Integer> pr : producsUnderThreshold.entrySet()) {
			String product = pr.getKey();
			Integer underThresCount = pr.getValue();
			pieChartData.add(new PieChart.Data(product + "-" + underThresCount, underThresCount));
		}
		if (ChatClient.userController.getUser().getRole().equals(Role.CEO)) {
			setLabelText(lblAreaField, areaCeo);
		}
		else {
			setLabelText(lblAreaField, areaManager);
		}
		setLabelText(lblDeviceField, device);
		setLabelText(lblTresholdField, deviceThres.toString());
		setLabelText(lblReportDate, month + "/" + year);
		setLabelText(lblItemUnderTreshold, mexProductUnderThres);
		setChartData(chrtInventory, pieChartData);
	}

	/**
	 * 
	 * Navigates the user back to the previous screen.
	 * 
	 * @param event the action event that triggered the method call
	 */
	@FXML
	void clickBackBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.back(event, "/clientGUI/ChooseReport.fxml");

	}

	/**
	 * Handles the exit button click event.
	 *
	 * @param event the action event that triggered the handler
	 */
	@FXML
	void clickExitBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		scene.exitOrLogOut(event, false);
	}
	
	public InventoryReportController() {
		
	}
	
	public void setLabelText(Label l, String str) {
		l.setText(str);
	}
	public void setChartData(PieChart c, ObservableList<PieChart.Data> data) {
		c.setData(data);
	}

}
