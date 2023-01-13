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

/**
 * 
 * InactivityLogoutController is a class that implements the runnable interface,
 * it is responsible for controlling the user's inactivity. If the user is
 * inactive for 15 minutes, the program will automatically log out the user and
 * return to the login screen.
 * 
 * @author Ron
 */
public class InactivityLogoutController implements Runnable {
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
