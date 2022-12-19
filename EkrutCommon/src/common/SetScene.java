package common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SetScene {
	private String userIconPath = "/GuiAssests/userIcon.png";
	private String serverIconPath = "/GuiAssests/serverIcon.jpeg";
	public void setScreen(Stage primaryStage,String path,boolean isUserScreen)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource(path));
			Scene scene = new Scene(root);
			String iconPath = isUserScreen == true ? userIconPath :  serverIconPath;
			//Image icon=new Image(getClass().getResourceAsStream(iconPath));
			//primaryStage.getIcons().add(icon);
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			//makeUndecoratedScreenMovable(root,primaryStage);
			primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

}
