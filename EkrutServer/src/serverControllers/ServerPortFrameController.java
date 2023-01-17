package serverControllers;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import entities.Device;
import enums.Region;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import server.ClientConnected;
import server.Console;
import server.EchoServer;
import server.MySqlController;
import server.ServerUI;

/**
 * This class represents the controller for the server GUI FXML file. It handles
 * the user's interactions with the view and controls the server's connection
 * and disconnection.
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
	EndOfMonthChecker endOfMonthChecker;

	/**
	 * Handles the 'Connect' button click event. Reads the entered port number,
	 * database name, username, and password from the text fields. If the port
	 * number is not entered, displays an error message. Otherwise, runs the server
	 * with the given details and enables the 'Disconnect' button. Populates the
	 * clients table with the connected clients and sets the columns for the table.
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
			setColumns();
			if (MySqlController.getdbConnector() != null) {
				if (!checkIfReportExist()) {
					createReport(false);
				}

				ServerPortFrameController controller = new ServerPortFrameController();
				endOfMonthChecker = new EndOfMonthChecker(controller);
				endOfMonthChecker.start();
			}
		}
	}

	/**
	 * Check if the report for the previous month already exists in the database.
	 * 
	 * @return a boolean indicating whether the report for the previous month
	 *         already exists in the database.
	 */
	private boolean checkIfReportExist() {
		ArrayList<String> details = new ArrayList<>();
		Calendar now = Calendar.getInstance();
		Integer currentMonth = (Integer) now.get(Calendar.MONTH) + 1;
		Integer currentYear = (Integer) now.get(Calendar.YEAR);
		if (currentMonth == 1) {
			currentMonth = 12;
			currentYear--;
		} else {
			currentMonth = currentMonth - 1;
		}
		String formattedMonth = String.format("%02d", currentMonth);
		details.addAll(Arrays.asList("NORTH", currentYear.toString(), formattedMonth));

		if (MySqlController.getCostumersReportData(details) == null) {
			System.out.println("There is report to create");
			return false;
		} else {
			System.out.println("Last month report exist");
			return true;
		}
	}

	/**
	 * Creates monthly reports for customer, order, delivery, and inventory. The
	 * report will be created for the current month if {@code isThisMonth} is true,
	 * otherwise it will be created for the previous month.
	 * 
	 * @param isThisMonth a boolean indicating whether the report should be created
	 *                    for the current month or the previous month.
	 */
	public void createReport(boolean isThisMonth) {
		ArrayList<Device> devices = new ArrayList<>();
		Calendar now = Calendar.getInstance();
		int currentMonth = now.get(Calendar.MONTH) + 1;
		int currentYear = now.get(Calendar.YEAR);
		if (!isThisMonth) {
			if (currentMonth == 1) {
				currentMonth = 12;
				currentYear--;
			} else {
				currentMonth = currentMonth - 1;
			}
			System.out.println("Creating monthly reports for previous month");
		} else {
			System.out.println("Creating monthly reports for this month");
		}
		String formattedMonth = String.format("%02d", currentMonth);

		for (Region region : Region.values()) {
			if (!region.equals(Region.ALL)) {
				MySqlController.createMonthlyCostumersReport(new ArrayList<String>(
						Arrays.asList(formattedMonth, Integer.toString(currentYear), region.toString())));
				MySqlController.createMonthlyOrdersReport(new ArrayList<String>(
						Arrays.asList(formattedMonth, Integer.toString(currentYear), region.toString())));
				devices.addAll(MySqlController.getAllDevicesByArea(region.toString()));
			}
		}
		MySqlController.createMonthlyDeliveryReport(
				new ArrayList<String>(Arrays.asList(formattedMonth, Integer.toString(currentYear))));
		for (Device device : devices) {
			MySqlController.createMonthlyInventoryReport(new ArrayList<String>(
					Arrays.asList(formattedMonth, Integer.toString(currentYear), device.getDeviceName())));
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
	 * Handles the 'Disconnect' button click event. Stops the server and disables
	 * the 'Disconnect' button.
	 * 
	 * @param event the ActionEvent object representing the button click event
	 */
	@FXML
	public void clickbtnDisconnect(ActionEvent event) {
		try {
			endOfMonthChecker.getExecutor().shutdown();
		} catch (NullPointerException e) {
		}
		ServerUI.stopServer();
		btnDisconnect.setDisable(true);
		btnConnect.setDisable(false);
	}

	/**
	 * Initializes the view. Displays the server's IP address and default values for
	 * the port, database name, username, and password. Disables the 'Disconnect'
	 * button. Calls the showConsoleStream() method to display the console output in
	 * the console text area.
	 */
	public void initialize() {
		showConsoleStream();
		txtPort.setText("5555");
		txtIP.setText(getLocalHost());
		txtDbName.setText("jdbc:mysql://localhost/ekrut?serverTimezone=IST&useSSL=false");
		txtDbUser.setText("root");
		txtDbPass.setText("Aa123456");
		btnDisconnect.setDisable(true);
		btnImport.setDisable(false);
		Image image = new Image("/images/FullLogo_Transparent_NoBuffer.png");
		ekrutLogo.setImage(image);

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
	 * Handles the 'Exit' button click event. Stops the server and closes the
	 * application.
	 * 
	 * @param event the ActionEvent object representing the button click event
	 * @throws Exception if an exception occurs while setting the scene
	 */
	public void getExitBtn(ActionEvent event) throws Exception {
		try {
			endOfMonthChecker.getExecutor().shutdown();
		} catch (NullPointerException e) {
		}
		ServerUI.stopServer();
		System.exit(0);
	}

	/**
	 * Sets the columns for the clients table.
	 */
	public void setColumns() {
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

	/**
	 * 
	 * This method is a JavaFX event handler method that is triggered when the
	 * "Import" button is clicked. It calls the importUsers() method from the
	 * MySqlController class to import users from a "register_service" schema in a
	 * MySQL database and inserts them into the "ekrut" schema in the same database.
	 * If an exception is thrown by the importUsers() method, it will print the
	 * stack trace of the exception.
	 * 
	 * @param event the ActionEvent object that was generated when the button was
	 *              clicked.
	 */

	@FXML
	public void clickbtnImport(ActionEvent event) {
		try {
			MySqlController.importUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}