package clientControllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import client.ChatClient;
import client.ClientUI;
import entities.InventoryReport;
import enums.Role;

public class InventoryReportControllerTest {
    private InventoryReportController controller;
    private InventoryReport inventoryReport;
    private ChatClient chatClient;

    @Before
    public void setUp() {
        controller = new InventoryReportController();
        inventoryReport = mock(InventoryReport.class);
        chatClient = mock(ChatClient.class);
        ClientUI clientUI = mock(ClientUI.class);
        ChatClient.inventoryReportController = mock(InventoryReportController.class);
        ChatClient.userController = mock(UserController.class);
        ChatClient.setClientUI(clientUI);
        ChatClient.setChatClient(chatClient);
        when(ChatClient.inventoryReportController.getInventoryReport()).thenReturn(inventoryReport);
    }

    @Test
    public void testInitialize() {
        HashMap<String, Integer> producsUnderThreshold = new HashMap<>();
        producsUnderThreshold.put("Product1", 10);
        producsUnderThreshold.put("Product2", 20);
        when(inventoryReport.getProducsUnderThreshold()).thenReturn(producsUnderThreshold);
        when(inventoryReport.getMexProductUnderThres()).thenReturn("Product1");
        when(inventoryReport.getDeviceThres()).thenReturn(5);
        when(inventoryReport.getDeviceName()).thenReturn("Device1");
        when(inventoryReport.getMonth()).thenReturn("January");
        when(inventoryReport.getYear()).thenReturn("2022");
        when(ChatClient.inventoryReportController.getAreaForCEO()).thenReturn("Area1");
        when(ChatClient.userController.getUser().getRegion()).thenReturn("Area2");
        when(ChatClient.userController.getUser().getRole()).thenReturn(Role.MANAGER);
        controller.initialize();

        assertEquals("Area2", controller.lblAreaField.getText());
        assertEquals("Device1", controller.lblDeviceField.getText());
        assertEquals("5", controller.lblTresholdField.getText());
        assertEquals("January/2022", controller.lblReportDate.getText());
        assertEquals("Product1", controller.lblItemUnderTreshold.getText());
        assertEquals(2, controller.chrtInventory.getData().size());
        for (PieChart.Data data : controller.chrtInventory.getData()) {
            String label = data.getName();
            int value = (int) data.getPieValue();
            assertEquals(producsUnderThreshold.get(label.split("-

}
