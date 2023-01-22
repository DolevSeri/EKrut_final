package clientControllers;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import entities.InventoryReport;
import entityControllers.InventoryReportsController;

public class InventoryReportControllerTest {

	@Mock
	private InventoryReportsController inventoryReportsController;

	private InventoryReportController controller = new InventoryReportController();
	HashMap<String, Integer> products = new HashMap<>();
	HashMap<String, Integer> noProducts = new HashMap<>();

	@Before
	public void setUp() {
		inventoryReportsController = Mockito.mock(InventoryReportsController.class);
		controller.setInventoryReportsController(inventoryReportsController);
		products.put("Bamba", 7);
		products.put("BisliGrill", 15);
		products.put("DoritosExtra", 3);
		products.put("BambaNogat", 19);
	}

	/**
	 * Description: Checking getOurReport method for existing report Input data:
	 * InventoryReport("06", "2022", "Haifa", products, "BambaNogat", 6) Expected
	 * result: InventoryReport("06", "2022", "Haifa", products, "BambaNogat", 6)
	 */
	@Test
	public void getOurReportTest_ExsitingReport() {
		InventoryReport expected = new InventoryReport("06", "2022", "Haifa", products, "BambaNogat", 6);
		when((inventoryReportsController).getInventoryReport()).thenReturn(expected);
		InventoryReport result = controller.getOurReport();
		assertEquals(expected, result);
	}

	/**
	 * Description: Checking getOurReport method for existing report with no
	 * products under threshold Input data: InventoryReport("06", "2022", "Haifa",
	 * noProducts, null, 6) Expected result: InventoryReport("06", "2022", "Haifa",
	 * noProducts, null, 6)
	 */
	@Test
	public void getOurReportTest_ExsitingReportWithoutProductsData() {
		InventoryReport expected = new InventoryReport("06", "2022", "Haifa", noProducts, null, 6);
		when((inventoryReportsController).getInventoryReport()).thenReturn(expected);
		InventoryReport result = controller.getOurReport();
		assertEquals(expected, result);
	}

	/**
	 * Description: Checking getOurReport method for not existing report Input data:
	 * null InventoryReport Expected result: null InventoryReport
	 */
	@Test
	public void getOurReportTest_NullReport() {
		InventoryReport expected = null;
		when((inventoryReportsController).getInventoryReport()).thenReturn(null);
		InventoryReport result = controller.getOurReport();
		assertEquals(expected, result);
	}

	/**
	 * Description: Checking getOurReport method when controller is not set Input
	 * data: null InventoryReportsController Expected result: NullPointerException
	 * thrown
	 */
	@Test
	public void getOurReportTest_NullReportController() {
		controller.setInventoryReportsController(null);
		assertThrows(NullPointerException.class, () -> controller.getOurReport());
	}

}
