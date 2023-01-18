package client;

import javafx.stage.Stage;

public interface ScreenInterface {
    public String getTxtUsername();
    public String getTxtPswd();
    public void changeScreen(Stage stage,String path);
	public void SetTextLableErrorUserNotExist();
	public void SetTextLableErrorUserAlreadyLogIn();
}
