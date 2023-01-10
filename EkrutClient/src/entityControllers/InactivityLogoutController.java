package entityControllers;

import java.io.IOException;

import client.ChatClient;
import client.ClientUI;
import clientControllers.SetSceneController;
import entities.Message;
import enums.Request;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InactivityLogoutController implements Runnable {
	public static int countMinitues = 0;
	SetSceneController newScreen = new SetSceneController();

	public InactivityLogoutController() {

	}

	@Override
	public void run() {
		try {
			// counting 15 minutes for client to finish order
			Thread.sleep(900000);
			Platform.runLater(() -> {
				ChatClient.cartController.clearCart();
				ChatClient.primaryStage.hide();
				newScreen.popUpMessage("Disconnected due to inactivity!");
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("/clientGUI/Identification_Interface.fxml"));
					Scene scene = new Scene(root);
					Stage primaryStage = new Stage();
					primaryStage.initStyle(StageStyle.UNDECORATED);
					primaryStage.setScene(scene);
					primaryStage.show();
					ClientUI.chat.accept(new Message(Request.Logout_request, ChatClient.userController.getUser()));
				} catch (IOException e) {
				}
				// newScreen.exitOrLogOut(event, true);
			});
		} catch (InterruptedException e) {
			// Handle interruption gracefully.
		}
	}

}
