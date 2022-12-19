package clientControllers;

import java.io.IOException;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class MenuFrameController {
	
	   FXMLLoader loader = new FXMLLoader();
	   static String choose;

		/*private Subscriber s;
		private ChatClient client;	*/
	   
		@FXML
		private Button btnexit;
				
		@FXML
		private Button updateSubscriberButton;
		
		@FXML
		private Button showSubscriberDetails;
		
		@FXML
		public void getExitBtn(ActionEvent event) throws Exception {
			System.out.println("exit");
			ClientUI.chat.accept("Disconnect");
			System.exit(0);	
		}

		
		public void gethideBtn(ActionEvent event) throws Exception {
		
		/*	this.setOnMouseClicked( event -> {
				  Stage obj = (Stage) iconid.getScene().getWindow();
				  obj.setIconified(true);
				});*/
		
		}
		
		public void getbtnupdateSubscriberButton(ActionEvent event) throws Exception{
			System.out.println("Client Connect");
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			SplitPane root = loader.load(getClass().getResource("/GUI/SubscriberForm.fxml").openStream());
			Scene scene = new Scene(root);			
			primaryStage.setTitle("find Subscriber Tool");
			primaryStage.setScene(scene);		
			primaryStage.show();
			choose="btnupdateSubscriberButton";
		}
		public void getbtnshowSubscriberDetails(ActionEvent event) throws Exception {
			System.out.println("Client Connect");
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			SplitPane root = loader.load(getClass().getResource("/GUI/SubscriberForm.fxml").openStream());
			Scene scene = new Scene(root);			
			primaryStage.setTitle("find Subscriber Tool");
			primaryStage.setScene(scene);		
			primaryStage.show();
			choose="btnshowSubscriberDetails";
		}
		
		public void getbtncomeback(ActionEvent event) throws IOException {
			FXMLLoader loader = new FXMLLoader();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root = loader.load(getClass().getResource("/GUI/SubscriberForm.fxml").openStream());
		    Scene scene = new Scene(root);			
			primaryStage.setTitle("subscriber manegment tool");
			primaryStage.setScene(scene);		
			primaryStage.show();
			
		}
         
      /*  public void start(Stage primaryStage) throws Exception {
			System.out.println("im here");
			Parent root = FXMLLoader.load(getClass().getResource("/GUI/MenuForm.fxml"));
			System.out.println("im here2");
			Scene scene = new Scene(root);
			primaryStage.setTitle("Academic Managment Tool");
			primaryStage.setScene(scene);
			primaryStage.show(); 	   
			
				
			
		}*/
		/*public void start(Stage primaryStage) throws IOException {
			AnchorPane pane;
			try {
				FXMLLoader loader= new FXMLLoader();
				loader.setLocation(getClass().getResource("/GUI/MenuForm.fxml"));
				pane=loader.load();
				
				
			}catch(IOException e) {
				e.printStackTrace();
				return;
			}
			Scene scene = new Scene(pane);
			primaryStage.setTitle("Academic Managment Tool");
			primaryStage.setScene(scene);
			primaryStage.show(); 	   
			
			
		}*/
	
		
		
	}


