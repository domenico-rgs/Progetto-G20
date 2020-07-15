package server;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import server.domain.exception.SeatException;
import server.services.DB.PersistenceFacade;

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

	private static void autoShowDelete() {
		final Runnable runnab = new Runnable() {
			@Override
			public void run() {
				System.out.println("Deleting old showngs...");
				try {
					PersistenceFacade.getInstance().deleteExpiredShowing(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
				} catch (SQLException | IOException | SeatException e) {
					e.printStackTrace();
				}
			}
		};
		@SuppressWarnings("unused")
		final ScheduledFuture<?> runnableHandle = scheduler.scheduleAtFixedRate(runnab, 1, 1, java.util.concurrent.TimeUnit.HOURS);

	}
}
