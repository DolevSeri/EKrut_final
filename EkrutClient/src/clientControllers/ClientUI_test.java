package clientControllers;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI_test extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//ClientLoginController controller = loader.getController();
		///EkrutClient/src/clientGUI/AreaManager_MainView.fxml
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/AreaManager_MainView.fxml"));
		// Parent root = FXMLLoader.load(getClass().getResource("ClientLogin.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Client");
		primaryStage.show();

	}

	@Override
	public void stop() throws IOException {
	}

}
