package clientControllers;

import java.io.IOException;

import client.ClientUI;
import entities.Message;
import enums.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * ConnectFormController class is a controller class of the ConnectForm.fxml
 * file, which contains the functionality of the connect form.
 * 
 * The connect form is the first screen that the user sees when launching the
 * application, and it allows the user to enter the IP address of the server and
 * connect to it.
 * 
 * @author Ron Lahiani
 * 
 * @author Peleg Oanuno
 */
public class ConnectFormController {

	@FXML
	private Button btnExit;

	@FXML
	private TextField IPText;

	@FXML
	private Button btnConnect;

	@FXML
	private Text lblEnterIP;
	@FXML
	private ImageView ImageLogo;
	SetSceneController newScreen = new SetSceneController();

	/**
	 * 
	 * Initializes the connect form by loading the logo image.
	 * 
	 * @throws IOException if the image cannot be loaded.
	 */
	public void initialize() throws IOException {
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		ImageLogo.setImage(image);
	}
	/**

	Connects to the server by sending a connect request to the server, using the IP address entered by the user.
	If the IP address is empty, an error message is displayed.
	If the connection is successful, the identification interface is displayed.
	@param event the event that triggers the method.
	@throws IOException if the fxml file cannot be loaded.
	*/
	@FXML
	void getConnectBtn(ActionEvent event) throws IOException {
		String ip = IPText.getText();
		// FXMLLoader loader = new FXMLLoader();
		// commennFunction-בדיקה שלא ריק
		// Validate
		if (checkNull(ip)) {
			System.out.println("Please insert text");
			return;
		}
		ClientUI.setChat(ip, 5555);
		ClientUI.chat.accept(new Message(Request.Connect_request, null)); // change later to Message OBJECT
		// set login screen
		((Node) event.getSource()).getScene().getWindow().hide();
		newScreen.setScreen(new Stage(), "/clientGUI/Identification_Interface.fxml");
		// get Orders from DB
		ClientUI.chat.accept(new Message(Request.getOrders, null));
	}
	/**

	The method checkNull is used to check if the input string is null.
	@param str a string that needs to be checked
	@return a boolean value indicating whether the input string is null or not
	*/
	public boolean checkNull(String str) {
		if (str != null)
			return false;
		return true;

	}
	/**

	The method start is used to start the Connect Form GUI.

	It loads the Connect Form GUI layout, sets the title, and displays the window.

	@param primaryStage the primary stage of the application

	@throws Exception if an error occurs while loading the layout or displaying the window
	*/

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/ConnectForm.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);

		primaryStage.setTitle("Connect Form");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/**

	The method getExitBtn is used to exit the application when the Exit button is pressed.
	It hides the current window and exits the application.
	@param event the action event that triggered the method call
	@throws Exception if an error occurs while hiding the window
	*/
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		System.out.println("exit ConnectForm");
		System.exit(0);
	}

}
