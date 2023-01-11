package serverControllers;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EndOfMonthChecker extends Application {
    private Label label;
    //private List<Month> offMonths = Arrays.asList(Month.JANUARY, Month.JULY); // list of "off" months
    List<Month> allMonths = Arrays.asList(Month.values());
    Map<Month,Boolean> map=new HashMap<>();
   
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        label = new Label();
        StackPane root = new StackPane();
        root.getChildren().add(label);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("End of Month Checker");
        primaryStage.setScene(scene);
        primaryStage.show();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	Month oct=Month.OCTOBER;
            	LocalDate date = LocalDate.now();
            	for(int i=0;i<allMonths.size();i++) {
            		   map.put(allMonths.get(i), false);
            	   }
            	
            	
                while (true) {
                	for(Map.Entry<Month,Boolean> targetMonth: map.entrySet()) {
                		if (date.getMonth().compareTo(targetMonth.getKey()) > 0&&targetMonth.getValue()==false) {
                            //call the sql to create something new
                        }
                	}
                    //LocalDate date = LocalDate.now();
                    if (date.getDayOfMonth() == date.lengthOfMonth()) {
                    	//call the sql to create something new
                    	//if this is a new year
                    	 if(date.getMonth()==oct) {
                    		 for(Map.Entry<Month,Boolean> targetMonth: map.entrySet()) {
                    			 map.replace(targetMonth.getKey(), false);
                    		 }
                         }
                    	
                    }
                   
                    Thread.sleep(1000);
                }
            }
        };
        label.textProperty().bind(task.messageProperty());
        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}
