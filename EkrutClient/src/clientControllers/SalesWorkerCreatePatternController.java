package clientControllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;


import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entities.SalesPattern;
import enums.Request;
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
 * @author peleg SalesWorkerCreatePatternController- a class controller that
 *         will help us to do the functionality at create pattern screen
 */

public class SalesWorkerCreatePatternController {

	@FXML
	private Button btnBack;

	@FXML
	private Button btnSendSale;

	@FXML
	private Button btnexit1;

	@FXML
	private ComboBox<String> cmbDiscount;

	@FXML
	private ComboBox<String> cmbEndDay;

	@FXML
	private ComboBox<String> cmbEndHour;

	@FXML
	private ComboBox<String> cmbStartDay;

	@FXML
	private ComboBox<String> cmbStartHour;

	@FXML
	private ImageView icon;

	@FXML
	private ImageView saleImage;

	@FXML
	private Label lblError;

	SetSceneController newScreen = new SetSceneController();

	SalesPattern sp;

	public void initialize() {
		ArrayList<String> discount = new ArrayList<String>();
		discount.addAll(Arrays.asList( "20%", "30%", "50%"));
		cmbDiscount.getItems().addAll(discount);
		ArrayList<String> daysStart = new ArrayList<String>();
		daysStart.addAll(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));
		cmbStartDay.getItems().addAll(daysStart);
		ArrayList<String> daysend = new ArrayList<String>();
		daysend.addAll(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));
		cmbEndDay.getItems().addAll(daysend);
		ArrayList<String> hourStart = new ArrayList<String>();
		hourStart.addAll(Arrays.asList("08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00",
				"14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00"));
		cmbStartHour.getItems().addAll(hourStart);
		ArrayList<String> duration = new ArrayList<String>();
		duration.addAll(Arrays.asList("08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00",
				"14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00"));
		
		cmbEndHour.getItems().addAll(duration);
		ClientUI.chat.accept(new Message(Request.import_SalesPattern, null));
		sp = new SalesPattern(ChatClient.salesPatternController.getSalespattern().size() + 1, null, null, null, null,
				null);
		
		lblError.setVisible(false);
		Image image = new Image("/images/SalesDesignPatternSale.jpeg");
		saleImage.setImage(image);
		

	}

	@FXML
	void ClickOnChooseDiscount(ActionEvent event) {
		sp.setDiscountType(cmbDiscount.getValue().toString());
	}

	@FXML
	void clickOnActivateSale(ActionEvent event) {
		if (cmbDiscount.getValue() == null || cmbEndDay.getValue() == null || cmbEndHour.getValue() == null
				|| cmbStartDay.getValue() == null || cmbStartHour.getValue() == null) {
			lblError.setVisible(true);
		} else {
			if(!compareTimee(cmbStartHour.getValue(),cmbEndHour.getValue())) {
				lblError.setText("You need to choose hours of one day only!!!");
				lblError.setVisible(true);
			}
			else {
			lblError.setVisible(false);
			ClientUI.chat.accept(new Message(Request.Update_SalesPattern, sp));
			newScreen.popUpMessage("Sale pattren successfully created!");
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_MainView.fxml");
			}
		}

	}

	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_MainView.fxml");

	}

	@FXML
	void clickOnEndDay(ActionEvent event) {
		sp.setEndDay(cmbEndDay.getValue().toString());
	}

	@FXML
	void clickOnEndHour(ActionEvent event) {
		sp.setEndHour(cmbEndHour.getValue().toString());
	}

	@FXML
	void clickOnStartDay(ActionEvent event) {
		sp.setStartDay(cmbStartDay.getValue().toString());
	}

	@FXML
	void clickOnStartHour(ActionEvent event) {
		sp.setStartHour(cmbStartHour.getValue().toString());
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}
	public static boolean compareTimee(String starthour, String endhour) {
		LocalTime start = LocalTime.parse(starthour);
		LocalTime end = LocalTime.parse(endhour);
		if (end.isBefore(start)) 
			return false;
		return true;

	}

	
}
