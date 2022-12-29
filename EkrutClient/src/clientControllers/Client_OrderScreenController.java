package clientControllers;

import java.io.IOException;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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
	private ArrayList<ProductController> productControllers = new ArrayList<>();
	public static double totalPrice = 0;

	public void initialize() {
		ClientUI.chat.accept(
				new Message(Request.Get_Products, ChatClient.costumerController.getCostumer().getDevice().toString()));
		products = ChatClient.productCatalogController.getProductCatalog();
		try {
			setCatalog();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SetGrid();
	}

	private void setCatalog() throws IOException {
		int column = 0;
		int row = 1;
		int i = 0;
		for (ProductInDevice p : products) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/clientGUI/Product.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();
			ProductController productController = fxmlLoader.getController();
			productControllers.add(productController);
			productControllers.get(i).setData(p.getProductName(), p.getPrice(), null, p.getImagePath());
			gpCart.add(anchorPane, column++, row++);// (child column,row)
			if (column == 3)
				column=0;
			// Set grid width
			gpCart.setMinWidth(Region.USE_COMPUTED_SIZE);
			gpCart.setPrefWidth(Region.USE_COMPUTED_SIZE);
			gpCart.setMaxWidth(Region.USE_COMPUTED_SIZE);
			// Set grid height
			gpCart.setMinHeight(Region.USE_COMPUTED_SIZE);
			gpCart.setPrefHeight(Region.USE_COMPUTED_SIZE);
			gpCart.setMaxHeight(Region.USE_COMPUTED_SIZE);
			GridPane.setMargin(anchorPane, new Insets(5));
		}
	}

	private void SetGrid() {

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
