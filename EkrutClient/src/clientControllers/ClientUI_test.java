package clientControllers;

import java.io.IOException;

import common.SetScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI_test extends Application {

	public static void main(String[] args) {
		launch(args);
	}
//da3
	@Override
	public void start(Stage primaryStage) throws Exception {

		

		SetScene scene = new SetScene();
		scene.setScreen(new Stage(), "/clientGUI/CEO_MainView.fxml");
	}

	@Override
	public void stop() throws IOException {
	}

}
