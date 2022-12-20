package clientControllers;

import java.io.IOException;

import common.SetScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI_test extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		//ClientLoginController controller = loader.getController();
//		///EkrutClient/src/clientGUI/AreaManager_MainView.fxml
//		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/CEO_MainView.fxml"));
//		// Parent root = FXMLLoader.load(getClass().getResource("ClientLogin.fxml"));
//		Scene scene = new Scene(root);
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("Client");
//		primaryStage.show();
		

		SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/CEO_MainView.fxml");
	}

	@Override
	public void stop() throws IOException {
	}

}
