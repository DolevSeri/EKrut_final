package serverControllers;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The EndOfMonthChecker class is responsible for checking if the current day is
 * the last day of the month, if so, it creates the report for the current
 * month.
 * 
 * @author Dolev Seri
 */
public class EndOfMonthChecker {
	private ServerPortFrameController controller;
	private ScheduledExecutorService executor;

	/**
	 * The constructor for the EndOfMonthChecker class
	 * 
	 * @param controller the controller of the system
	 */
	public EndOfMonthChecker(ServerPortFrameController controller) {
		this.controller = controller;
		executor = Executors.newScheduledThreadPool(1);

	}

	/**
	 * The start method is responsible for scheduling the endOfMonthChecker task, it
	 * will run every day.
	 */
	public void start() {

		Runnable endOfMonthChecker = new Runnable() {
			public void run() {
				Calendar today = Calendar.getInstance();
				int lastDayOfMonth = today.getActualMaximum(Calendar.DAY_OF_MONTH);
				if (today.get(Calendar.DAY_OF_MONTH) == lastDayOfMonth) {
					controller.createReport(true);
				}
			}
		};
		executor.scheduleAtFixedRate(endOfMonthChecker, 0, 1, TimeUnit.DAYS);

	}

	/**
	 * ScheduledExecutorService getter
	 * 
	 * @return executor
	 */
	public ScheduledExecutorService getExecutor() {
		return executor;
	}
}