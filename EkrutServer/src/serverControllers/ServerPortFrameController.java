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

	public void showConsoleStream() {
		showConsole = new PrintStream(new Console(txtConsole));
		System.setOut(showConsole);
		System.setErr(showConsole);
	}

	@FXML
	public void clickbtnDisconnect(ActionEvent event) {
		ServerUI.stopServer();
		btnDisconnect.setDisable(true);
		btnConnect.setDisable(false);
	}

	public void initialize() {
	    showConsoleStream();
		txtPort.setText("5555");
		txtIP.setText(getLocalHost());
		txtDbName.setText("jdbc:mysql://localhost/ekrut?serverTimezone=IST&useSSL=false");
		txtDbUser.setText("root");
		txtDbPass.setText("Dolev1995");
		btnDisconnect.setDisable(true);
		btnImport.setDisable(true);

	}

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

	public void getExitBtn(ActionEvent event) throws Exception {
		ServerUI.stopServer();
		System.exit(0);
	}

	public void setCulomns() {
		IP.setCellValueFactory(new PropertyValueFactory<ClientConnected, String>("IP"));
		Host.setCellValueFactory(new PropertyValueFactory<ClientConnected, String>("host"));
		Status.setCellValueFactory(new PropertyValueFactory<ClientConnected, String>("status"));

	}

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
		txtDbPass.setText("Dolev1995");

	}

}