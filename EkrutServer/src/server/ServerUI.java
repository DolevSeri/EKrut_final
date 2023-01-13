package server;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import serverControllers.ServerPortFrameController;

/**
 * The ServerUI class provides a user interface for controlling an EchoServer instance.
 * It allows the user to start and stop the server, and display the status of the server.
 * This class does not create a new instance of the EchoServer, it controls the singleton instance.
 */
public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	 /**
     * The singleton instance of the EchoServer class.
     */
    private static EchoServer sv;
    


	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// TODO Auto-generated method stub				  		
		ServerPortFrameController aFrame = new ServerPortFrameController(); // create StudentFrame
		 
		aFrame.start(primaryStage);
	}
	
	public static EchoServer getSv() {
		return sv;
	}
	
	
	public static void runServer(String ekrutPort, String dbName, String dbUserName, String dbPwd)
	{
		
			 int port = 0; //Port to listen on

		        try
		        {
		        	port = Integer.parseInt(ekrutPort); //Set port to 5555
		          
		        }
		        catch(Throwable t)
		        {
		        	System.out.println("ERROR - Could not connect!");
		        }
		    	
		       
		        sv= EchoServer.getInstance(port);
		
		
		        
		        try 
		        {

		          sv.listen(); //Start listening for connections
		        
		        } 
		        catch (Exception ex) 
		        {
		          System.out.println("ERROR - Could not listen for clients!");

		        }
			
		
	        MySqlController.connectToDB(dbName,dbUserName,dbPwd);

	        
	}
	
	public static void stopServer() {
		if (sv != null) {
			MySqlController.disconnectFromDB();
			System.out.println("Server Disconnected");	
			try {
				sv.close();
				sv = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

		
}


