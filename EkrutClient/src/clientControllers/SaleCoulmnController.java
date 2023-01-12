package clientControllers;

import java.io.IOException;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entities.Sale;
import enums.Request;
import enums.SaleStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 *
 * 
 * @author Ron, Peleg
 * 
 *         The class represents a column in the sales worker's interface for
 *         activating sales.
 * 
 *         It contains the functionality for activating a sale and updating the
 *         sales worker's interface accordingly.
 */
public class SaleCoulmnController {

	@FXML
	private Button activateSale;

	@FXML
	private ImageView imgProduct;

	@FXML
	private Label lblPrice;

	@FXML
	private Label lblQuantity;

	@FXML
	private TextArea saleText;
	private Sale sale;
	SalesWorkerActivateSaleController salesworkeractivate;
	SetSceneController newScreen = new SetSceneController();
	/**

	This method is called when the activate button is clicked. It updates the sale's status in the system and on the sales worker's interface.

	@param event - the button click event

	@throws IOException
	*/
	@FXML
	void clickOnActivate(ActionEvent event) throws IOException {
		for (Sale sale : ChatClient.salesController.getSales()) {
			if (sale.getSaleID() == this.sale.getSaleID()) {
				sale.setStatus(SaleStatus.ACTIVATE);
				this.sale.setStatus(SaleStatus.ACTIVATE);
				ClientUI.chat.accept(new Message(Request.Update_sale_status, sale));

			}
		}

		newScreen.popUpMessage("Sale has been activate(:");
		ClientUI.chat.accept(new Message(Request.import_Sales, null));
		salesworkeractivate.clearSales();
		salesworkeractivate.saleControllers.clear();
		salesworkeractivate.setSale();
	}

	/**
	 * 
	 * This method sets the sale and sales worker controller for the column.
	 * 
	 * @param sale                - the sale to be displayed in the column
	 * @param salesworkeractivate - the sales worker controller
	 */
	public void setData(Sale sale, SalesWorkerActivateSaleController salesworkeractivate) {
		this.sale = sale;
		saleText.setText(sale.toString());
		this.salesworkeractivate = salesworkeractivate;

	}

}
