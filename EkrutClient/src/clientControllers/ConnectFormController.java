package clientControllers;

import java.io.IOException;

import client.ClientUI;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConnectFormController {

	@FXML
	private Button btnExit;

	@FXML
	private TextField IPText;

	@FXML
	private Button btnConnect;

	@FXML
	private Text lblEnterIP;

	private String getIP() {
		return IPText.getText();
	}

	@FXML
	void getConnectBtn(ActionEvent event) throws IOException {
		String ip = IPText.getText();
		// FXMLLoader loader = new FXMLLoader();
		// commennFunction-����� ��� ���
		// Validate
		if (checkNull(ip)) {
			System.out.println("Please insert text");
			return;
		}
		ClientUI.setChat(ip, 5555);
		ClientUI.chat.accept(new Message(Request.Connect_request, null)); // change later to Message OBJECT
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/Identification_Interface.fxml"));
		try {
			loader.load();
			Parent root = loader.getRoot();
			stage.getScene().setRoot(root);
			stage.sizeToScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// get Orders from DB
		ClientUI.chat.accept(new Message(Request.getOrders, null));
		ClientUI.chat.accept(new Message(Request.get_Msg_In_System, null));
	}

	public boolean checkNull(String str) {
		if (str != null)
			return false;
		return true;

	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/ConnectForm.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);

		primaryStage.setTitle("Connect Form");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

}
