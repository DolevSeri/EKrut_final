package clientControllers;

import java.io.IOException;

import client.ClientUI;
import entities.Subscriber;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewSubscriberDataController {
	private Subscriber s;
	@FXML
	private Label lblfname;
	@FXML
	private Label lblName;
	@FXML
	private Label lblid;
	@FXML
	private Label lblphoneN;
	@FXML
	private Label lblEmailA;
	@FXML
	private Label lblCreditcardN;
	@FXML
	private Label lblSubscriberN;
	
	@FXML
	private TextField txtID;
	@FXML
	private TextField txtFName;
	@FXML
	private TextField txtLName;
	@FXML
	private TextField txtphone;
	@FXML
	private TextField txtcreditcard;
	@FXML
	private TextField txtsubscribernumber;
	@FXML
	private TextField txtEmailadress;
	
	@FXML
	private Button btncomeback=null;
	@FXML
	private Button btnexit=null;
	@FXML
	private Button btnhide=null;
		
	
	ObservableList<String> list;
		
	public void loadSubscriberView(Subscriber s1) {
		this.s=s1;
		this.txtFName.setText(s.getFirstName());
		this.txtLName.setText(s.getLastName());
		this.txtID.setText(s.getID());
		this.txtcreditcard.setText(s.getCreditCardNumber());
		this.txtphone.setText(s.getPhoneNumber());
		this.txtEmailadress.setText(s.getEmailAddress());
		this.txtsubscribernumber.setText(s.getSubscriberNumber());
		
	}
	



	public void getbtncomeback(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = loader.load(getClass().getResource("/GUI/MenuFormClient.fxml").openStream());
	    Scene scene = new Scene(root);			
		primaryStage.setTitle("subscriber manegment tool");
		primaryStage.setScene(scene);		
		primaryStage.show();
		
	}
    

	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit");
		ClientUI.chat.accept("Disconnect");
		System.exit(0);	
	}
	
	
	
	
	public void gethideBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		System.out.println("hide Academic Tool");		
	}

	
}
