import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.InventoryReport;
import server.MySqlController;

class MySqlControllerTest {

	// Variables for inventory report tests
	InventoryReport haifa06_22;
	InventoryReport habshan12_22;
	InventoryReport haifa_check; // for checking create report functionality
	InventoryReport nullReport;
	HashMap<String, Integer> products = new HashMap<>();
	HashMap<String, Integer> haifaPoductsCheck = new HashMap<>();
	HashMap<String, Integer> emptyProducts = new HashMap<>();
	ArrayList<String> data = new ArrayList<>(Arrays.asList("NORTH", "2022", "06", "InventoryReport", "Haifa"));
	ArrayList<String> data2 = new ArrayList<>(Arrays.asList("UAE", "2022", "12", "InventoryReport", "Habshan"));
	ArrayList<String> data3 = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		MySqlController.connectToDB("jdbc:mysql://localhost/ekrut?serverTimezone=IST&useSSL=false", "root", "Aa123456");
		products.put("Bamba", 7);
		products.put("BisliGrill", 15);
		products.put("DoritosExtra", 3);
		products.put("BambaNogat", 19);
		haifa06_22 = new InventoryReport("06", "2022", "Haifa", products, "BambaNogat", 6);

		habshan12_22 = new InventoryReport("12", "2022", "Habshan", emptyProducts, null, 2);

		nullReport = null;

		haifaPoductsCheck.put("Bamba", 51);
		haifaPoductsCheck.put("ShugiUgi", 8);
		haifaPoductsCheck.put("yellowM&M", 18);
		haifaPoductsCheck.put("RedFanta", 23);
		haifa_check = new InventoryReport("12", "2022", "Haifa", haifaPoductsCheck, "Bamba", 6);
	}

	// Functionality: check getInventoryReport functionality when report is exist on
	// DB
	// Input: haifa06_22 (InventoryReport), res(IneventoryReport)
	// Expected Result: true
	@Test
	void getInventoryReportTest_ExisttingReport() {
		InventoryReport res = MySqlController.getInventoryReportData(data);
		assertEquals(res, haifa06_22);
	}

	// Functionality: check getInventoryReport functionality when report is exist on
	// DB
	// but some of its field are empty
	// Input: habshan12_22 (InventoryReport), res(IneventoryReport)
	// Expected Result:true
	@Test
	void getInventoryReportTest_EmptyReport() {
		InventoryReport res = MySqlController.getInventoryReportData(data2);
		assertEquals(res, habshan12_22);
	}

	// Functionality: check getInventoryReport functionality when report is not
	// exist
	// on DB
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: true
	@Test
	void getInventoryReportTest_ReportNotExist_ByAllFields() {
		data3.addAll(Arrays.asList("SOUTH", "2023", "1", "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when report is not
	// exist
	// on DB
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result:true
	@Test
	void getInventoryReportTest_ReportNotExist_ByMonth() {
		data3.addAll(Arrays.asList("SOUTH", "2022", "1", "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when report is not
	// exist
	// on DB
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: true
	@Test
	void getInventoryReportTest_ReportNotExist_ByYear() {
		data3.addAll(Arrays.asList("SOUTH", "2020", "12", "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when report is not
	// exist
	// on DB
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result:true
	@Test
	void getInventoryReportTest_ReportNotExist_ByDeviceName() {
		data3.addAll(Arrays.asList("SOUTH", "2022", "12", "InventoryReport", "Eilat"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when report data list
	// is empty
	// Input: res(IneventoryReport)
	// Expected Result: throw IndexOutOfBoundsException
	@Test
	void getInventoryReportTest_EmptyData() {
		assertThrows(IndexOutOfBoundsException.class, () -> MySqlController.getInventoryReportData(data3));
	}

	// Functionality: check getInventoryReport functionality when month on report
	// data is null
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result:true
	@Test
	void getInventoryReportTest_ReportNotExist_nullMonth() {
		data3.addAll(Arrays.asList("SOUTH", "2022", null, "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when year on report
	// data is null
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result: true
	@Test
	void getInventoryReportTest_ReportNotExist_nullYear() {
		data3.addAll(Arrays.asList("SOUTH", null, "12", "InventoryReport", "TelAviv"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check getInventoryReport functionality when device name on
	// report data is null
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result:true
	@Test
	void getInventoryReportTest_ReportNotExist_nullDeviceName() {
		data3.addAll(Arrays.asList("SOUTH", "2022", "12", "InventoryReport", null));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, nullReport);
	}

	// Functionality: check createInventoryReport functionality all details valid
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result:true
	@Test
	void createInventoryReport_ReportWithProducts() {
		data3.addAll(Arrays.asList("NORTH", "2022", "12", "InventoryReport", "Haifa"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		// check if the report does not already exist on DB
		assertEquals(res, nullReport);
		MySqlController.createMonthlyInventoryReport(new ArrayList<String>(Arrays.asList("12", "2022", "Haifa")));
		res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, haifa_check);
	}

	// Functionality: check createInventoryReport functionality all details valid
	// when there is no products that was under threshold at this month
	// Input: nullRepo (InventoryReport), res(IneventoryReport)
	// Expected Result:true
	@Test
	void createInventoryReport_ReportWithoutProducts() {
		data3.addAll(Arrays.asList("NORTH", "2020", "01", "InventoryReport", "Haifa"));
		InventoryReport res = MySqlController.getInventoryReportData(data3);
		// check if the report does not already exist on DB
		assertEquals(res, nullReport);
		InventoryReport expected = new InventoryReport("01", "2020", "Haifa", emptyProducts, null, 6);
		MySqlController.createMonthlyInventoryReport(new ArrayList<String>(Arrays.asList("01", "2020", "Haifa")));
		res = MySqlController.getInventoryReportData(data3);
		assertEquals(res, expected);
	}

	// Functionality: check createInventoryReport functionality all details valid
		// when there is no products that was under threshold at this month
		// Input: nullRepo (InventoryReport), res(IneventoryReport)
		// Expected Result:true
		@Test
		void createInventoryReport_nullReportData() {
			assertThrows(NullPointerException.class, () -> MySqlController.getInventoryReportData(null));

		}
}
