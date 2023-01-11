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
     
    @FXML
    void clickOnActivate(ActionEvent event) throws IOException {
    	for(Sale sale:ChatClient.salesController.getSales()) {
    		if(sale.getSaleID()==this.sale.getSaleID()) {
    			sale.setStatus(SaleStatus.ACTIVATE);
    			this.sale.setStatus(SaleStatus.ACTIVATE);
    			ClientUI.chat.accept(new Message(Request.Update_sale_status,sale));
    			
    		}
    	}
    	
        newScreen.popUpMessage("Sale has been activate(:"); 
        ClientUI.chat.accept(new Message(Request.import_Sales, null));
        salesworkeractivate.clearSales();
        salesworkeractivate.saleControllers.clear();
        salesworkeractivate.setSale();
    }
    public void setData(Sale sale,SalesWorkerActivateSaleController salesworkeractivate) {
		this.sale = sale;
		saleText.setText(sale.toString());
		this.salesworkeractivate=salesworkeractivate;
		
	}

}
