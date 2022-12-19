package client;
import clientControllers.ConnectFormController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
	public static ClientController chat; //only one instance

	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 public static void setChat(String ip, int port) {
		 chat = new ClientController(ip, port);
	 }
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub		
		ConnectFormController connectFormController= new ConnectFormController(); // create Subscriber form 	
		connectFormController.start(primaryStage);
		 	
	}
	
	
}