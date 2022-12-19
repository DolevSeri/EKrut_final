package clientControllers;

import java.io.IOException;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SubscriberFormController {

	@FXML
	private Button btnExit = null;

	@FXML
	private Button btnHide = null;

	@FXML
	private Button btSearch = null;

	@FXML
	private Button btcomenack = null;
	@FXML
	private Button btnExitError;

	@FXML
	private TextField txtID;

	private String getID() {
		return txtID.getText();
	}

	public void Searchbtn(ActionEvent event) throws Exception {
		String id;
		FXMLLoader loader = new FXMLLoader();
		id = getID();
		if (id.trim().isEmpty()) {

			System.out.println("You must enter an id number");
		} else {
			ClientUI.chat.accept("View " + id);
			if (ChatClient.s1.getID().equals("ERROR")) {
				Stage primaryStage1 = new Stage();
				AnchorPane root = loader
						.load(getClass().getResource("/GUI/ErrorMessage.fxml").openStream());
				Scene scene = new Scene(root);
				primaryStage1.setTitle("Subscriber view tool");
				primaryStage1.setScene(scene);
				primaryStage1.show();
			}

			// ClientUI.chat.accept(id);
			else {
				System.out.println("Subscriber ID Found");
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				
				if (MenuFrameController.choose.compareTo("btnupdateSubscriberButton") == 0) {
					AnchorPane root = loader
							.load(getClass().getResource("/GUI/UpdateSubscriberFormClient.fxml").openStream());
					UpdateSubscriberDataFormController myController = loader.getController();
					myController.loadSubscriberUpdate(ChatClient.s1);
					Scene scene = new Scene(root);
					primaryStage.setTitle("Subscriber update tool");
					primaryStage.setScene(scene);
					primaryStage.show();
				} else if (MenuFrameController.choose.compareTo("btnshowSubscriberDetails") == 0) {
					AnchorPane root = loader
							.load(getClass().getResource("/GUI/ViewSubscriberDetailsClient.fxml").openStream());
					ViewSubscriberDataController vsdf = loader.getController();
					vsdf.loadSubscriberView(ChatClient.s1);
					Scene scene = new Scene(root);
					primaryStage.setTitle("Subscriber view tool");
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			}

		}
	}

	public void getbtncomeback(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		System.out.println("Student ID Found");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = loader.load(getClass().getResource("/GUI/MenuFormClient.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("menu tool");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void getExitBtn(ActionEvent event) throws Exception {
		ClientUI.chat.accept("Disconnect");
		System.exit(0);
	}
	
	public void getExitBtnError(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	}

	public void display(String message) {
		System.out.println("message");

	}

	public void gethideBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("hide Academic Tool");
	}

}
