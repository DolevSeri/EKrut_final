package entityControllers;

import entities.OrderReport;

/**
 * 
 * The OrderReportController class controls the OrderReport object and its data.
 * 
 * @author Ron Lahiani , Peleg Oanuno
 * 
 */
public class OrderReportController {

	/**
	 * 
	 * The OrderReport object that holds the data for the order report.
	 */
	private OrderReport orderReport = null;

	/**
	 * 
	 * Gets the OrderReport object.
	 * 
	 * @return The OrderReport object.
	 */
	public OrderReport getOrderReport() {
		return orderReport;
	}

	/**
	 * 
	 * Sets the OrderReport object with the given OrderReport object.
	 * 
	 * @param orderReport The OrderReport object to set.
	 */
	public void setOrderReport(OrderReport orderReport) {
		this.orderReport = orderReport;
	}
}
