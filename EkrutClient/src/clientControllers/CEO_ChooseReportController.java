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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
/**
 * This class represents the controller for the Reports View FXML file.
 * It handles the user's interactions with the view and communicates with the server to retrieve the required data.
 * 
 * @author Inbar Mizrahi
 */
public class CEO_ChooseReportController {

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

    
    
	SetSceneController scene = new SetSceneController();
	/**
	 * Handles the 'Back' button click event.
	 * Navigates the user back to the CEO Main View.
	 *
	 * @param event the ActionEvent object representing the button click event
	 */
    @FXML
    void clickBtnBack(ActionEvent event) {
    	
    	scene.back(event, "/clientGUI/CEO_MainView.fxml");

    }
    /**
     * Initializes the view and sets up the necessary components and data.
     * Populates the year, month, and report type combo boxes with predefined options.
     * If the logged in user is a CEO, it enables the region combo box and sets a prompt text for the device combo box.
     * If the logged in user is not a CEO, it hides the region combo box and displays the user's region instead.
     * It also retrieves the list of devices for the logged in user's region and adds them to the device combo box.
     */
    public void initialize() {
    	
    	ArrayList<String> years= new ArrayList<String>(Arrays.asList("2015","2016","2017","2018","2019","2020","2021","2022"));
		cmbYear.getItems().addAll(years);
    	ArrayList<String> months= new ArrayList<String>(Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12"));
    	cmbMonth.getItems().addAll(months);
		ArrayList<String> reportsType= new ArrayList<String>(Arrays.asList("Inventory reports","Orders reports","Costumer Reports"));
		cmbType.getItems().addAll(reportsType);
		
    	if(ChatClient.userController.getUser().getRole().equals(Role.CEO)) {
    		cmbDevice.setDisable(true);
    		ArrayList<String> area = new ArrayList<String>(Arrays.asList("Tel-Aviv","Haifa","Kiryat-Ata","Karmiel","Beer-Sheva"));
    		cmbArea.getItems().addAll(area);
    		cmbDevice.setPromptText("Choose region first!");

    		
    	}
    	else {
    		cmbArea.setVisible(false);
    		String area = ChatClient.userController.getUser().getRegion().toString();
    		lblRegion.setText(area);
    		lblRegion.setVisible(true);
    		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, area));
    		cmbDevice.getItems().addAll(ChatClient.devices);
    		
    	}
		
    	errorFieldsMsg.setVisible(false);

    }
    /**
     * Handles the selection event of the region combo box.
     * Clears the device combo box and enables it.
     * Retrieves the list of devices for the selected region and adds them to the device combo box.
     *
     * @param event the ActionEvent object representing the selection event
     */
    @FXML
    void clickComboArea(ActionEvent event) {
    	cmbDevice.getItems().clear();
    	cmbDevice.setDisable(false);
		String areaChoosen = cmbArea.getValue().toString();
		ClientUI.chat.accept(new Message(Request.Get_Devices_By_Area, areaChoosen));
		cmbDevice.getItems().addAll(ChatClient.devices);

    }
    /**
     * Handles the 'Show Reports' button click event.
     * Validates the selected fields in the combo boxes and displays an error message if any of them is not selected.
     * 
     * @param event the ActionEvent object representing the button click event
     */
    @FXML
    void clickBtnShowReports(ActionEvent event) {
    	
        ArrayList<String> fields = new ArrayList<String>(
                Arrays.asList(cmbArea.getValue(),cmbArea.getValue(),cmbMonth.getValue(),cmbType.getValue()));
        if(fields.contains(null)) {
        	errorFieldsMsg.setVisible(true);
        }
        else {
        	
        }
        	
    }
    /**
     * Handles the 'Exit' button click event.
     * Disconnects from the server and closes the application.
     *
     * @param event the ActionEvent object representing the button click event
     */
    @FXML
    void getExitBtn(ActionEvent event) {
    	//ClientUI.chat.accept("Disconnect");
		System.exit(0);
    }

    
}








