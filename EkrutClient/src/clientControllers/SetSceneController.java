package common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SetScene {

	public void setScreen(Stage primaryStage,String path)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource(path));
			Scene scene = new Scene(root);
			//String iconPath = isUserScreen == true ? userIconPath :  serverIconPath;
			//Image icon=new Image(getClass().getResourceAsStream(iconPath));
			//primaryStage.getIcons().add(icon);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			//makeUndecoratedScreenMovable(root,primaryStage);
			primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

}
