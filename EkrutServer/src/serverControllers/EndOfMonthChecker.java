package serverControllers;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EndOfMonthChecker {
	private ServerPortFrameController controller;
	private ScheduledExecutorService executor;

	public EndOfMonthChecker(ServerPortFrameController controller) {
		this.controller = controller;
		executor = Executors.newScheduledThreadPool(1);

	}

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

	public ScheduledExecutorService getExecutor() {
		return executor;
	}
}