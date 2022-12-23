
package clientControllers;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class InventoryReportController {

	@FXML
	private BarChart<?, ?> chrtInentory;

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

	SetSceneController scene = new SetSceneController();

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
