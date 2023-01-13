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

/**
 * 
 * SalesWorkerActivateSaleController class is responsible for handling the
 * activation of sales by a sales worker.
 * 
 * It displays a list of sales that need to be activated, allows the sales
 * worker to navigate back to the main view,
 * 
 * and has a button to exit the application.
 * 
 * @author Ron,Peleg
 * 
 */
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
  	@FXML
	private ImageView logo;
   public ArrayList<SaleCoulmnController> saleControllers=new ArrayList<>();


	/**
	 * 
	 * Initializes the screen by importing the sales from the server, setting the
	 * image, and displaying the list of sales that need to be activated.
	 * 
	 * @throws IOException if there is an issue loading the FXML or image files
	 */

	public void initialize() throws IOException {
			ClientUI.chat.accept(new Message(Request.import_Sales, null));
			setSale();
			Image image = new Image("/images/SalesMenagerScreenImage.png");
			saleImage.setImage(image);
			Image imagelogo = new Image("/images/IconOnly_Transparent_NoBuffer.png");
			logo.setImage(imagelogo);
			
		

	}

	/**
	 * 
	 * clickOnBtnBack method handles the event of the back button being clicked.
	 * 
	 * It hides the current window and navigates to the SalesWorker_MainView.
	 * 
	 * @param event the action event of the button being clicked
	 */
	@FXML
	void clickOnBtnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/SalesWorker_MainView.fxml");
	}

	/**
	 * 
	 * getExitBtn method handles the event of the exit button being clicked. It
	 * exits the application or logs out the user depending on the value of the
	 * second parameter.
	 * 
	 * @param event the action event of the button being clicked
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		newScreen.exitOrLogOut(event, false);
	}

	/**
	 * 
	 * setSale method is used to populate the gridpane with sales that need to be
	 * activated.
	 * 
	 * @throws IOException if there is an issue loading the FXML or image files
	 */
	public void setSale() throws IOException {
		int row = 3;
		int i = 0;
		System.out.println(ChatClient.salesController.getSales().toString());
		for (Sale sale : ChatClient.salesController.getSales()) {
			if (sale.getStatus().toString().equals("NEEDTOACTIVATE")
					&& sale.getRegion().toString().equals(ChatClient.userController.getUser().getRegion().toString())) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/clientGUI/SaleCoulmn.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();
				SaleCoulmnController saleController = fxmlLoader.getController();
				saleControllers.add(saleController);
				saleControllers.get(i++).setData(sale, this);
				gpSalesList.add(anchorPane, 0, row++);// (child column,row)

			}
		}
	}

	/**
	 * clearSales remove all sales in gridpane
	 */
	public void clearSales() {
		gpSalesList.getChildren().clear();
	}

}
