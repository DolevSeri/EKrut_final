package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entities.Sale;
import entities.SalesPattern;
import enums.Region;
import enums.Request;
import enums.SaleStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author peleg a class that will do the functionality of create sale screen of
 *         Sales Manager
 */
public class SalesManagerCreateSaleController {

	@FXML
	private Button btnBack;

	@FXML
	private Button btnSendSale;

	@FXML
	private Button btnexit1;

	@FXML
	private ComboBox<String> cmbArea;

	@FXML
	private ComboBox<String> cmbSale;

	@FXML
	private ImageView icon;

	@FXML
	private Label lblDiscountType;

	@FXML
	private Label lblDuration;

	@FXML
	private Label lblEndDay;

	@FXML
	private Label lblPatternID;

	@FXML
	private Label lblStartDay;

	@FXML
	private Label lblStartHour;

	@FXML
	private ImageView saleImage;

	@FXML
	private Label txtDiscuntType;

	@FXML
	private Label txtEndHour;

	@FXML
	private Label txtEndDay;

	@FXML
	private Label txtStartDay;

	@FXML
	private Label txtStartHour;

	@FXML
	private Label txtpatternID;
	@FXML
	private Label lblError;

	SetSceneController newScreen = new SetSceneController();
	Sale sale;

	public void initialize() {
		ArrayList<String> patternsID = new ArrayList<>();
		ArrayList<String> area = new ArrayList<String>();
		area.addAll(Arrays.asList("NORTH", "SOUTH", "UAE"));
		cmbArea.getItems().addAll(area);
		ClientUI.chat.accept(new Message(Request.import_SalesPattern, null));
		for (SalesPattern sale : ChatClient.salesPatternController.getSalespattern()) {
			patternsID.add(String.valueOf(sale.getPatternID()));
		}
		cmbSale.getItems().addAll(patternsID);
		setVisibleLable(false);
		lblError.setVisible(false);
		ClientUI.chat.accept(new Message(Request.import_Sales, null));

	}

	@FXML
	void ClickOnSendToAcrivate(ActionEvent event) {
		if (cmbArea.getValue() == null || cmbSale.getValue() == null) {
			lblError.setVisible(true);

		} else {
			
			lblError.setVisible(false);
			sale = new Sale(Integer.valueOf(txtpatternID.getText()), txtDiscuntType.getText(), txtStartDay.getText(),
					txtEndDay.getText(), txtStartHour.getText(), txtEndHour.getText(),
					Region.valueOf(cmbArea.getValue()), ChatClient.salesController.getSales().size() + 1,
					SaleStatus.valueOf("NEEDTOACTIVATE"));
			ClientUI.chat.accept(new Message(Request.Update_Sales, sale));
			newScreen.popUpMessage("Sale is activated successfully!");
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			newScreen.setScreen(new Stage(), "/clientGUI/SalesManagerMainScreen.fxml");
		}

	}

	@FXML
	void chooseAreaComboBox(ActionEvent event) {

	}

	@FXML
	void chooseSalePatternComboBox(ActionEvent event) {
		String id = cmbSale.getValue().toString();
		setVisibleLable(true);
		for (SalesPattern sale : ChatClient.salesPatternController.getSalespattern()) {
			if (String.valueOf(sale.getPatternID()).equals(id)) {
				txtDiscuntType.setText(sale.getDiscountType());
				txtEndHour.setText(sale.getEndHour());
				txtEndDay.setText(sale.getEndDay());
				txtStartDay.setText(sale.getStartDay());
				txtStartHour.setText(sale.getStartHour());
				txtpatternID.setText(id);
			}
		}

	}

	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesManagerMainScreen.fxml");
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

	/**
	 * setVisibleLable-a method that will change visibility to labels
	 * 
	 * @param bol-will be true if we want that the labels be visible and false for
	 *                 not visible
	 */
	void setVisibleLable(boolean bol) {
		lblDiscountType.setVisible(bol);
		lblDuration.setVisible(bol);
		lblEndDay.setVisible(bol);
		lblPatternID.setVisible(bol);
		lblStartDay.setVisible(bol);
		lblStartHour.setVisible(bol);

	}

}
