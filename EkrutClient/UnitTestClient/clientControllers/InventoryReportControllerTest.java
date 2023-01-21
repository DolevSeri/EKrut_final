package clientControllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;

import entities.InventoryReport;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class InventoryReportControllerTest {

	@Mock
	private entityControllers.InventoryReportController reportMock;
	private InventoryReportController controller;

	private ActionEvent event;
	private InventoryReport report;

	@BeforeEach
	public void setUp() {
		reportMock = Mockito.mock(entityControllers.InventoryReportController.class);
	}

	@Test(expected = NullPointerException.class)
	public void testInitialize_NullReport() {		
		when((reportMock).getInventoryReport()).thenReturn(null);
		controller.initialize();
	}

//	@Test
//	public void testInitialize_ExistingReportWithData() {
//		doNothing().when(reportMock).getInventoryReport();
//
//	}

}
//    private InventoryReportController controller;
//    private InventoryReport inventoryReport;
//    private ChatClient chatClient;
//
//    @Before
//    public void setUp() {
//        controller = new InventoryReportController();
//        inventoryReport = mock(InventoryReport.class);
//        chatClient = mock(ChatClient.class);
//        ClientUI clientUI = mock(ClientUI.class);
//        ChatClient.inventoryReportController = mock(InventoryReportController.class);
//        ChatClient.userController = mock(UserController.class);
//        ChatClient.setClientUI(clientUI);
//        ChatClient.setChatClient(chatClient);
//        when(ChatClient.inventoryReportController.getInventoryReport()).thenReturn(inventoryReport);
//    }

//    @Test
//    public void testInitialize() {
//        HashMap<String, Integer> producsUnderThreshold = new HashMap<>();
//        producsUnderThreshold.put("Product1", 10);
//        producsUnderThreshold.put("Product2", 20);
//        when(inventoryReport.getProducsUnderThreshold()).thenReturn(producsUnderThreshold);
//        when(inventoryReport.getMexProductUnderThres()).thenReturn("Product1");
//        when(inventoryReport.getDeviceThres()).thenReturn(5);
//        when(inventoryReport.getDeviceName()).thenReturn("Device1");
//        when(inventoryReport.getMonth()).thenReturn("January");
//        when(inventoryReport.getYear()).thenReturn("2022");
//        when(ChatClient.inventoryReportController.getAreaForCEO()).thenReturn("Area1");
//        when(ChatClient.userController.getUser().getRegion()).thenReturn("Area2");
//        when(ChatClient.userController.getUser().getRole()).thenReturn(Role.MANAGER);
//        controller.initialize();
//
//        assertEquals("Area2", controller.lblAreaField.getText());
//        assertEquals("Device1", controller.lblDeviceField.getText());
//        assertEquals("5", controller.lblTresholdField.getText());
//        assertEquals("January/2022", controller.lblReportDate.getText());
//        assertEquals("Product1", controller.lblItemUnderTreshold.getText());
//        assertEquals(2, controller.chrtInventory.getData().size());
//        for (PieChart.Data data : controller.chrtInventory.getData()) {
//            String label = data.getName();
//            int value = (int) data.getPieValue();
//            assertEquals(producsUnderThreshold.get(label.split("-
//
//}

/*
 import static org.mockito.Mockito.*;
import java.util.Calendar;
import org.junit.Test;

public class MyClassTest {
    @Test
    public void testStart() {
        // create a mock Calendar object that represents the last day of the month
        Calendar mockCalendar = mock(Calendar.class);
        when(mockCalendar.getInstance()).thenReturn(mockCalendar);
        when(mockCalendar.get(Calendar.DAY_OF_MONTH)).thenReturn(31);
        when(mockCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)).thenReturn(31);
        
        // create a mock controller
        MyController mockController = mock(MyController.class);
        
        // create a new instance of your class and set the mock controller
        MyClass myClass = new MyClass();
        myClass.setController(mockController);
        
        // call the start method
        myClass.start();
        
        // assert that the createReport method was called
        verify(mockController, times(1)).createReport(true);
    }
}
*/

