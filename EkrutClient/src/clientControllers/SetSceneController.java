package clientControllers;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SetSceneController {
	public void setScreen(Stage primaryStage, String path) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(path));
			Scene scene = new Scene(root);

			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void back(ActionEvent event, String path) {
		((Node) event.getSource()).getScene().getWindow().hide();
		setScreen(new Stage(), path);
	}

	public void exitOrLogOut(ActionEvent event,boolean isLogOut) {
		ClientUI.chat.accept(new Message(Request.Logout_request, ChatClient.userController.getUser().getUsername()));
		if (isLogOut) {
			((Node) event.getSource()).getScene().getWindow().hide();
			setScreen(new Stage(), "clientGUI/Identification_Interface.fxml");
		}
		else {
			ClientUI.chat.accept(new Message(Request.Disconnect_request,null));

		}
		System.exit(0);
	}
}
