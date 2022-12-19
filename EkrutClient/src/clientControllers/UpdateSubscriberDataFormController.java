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

public class UpdateSubscriberDataFormController {


	private Subscriber s;
		
	
	@FXML
	private Label lblCreditcardN;
	@FXML
	private Label lblSubscriberN;
	@FXML
	private Label lblSubscriberid;
	
	@FXML
	private TextField txtcreditcard;
	@FXML
	private TextField txtsubscribernumber;
	@FXML
	private TextField txtID;
	

	@FXML
	private Button btncomeback=null;
	@FXML
	private Button btnexit=null;
	@FXML
	private Button btnhide=null;
	@FXML
	private Button btnupdate=null;
		
	
		
	
	ObservableList<String> list;
		
	public void loadSubscriberUpdate(Subscriber s1) {
		this.s=s1;
		this.txtsubscribernumber.setText(s.getSubscriberNumber());
		this.txtcreditcard.setText(s.getCreditCardNumber());
		this.txtID.setText(s.getID());
		txtID.setDisable(true);
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
	
	public void getbtnUpdate(ActionEvent event) throws IOException {
		String details = "";
		//this.loadSubscriber1(s);
		details += txtID.getText() + " ";
		details += txtcreditcard.getText() + " ";
		details += txtsubscribernumber.getText();
		ClientUI.chat.accept("Update "+ details);
	
	}
	public void getExitBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		System.out.println("exit Academic Tool");		
	}
	
	
	
	


}
