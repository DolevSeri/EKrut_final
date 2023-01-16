package entityControllers;

import entities.InventoryReport;

/**
 * 
 * The InventoryReportController class is responsible for handling the inventory
 * report.
 * 
 * It contains an InventoryReport object, which stores the information about the
 * report.
 * 
 * @author Ron Lahiani , Peleg Oanuno
 */
public class InventoryReportController {

	private InventoryReport inventoryReport = null;
	private String areaForCEO = null;
	/**
	 * 
	 * Getter method for the InventoryReport object.
	 * 
	 * @return The InventoryReport object that stores the information about the
	 *         report.
	 */
	public InventoryReport getInventoryReport() {
		return inventoryReport;
	}

	/**
	 * 
	 * Setter method for the InventoryReport object.
	 * 
	 * @param inventoryReport The InventoryReport object that stores the information
	 *                        about the report.
	 */
	public void setInventoryReport(InventoryReport inventoryReport) {
		this.inventoryReport = inventoryReport;
	}

	public String getAreaForCEO() {
		return areaForCEO;
	}

	public void setAreaForCEO(String areaForCEO) {
		this.areaForCEO = areaForCEO;
	}
}
