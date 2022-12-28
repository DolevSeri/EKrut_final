package clientControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.Product;
import entities.ProductInDevice;
import entities.Message;
import enums.Request;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client_OrderScreenController {
	FXMLLoader loader = new FXMLLoader();
	SetSceneController newScreen = new SetSceneController();

	@FXML
	private Button btnBack;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnEndOrder;

	@FXML
	private Button btnExit;

	@FXML
	private GridPane gpCart;

	@FXML
	private GridPane gpCatalog;

	@FXML
	private Label lblTotalPrice;

	@FXML
	private ImageView logoImage;

	public static ArrayList<Product> selectedItems = new ArrayList<Product>();
	private ObservableList<ProductInDevice> products;
	public static double totalPrice = 0;

	public void initialize() {
		ClientUI.chat
				.accept(new Message(Request.Get_Products, ChatClient.userController.getUser().getRegion().toString()));
		products = ChatClient.productCatalogController.getProductCatalog();
		SetGrid();

	}

	private void SetGrid() {
		// TODO Auto-generated method stub

	}

	@FXML
	void clickOnBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_EK_MainView.fxml");
	}

	@FXML
	void clickOnCancel(ActionEvent event) {

	}

	@FXML
	void clickOnExit(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

	@FXML
	void clickOnEndOrder(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		newScreen.setScreen(new Stage(), "/clientGUI/Client_OrderConfirmation.fxml");
	}

}
