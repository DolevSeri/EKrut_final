package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import entities.Sale;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SalesWorkerActivateSaleController {
	SetSceneController newScreen = new SetSceneController();
	@FXML
	private Button btnBack;

	@FXML
	private Button btnexit1;

	@FXML
	private GridPane gpSalesList;

	@FXML
	private AnchorPane gpUsers;

	@FXML
	private ImageView saleImage;
	
   public ArrayList<SaleCoulmnController> saleControllers=new ArrayList<>();
	public void initialize() throws IOException {
			ClientUI.chat.accept(new Message(Request.import_Sales, null));
			setSale();
			Image image = new Image("/images/SalesMenagerScreenImage.png");
			saleImage.setImage(image);
			
		
	}

	@FXML
	void clickOnBtnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_MainView.fxml");
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}
	public void setSale() throws IOException {
		int row = 3;
		int i = 0;
		System.out.println(ChatClient.salesController.getSales().toString());
		for(Sale sale:ChatClient.salesController.getSales()) {
			if(sale.getStatus().toString().equals("NEEDTOACTIVATE")&&sale.getRegion().toString().equals(ChatClient.userController.getUser().getRegion().toString())) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/clientGUI/SaleCoulmn.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();
			SaleCoulmnController saleController = fxmlLoader.getController();
			saleControllers.add(saleController);
			saleControllers.get(i++).setData(sale,this);
			gpSalesList.add(anchorPane, 0, row++);// (child column,row)
				
			}
		}
	}
	public void clearSales() {
		gpSalesList.getChildren().clear();
	}

}
