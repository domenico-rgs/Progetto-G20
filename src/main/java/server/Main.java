package server;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import server.services.persistence.PersistenceFacade;
import server.servlet.CinemaServlet;

public class Main {
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void main(String[] argv) throws Exception {
		int portNumber = 8080;
		if(argv.length !=0) {
			portNumber = Integer.parseInt(argv[0]);
		}

		ApplicationServer server = new ApplicationServer(portNumber, new CinemaServlet());

		PersistenceFacade.getInstance();
		autoShowDelete();

		server.start();
	}

	/**
	 * Method that starts a thread for the automatic elimination of showings whose date is earlier than the current system date
	 * Repeated every hour
	 */
	private static void autoShowDelete() {
		final Runnable runnab = new Runnable() {
			@Override
			public void run() {
				System.out.println("Deleting old showngs...");
				try {
					PersistenceFacade.getInstance().deleteExpiredShowing(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		@SuppressWarnings("unused")
		final ScheduledFuture<?> runnableHandle = scheduler.scheduleAtFixedRate(runnab, 1, 1, java.util.concurrent.TimeUnit.HOURS);
	}
}
