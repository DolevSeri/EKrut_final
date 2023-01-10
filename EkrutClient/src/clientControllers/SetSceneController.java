package clientControllers;

import java.lang.reflect.Method;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SetSceneController {
	public void setScreen(Stage primaryStage, String path) {
		try {
			ChatClient.primaryStage = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource(path));
			Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void setScreenWithData(Stage primaryStage, String path, Object data) {
        try {
            ChatClient.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            Object controller = loader.getController();
            Method initData = controller.getClass().getMethod("initData", data.getClass());
            initData.invoke(controller, data);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void back(ActionEvent event, String path) {
		((Node) event.getSource()).getScene().getWindow().hide();
		setScreen(new Stage(), path);
	}

	public void exitOrLogOut(ActionEvent event, boolean isLogOut) {
		ClientUI.chat.accept(new Message(Request.Logout_request, ChatClient.userController.getUser()));
		if (isLogOut) {
			((Node) event.getSource()).getScene().getWindow().hide();
			setScreen(new Stage(), "/clientGUI/Identification_Interface.fxml");

		} else {
			ClientUI.chat.accept(new Message(Request.Disconnect_request, null));
			System.exit(0);
		}
	}

	public void popUpMessage(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);

		// Set the alert dialog style
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setStyle("-fx-background-color:  #D0A9F5;");

		alert.showAndWait();
	}
}
