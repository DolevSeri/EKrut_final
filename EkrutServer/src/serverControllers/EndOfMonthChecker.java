package serverControllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import entities.Device;
import enums.Region;
import server.MySqlController;

public class EndOfMonthChecker {

	public void start() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Runnable endOfMonthChecker = new Runnable() {
			public void run() {
				Calendar today = Calendar.getInstance();
				int lastDayOfMonth = today.getActualMaximum(Calendar.DAY_OF_MONTH);
				if (today.get(Calendar.DAY_OF_MONTH) == lastDayOfMonth) {
					createReport();
				}
			}
		};
		executor.scheduleAtFixedRate(endOfMonthChecker, 0, 1, TimeUnit.DAYS);
	}

	private void createReport() {
		ArrayList<Device> devices = new ArrayList<>();
		Calendar now = Calendar.getInstance();
		int currentMonth = now.get(Calendar.MONTH) + 1; // add 1 because Calendar.MONTH starts at 0
		int currentYear = now.get(Calendar.YEAR);
		for (Region region : Region.values()) {
			System.out.println("Creating monthly reports");
			MySqlController.createMonthlyCostumersReport(new ArrayList<String>(
					Arrays.asList(Integer.toString(currentMonth), 
							Integer.toString(currentYear), region.toString())));
			MySqlController.createMonthlyOrdersReport(new ArrayList<String>(
					Arrays.asList(Integer.toString(currentMonth), 
							Integer.toString(currentYear), region.toString())));
			devices.addAll(MySqlController.getAllDevicesByArea(region.toString()));
		}
		for (Device device : devices) {
			MySqlController.createMonthlyInventoryReport(new ArrayList<String>(
					Arrays.asList(Integer.toString(currentMonth), 
							Integer.toString(currentYear), device.getDeviceName())));
		}
	}
}
