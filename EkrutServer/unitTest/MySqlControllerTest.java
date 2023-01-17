import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.InventoryReport;
import server.MySqlController;

class MySqlControllerTest {
	
	
	
	//Variables for inventory report tests
	InventoryReport haifa6_22;
	InventoryReport month2;
	
	HashMap<String, Integer> products = new HashMap<>();
	ArrayList<String> data = new ArrayList<>(Arrays.asList("NORTH","2022", "06","InventoryReport", "Haifa"));
	

	@BeforeEach
	void setUp() throws Exception {
		MySqlController.connectToDB("jdbc:mysql://localhost/ekrut?serverTimezone=IST&useSSL=false", "root", "Aa123456");
		products.put("Bamba", 7);
		products.put("BisliGrill", 15);
		products.put("DoritosExtra", 3);
		products.put("BambaNogat", 19);
		haifa6_22 = new InventoryReport("06", "2022", "Haifa", products, "BambaNogat", 6);
		
	}

	@Test
	void getInventoryReportTest_ExisttingReport() {
		InventoryReport res = MySqlController.getInventoryReportData(data);
		assertEquals(res, haifa6_22);
	}

}
