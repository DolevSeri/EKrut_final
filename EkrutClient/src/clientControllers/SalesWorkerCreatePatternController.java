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
 * 
 * SalesWorkerCreatePatternController is a class that responsible for the
 * creation of new Sales patterns by the sales worker
 * 
 * @author Peleg
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

	/**
	 * 
	 * The initialize method is called upon loading the screen, and is used to
	 * populate the comboboxes with options for discounts, start and end days, and
	 * start and end hours, as well as to import the existing sales patterns from
	 * the server and create a new sales pattern instance.
	 */
	public void initialize() {
		ArrayList<String> discount = new ArrayList<String>();
		discount.addAll(Arrays.asList("20%", "30%", "50%"));
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

	/**
	 * Handles the event of choosing a discount type in the discount combo box. Sets
	 * the discount type in the sales pattern object based on the selected value in
	 * the discount combo box.
	 *
	 * @param event the event that triggers the method.
	 */
	@FXML
	void ClickOnChooseDiscount(ActionEvent event) {
		sp.setDiscountType(cmbDiscount.getValue().toString());
	}

	/**
	 * Handles the event of activating a new sale pattern. It checks if the client
	 * has chosen a value in all the combo boxes and compares the start and end
	 * hours. If the start hour is later than the end hour, it displays an error
	 * message. Otherwise, it sends a message to the server to update the sales
	 * pattern, shows a success message, hides the current window and opens the main
	 * sales worker view.
	 *
	 * @param event the event that triggers the method.
	 */
	@FXML
	void clickOnActivateSale(ActionEvent event) {
		// if client didn't choose one of the comboboxes
		if (cmbDiscount.getValue() == null || cmbEndDay.getValue() == null || cmbEndHour.getValue() == null
				|| cmbStartDay.getValue() == null || cmbStartHour.getValue() == null) {
			lblError.setVisible(true);
		} else {
			// if client chose illegal hours
			if (!compareTimee(cmbStartHour.getValue(), cmbEndHour.getValue())) {
				lblError.setText("You can't choose end hour earlier than star thour! try again.");
				lblError.setVisible(true);
			} else {
				lblError.setVisible(false);
				ClientUI.chat.accept(new Message(Request.Update_SalesPattern, sp));
				newScreen.popUpMessage("Sale pattren successfully created!");
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_MainView.fxml");
			}
		}

	}

	/**
	 * Handles the event of clicking the back button. Hides the current window and
	 * opens the main sales worker view.
	 *
	 * @param event the event that triggers the method.
	 */
	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_MainView.fxml");

	}

	/**
	 * 
	 * Handles the event of choosing an end day in the end day combo box.
	 * 
	 * Sets the end day in the sales pattern object based on the selected value in
	 * the end day combo box.
	 * 
	 * @param event the event that triggers the method.
	 */
	@FXML
	void clickOnEndDay(ActionEvent event) {
		sp.setEndDay(cmbEndDay.getValue().toString());
	}

	/**
	 * 
	 * Handles the event of choosing an end hour in the end hour combo box. Sets the
	 * end hour in the sales pattern object based on the selected value in the end
	 * hour combo box.
	 * 
	 * @param event the event that triggers the method.
	 */
	@FXML
	void clickOnEndHour(ActionEvent event) {
		sp.setEndHour(cmbEndHour.getValue().toString());
	}

	/**
	 * 
	 * Handles the event of choosing a start day in the start day combo box. Sets
	 * the start day in the sales pattern object based on the selected value in the
	 * start day combo box.
	 * 
	 * @param event the event that triggers the method.
	 */
	@FXML
	void clickOnStartDay(ActionEvent event) {
		sp.setStartDay(cmbStartDay.getValue().toString());
	}

	/**
	 * 
	 * Handles the event of choosing a start hour in the start hour combo box. Sets
	 * the start hour in the sales pattern object based on the selected value in the
	 * start hour combo box.
	 * 
	 * @param event the event that triggers the method.
	 */
	@FXML
	void clickOnStartHour(ActionEvent event) {
		sp.setStartHour(cmbStartHour.getValue().toString());
	}

	/**
	 * Handles the event of clicking the exit button. Calls the 'exitOrLogOut'
	 * method in the 'newScreen' object to handle the exit/log out process.
	 *
	 * @param event the event that triggers the method.
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

	/**
	 * Compares two given times in the format of "HH:mm" and returns whether the
	 * first time is before the second.
	 *
	 * @param starthour the start time in the format of "HH:mm"
	 * @param endhour   the end time in the format of "HH:mm"
	 * @return true if the start time is before the end time, false otherwise.
	 */
	public static boolean compareTimee(String starthour, String endhour) {
		LocalTime start = LocalTime.parse(starthour);
		LocalTime end = LocalTime.parse(endhour);
		if (end.isBefore(start))
			return false;
		return true;

	}

}
