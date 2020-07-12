package server;

import static java.util.concurrent.TimeUnit.HOURS;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import server.domain.cinema.Cinema;
import server.domain.cinema.TypeCategory;
import server.domain.exception.SeatException;
import server.services.DB.PersistenceFacade;

public class Main {
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void main(String[] argv) throws Exception {
		ApplicationServer server = new ApplicationServer(8080, new CinemaServlet());

		server.start();

		automaticExecution();
	}

	public static void automaticExecution() {
		final Runnable runnab = new Runnable() {
			@Override
			public void run() {
				System.out.println("Deleting old showngs...");
				try {
					PersistenceFacade.getInstance().deleteExpiredShowing(System.currentTimeMillis());
				} catch (SQLException | IOException | SeatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		final ScheduledFuture<?> runnableHandle = scheduler.scheduleAtFixedRate(runnab, 1, 1, HOURS);

	}
}
