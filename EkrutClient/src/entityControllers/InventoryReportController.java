package entityControllers;

import entities.InventoryReport;

public class InventoryReportController {
	
	private InventoryReport inventoryReport = null;

	public InventoryReport getInventoryReport() {
		return inventoryReport;
	}

	public void setInventoryReport(InventoryReport inventoryReport) {
		this.inventoryReport = inventoryReport;
	}
}

