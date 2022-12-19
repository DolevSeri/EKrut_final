package clientControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class IdentificationController {
	IdentificationController myController;
	@FXML
	private TextField txtUsername = null;

	@FXML
	private TextField txtPswd = null;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnExit;

	@FXML
	private ImageView ekrutLogo;

	@FXML
	private ImageView QRimage;

	@FXML
	private Label lblUsername;

	@FXML
	private Label lblPswd;

	@FXML
	private Label lblErrorOnDetails;

	public void initialize() {
		lblErrorOnDetails.setVisible(false);
	}

	/*public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGUI/Identification_Interface.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Connect Form");

		myController = loader.getController();
		myController.logoImage.setImage(new Image("/images/FullLogo_Transparent_NoBuffer.png"));
		myController.QRimage.setImage(new Image("/images/QR_Code.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}*/
}
