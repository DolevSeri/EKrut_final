package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import enums.Request;
import enums.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class represents the controller for the Reports View FXML file. It
 * handles the user's interactions with the view and communicates with the
 * server to retrieve the required data.
 * 
 * @author Inbar Mizrahi
 */
public class ChooseReportController {

	@FXML
	private Button btnexit;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnShowReport;

	@FXML
	private ComboBox<String> cmbYear;

	@FXML
	private ComboBox<String> cmbMonth;

	@FXML
	private ComboBox<String> cmbType;

	@FXML
	private ComboBox<String> cmbDevice;

	@FXML
	private ComboBox<String> cmbArea;

	@FXML
	private Label errorFieldsMsg;

	@FXML
	private Label lblRegion;

	@FXML
	private Label lblDevice;
	
	@FXML
	private Label lblArea;
	
	@FXML
	private ImageView picture;
	
	@FXML
	private ImageView logo;

	public static ArrayList<String> fields;

	boolean isCEO;

	SetSceneController scene = new SetSceneController();

	/**
	 * Handles the 'Back' button click event. Navigates the user back to the CEO
	 * Main View.
	 *
	 * @param event the ActionEvent object representing the button click event
	 */
	@FXML
	void clickBtnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		if (ChatClient.userController.getUser().getRole().equals(Role.CEO)) {
			scene.back(event, "/clientGUI/CEO_MainView.fxml");

		} else
			scene.back(event, "/clientGUI/AreaManager_MainView.fxml");
	}

	/**
	 * Initializes the view and sets up the necessary components and data. Populates
	 * the year, month, and report type combo boxes with predefined options. If the
	 * logged in user is a CEO, it enables the region combo box and sets a prompt
	 * text for the device combo box. If the logged in user is not a CEO, it hides
	 * the region combo box and displays the user's region instead. It also
	 * retrieves the list of devices for the logged in user's region and adds them
	 * to the device combo box.
	 */
	public void initialize() {
		Image image = new Image("/images/reportViewImage.png");
		picture.setImage(image);
		Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
		logo.setImage(imagelogo);

		ArrayList<String> years = new ArrayList<String>(
				Arrays.asList("2018", "2019", "2020", "2021", "2022","2023"));
		cmbYear.getItems().addAll(years);
		ArrayList<String> months = new ArrayList<String>(
				Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
		cmbMonth.getItems().addAll(months);


		if (ChatClient.userController.getUser().getRole().equals(Role.CEO)) {
			isCEO = true;
			cmbDevice.setDisable(true);
			ArrayList<String> area = new ArrayList<String>(Arrays.asList("NORTH", "SOUTH", "UAE"));
			cmbArea.getItems().addAll(area);
			cmbDevice.setPromptText("Choose region first!");

		} else {
			isCEO = false;
			cmbArea.setVisible(false);
			String area = ChatClient.userController.getUser().getRegion().toString();
			cmbArea.setValue(area);
			lblRegion.setText(area);
			lblRegion.setVisible(true);
			ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, area));
			cmbDevice.getItems().addAll(ChatClient.deviceController.getAreaDevicesNames());

		}
		if(isCEO) {
			ArrayList<String> reportsType = new ArrayList<String>(
					Arrays.asList("Inventory report", "Orders report", "Clients report","Delivery report"));
			cmbType.getItems().addAll(reportsType);
			
		}
		else {
			ArrayList<String> reportsType = new ArrayList<String>(
					Arrays.asList("Inventory report", "Orders report", "Clients report"));
			cmbType.getItems().addAll(reportsType);
			
		}
		errorFieldsMsg.setVisible(false);

	}

	/**
	 * Handles the selection event of the region combo box. Clears the device combo
	 * box and enables it. Retrieves the list of devices for the selected region and
	 * adds them to the device combo box.
	 *
	 * @param event the ActionEvent object representing the selection event
	 */

	@FXML
	void clickComboArea(ActionEvent event) {
		cmbDevice.getItems().clear();
		cmbDevice.setDisable(false);
		String areaChosen = null;
		try {
			areaChosen = cmbArea.getValue().toString();
			ChatClient.inventoryReportController.setAreaForCEO(areaChosen);
		}catch (NullPointerException e) {}
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, areaChosen));
		cmbDevice.getItems().addAll(ChatClient.deviceController.getAreaDevicesNames());
		cmbDevice.setPromptText("Choose Device");

	}

	/**
	 * Handles the action of the type of report combo box being clicked. It displays or hides the device 
	 * and area combo boxes based on the type of report selected.
	 * 
	 * @param event the action event of the combo box being clicked
	 *
	 */
	@FXML
	void clickTypeOfReport(ActionEvent event) {
		String type = cmbType.getValue().toString();
		switch(type) {
		
		case "Inventory report":
			cmbDevice.setValue(null);
			cmbDevice.setVisible(true);
			lblDevice.setVisible(true);

			if(isCEO) {
				cmbArea.setValue(null);
				cmbArea.setVisible(true);
				lblArea.setVisible(true);	
			}
			break;
		
		case "Orders report":			
		case "Clients report":
			cmbDevice.setValue("Choose Device");
			cmbDevice.setVisible(false);
			lblDevice.setVisible(false);
			if(isCEO) {
				cmbArea.setValue(null);
				cmbArea.setVisible(true);
				lblArea.setVisible(true);
			}
			break;
			
		case "Delivery report":
			cmbDevice.setVisible(false);
			lblDevice.setVisible(false);
			cmbArea.setValue("Choose Area");
			cmbArea.setVisible(false);
			lblArea.setVisible(false);
			cmbDevice.setValue("Choose Device");
			break;	
		}

	}

	/**
	 * Handles the 'Show Reports' button click event. Validates the selected fields
	 * in the combo boxes and displays an error message if any of them is not
	 * selected.
	 * 
	 * @param event the ActionEvent object representing the button click event
	 */
	@FXML
	void clickBtnShowReports(ActionEvent event) {
		try {
			String reportType = cmbType.getValue().toString();

			if(reportType.equals("Orders report") || reportType.equals("Clients report")) {
				cmbDevice.setValue("Choose Device");
			}
			fields = new ArrayList<String>(Arrays.asList(cmbArea.getValue(), cmbYear.getValue(), cmbMonth.getValue(),
					cmbType.getValue(), cmbDevice.getValue()));
			
			if (fields.contains(null)) {
				errorFieldsMsg.setText("Not all fields were chosen");
				errorFieldsMsg.setVisible(true);
			} else {
				switch (fields.get(3).toString()) {
				
				case "Delivery report":
					try {
						ClientUI.chat.accept(new Message(Request.GetDeliveryReportData, fields));
						
					}catch (NullPointerException e) {
						errorFieldsMsg.setText("No such report");
						errorFieldsMsg.setVisible(true);
					}
					if (ChatClient.deliveryReportController.getDeliveryReport() != null) {
						((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
						scene.setScreen(new Stage(), "/clientGUI/MonthlyDeliveryReport.fxml");
					}
					errorFieldsMsg.setText("No such report");
					errorFieldsMsg.setVisible(true);
					break;

				case "Inventory report":

					// NEED TO SEND THE VALUES TO SQL AND GENERATE REPORT
					try {
						ClientUI.chat.accept(new Message(Request.GetInventoryReportData, fields));
					} catch (NullPointerException e) {
						errorFieldsMsg.setText("No such report");
						errorFieldsMsg.setVisible(true);
					}

					if (ChatClient.inventoryReportController.getInventoryReport() != null) {
						((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
						scene.setScreen(new Stage(), "/clientGUI/MonthllyInventoryReport.fxml");
					}
					errorFieldsMsg.setText("No such report");
					errorFieldsMsg.setVisible(true);
					break;
					
				case "Orders report":

					// NEED TO SEND THE VALUES TO SQL AND GENERATE REPORT
					try {
						ClientUI.chat.accept(new Message(Request.GetOrdersReportData, fields));
					} catch (NullPointerException e) {
						errorFieldsMsg.setText("No such report");
						errorFieldsMsg.setVisible(true);
					}

					if (ChatClient.orderReportController.getOrderReport() != null) {
						((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
						scene.setScreen(new Stage(), "/clientGUI/MonthllyOrdersReport.fxml");
					}
					errorFieldsMsg.setText("No such report");
					errorFieldsMsg.setVisible(true);
					break;

				case "Clients report":
					// NEED TO SEND THE VALUES TO SQL AND GENERATE REPORT
					
					try {
						ClientUI.chat.accept(new Message(Request.GetCostumersReportData, fields));
						System.out.println(ChatClient.costumersReportController.getCostumersReport().toString());
					} catch (NullPointerException e) {
						errorFieldsMsg.setText("No such report");
						errorFieldsMsg.setVisible(true);
					}

					if (ChatClient.costumersReportController.getCostumersReport() != null) {
						((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
						scene.setScreen(new Stage(), "/clientGUI/MonthllyClientsReport.fxml");
					}
					errorFieldsMsg.setText("No such report");
					errorFieldsMsg.setVisible(true);
					break;

				default:
					break;

				}

			}
		} catch (NullPointerException e) {
			errorFieldsMsg.setText("Not all fields were chosen");
			errorFieldsMsg.setVisible(true);
		}

	}

	/**
	 * Handles the 'Exit' button click event. Disconnects from the server and closes
	 * the application.
	 *
	 * @param event the ActionEvent object representing the button click event
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		scene.exitOrLogOut(event, false);
	}

}
