package clientControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    void clickBtnBack(ActionEvent event) {
    	
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/CEO_MainView.fxml"));
		try {
			loader.load();
			Parent root = loader.getRoot();
			stage.getScene().setRoot(root);
			stage.sizeToScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

    }
    
    public void initialize() {
		ArrayList<String> area = new ArrayList<String>(Arrays.asList("Tel-Aviv","Haifa","Kiryat-Ata","Karmiel","Beer-Sheva"));
		cmbArea.getItems().addAll(area);
		ArrayList<String> years= new ArrayList<String>(Arrays.asList("2015","2016","2017","2018","2019","2020","2021","2022"));
		cmbYear.getItems().addAll(years);
    	ArrayList<String> months= new ArrayList<String>(Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12"));
    	cmbMonth.getItems().addAll(months);
		ArrayList<String> reportsType= new ArrayList<String>(Arrays.asList("Inventory reports","Orders reports","Costumer Reports"));
		cmbType.getItems().addAll(reportsType);

		
		ArrayList<String> device = new ArrayList<String>(Arrays.asList("Ben-Yehuda","Carmel","Achi-Eilat","Center-Karmiel","Ben-Gurion"));
		cmbDevice.getItems().addAll(device);
		
    	errorFieldsMsg.setVisible(false);

    	
    }

    @FXML
    void clickBtnShowReports(ActionEvent event) {
    	
        ArrayList<String> fields = new ArrayList<String>(
                Arrays.asList(cmbArea.getValue(),cmbArea.getValue(),cmbMonth.getValue(),cmbType.getValue()));
        if(fields.contains(null)) {
        	errorFieldsMsg.setVisible(true);
        }
        	
    }

    @FXML
    void clickComboArea(ActionEvent event) {

    }

    @FXML
    void clickComboDevice(ActionEvent event) {

    }

    @FXML
    void clickComboMonth(ActionEvent event) {

    }

    @FXML
    void clickComboType(ActionEvent event) {

    }

    @FXML
    void clickComboYear(ActionEvent event) {

    }

    @FXML
    void getExitBtn(ActionEvent event) {
		System.exit(0);
    }
    
}








