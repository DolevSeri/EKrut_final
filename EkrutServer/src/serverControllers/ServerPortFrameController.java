package serverControllers;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import common.ClientConnected;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import server.Console;
import server.EchoServer;
import server.ServerUI;

/**
 * This class represents the controller for the server GUI FXML file.
 * It handles the user's interactions with the view and controls the server's connection and disconnection.
 * 
 * @author Dolev Seri
 */
public class ServerPortFrameController {

	@FXML
	private TextField txtIP;

	@FXML
	private TextField txtPort;

	@FXML
	private TextField txtDbName;

	@FXML
	private TextField txtDbUser;

	@FXML
	private PasswordField txtDbPass;

	@FXML
	private TableView<ClientConnected> tblConClients;

	@FXML
	private TableColumn<ClientConnected, String> IP;

	@FXML
	private TableColumn<ClientConnected, String> Host;

	@FXML
	private TableColumn<ClientConnected, String> Status;

	@FXML
	private TextArea txtConsole;

	@FXML
	private Button btnConnect;

	@FXML
	private Button btnDisconnect;

	@FXML
	private Button btnImport;
	@FXML
	private Button btnExit;
	@FXML
	private ImageView ekrutLogo;

	private PrintStream showConsole;
	
	/**
	 * Handles the 'Connect' button click event.
	 * Reads the entered port number, database name, username, and password from the text fields.
	 * If the port number is not entered, displays an error message.
	 * Otherwise, runs the server with the given details and enables the 'Disconnect' button.
	 * Populates the clients table with the connected clients and sets the columns for the table.
	 *
	 * @param event the ActionEvent object representing the button click event
	 * @throws Exception if an exception occurs while setting the scene
	 */
	@FXML
	public void clickBtnConnect(ActionEvent event) throws Exception {
		String ekrutPort, dbName, dbUserName, dbPwd;
		ekrutPort = txtPort.getText();
		dbName = txtDbName.getText();
		dbUserName = txtDbUser.getText();
		dbPwd = txtDbPass.getText();

		if (ekrutPort.trim().isEmpty()) {
			System.out.println("You must enter a port number");

		} else {

			ServerUI.runServer(ekrutPort, dbName, dbUserName, dbPwd);
			btnConnect.setDisable(true);
			btnDisconnect.setDisable(false);
			EchoServer sv = ServerUI.getSv();
			tblConClients.setEditable(true);
			tblConClients.setItems(sv.getClientList());
			setCulomns();
		}
	}
	/**
	 * Displays the console output in the console text area.
	 */
	public void showConsoleStream() {
		showConsole = new PrintStream(new Console(txtConsole));
		System.setOut(showConsole);
		System.setErr(showConsole);
	}
	
	/**
	 * Handles the 'Disconnect' button click event.
	 * Stops the server and disables the 'Disconnect' button.
	 * 
	 * @param event the ActionEvent object representing the button click event
	 */
	@FXML
	public void clickbtnDisconnect(ActionEvent event) {
		ServerUI.stopServer();
		btnDisconnect.setDisable(true);
		btnConnect.setDisable(false);
	}
	
	/**
	 * Initializes the view.
	 * Displays the server's IP address and default values for the port, database name, username, and password.
	 * Disables the 'Disconnect' button.
	 * Calls the showConsoleStream() method to display the console output in the console text area.
	 */
	public void initialize() {
	    showConsoleStream();
		txtPort.setText("5555");
		txtIP.setText(getLocalHost());
		txtDbName.setText("jdbc:mysql://localhost/ekrut?serverTimezone=IST&useSSL=false");
		txtDbUser.setText("root");
		txtDbPass.setText("Aa123456");
		btnDisconnect.setDisable(true);
		btnImport.setDisable(true);

	}
	
	/**
	 * Returns the server's IP address.
	 * 
	 * @return the server's IP address as a String
	 */
	public String getLocalHost() {
		String localHost;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress().toString();
			return localHost;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Handles the 'Exit' button click event.
	 * Stops the server and closes the application.
	 * 
	 * @param event the ActionEvent object representing the button click event
	 * @throws Exception if an exception occurs while setting the scene
	 */
	public void getExitBtn(ActionEvent event) throws Exception {
		ServerUI.stopServer();
		System.exit(0);
	}
	
	/**
	 * Sets the columns for the clients table.
	 */
	public void setCulomns() {
		IP.setCellValueFactory(new PropertyValueFactory<ClientConnected, String>("IP"));
		Host.setCellValueFactory(new PropertyValueFactory<ClientConnected, String>("host"));
		Status.setCellValueFactory(new PropertyValueFactory<ClientConnected, String>("status"));

	}
	/**
	 * Starts the server GUI application.
	 * 
	 * @param primaryStage the primary stage for the application
	 */
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/serverGUI/ServerPort.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);

			primaryStage.show();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void clickbtnImport(ActionEvent event) {
		txtPort.setText("5555");
		txtIP.setText(getLocalHost());
		txtDbName.setText("jdbc:mysql://localhost/ekrut?serverTimezone=IST");
		txtDbUser.setText("root");
		txtDbPass.setText("Aa123456");

	}

}