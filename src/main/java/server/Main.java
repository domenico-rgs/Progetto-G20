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

//		Cinema.getCinema().createMovie("Armageddon", 120, "Bruce Willis", "../statics/images/cover/armageddon.jpg", TypeCategory.ACTION);
//		Cinema.getCinema().createMovie("Interstellar", 120, "Bellissimo", "../statics/images/cover/interstellar.jpg", TypeCategory.FANTASY);
//		Cinema.getCinema().createMovie("Indiana Jones", 120, "Indy", "../statics/images/cover/indiana.jpg", TypeCategory.ADVENTURE);
//		Cinema.getCinema().createMovie("Pulp Fiction", 120, "PF", "../statics/images/cover/pulp.jpg", TypeCategory.ROMANCE);
//		Cinema.getCinema().createMovie("Ritorno al futuro", 120, "RF", "../statics/images/cover/futuro.jpg", TypeCategory.FANTASY);
//		Cinema.getCinema().createMovie("Jurassic Park", 150, "Jurassico", "../statics/images/cover/jurassic.jpg", TypeCategory.ADVENTURE);
//		Cinema.getCinema().createMovie("The Wolf of Wall street", 180, "Di Caprio docet", "../statics/images/cover/wolf.jpg", TypeCategory.ACTION);
//		Cinema.getCinema().createMovie("Hugo Cabret", 80, "Time is money", "../statics/images/cover/hugo.jpg", TypeCategory.FANTASY);
//
//		Cinema.getCinema().createTheatre("theatre1", "X X X X\nP X D X\nD D P P");
//		Cinema.getCinema().createTheatre("theatre2", "X X X X\nP X D X\nD D P P");
//		Cinema.getCinema().createTheatre("theatre3", "X X X X\nP X D X\nD D P P");
//		Cinema.getCinema().createTheatre("theatre4", "X X X X\nP X D X\nD D P P");
//
//		Cinema.getCinema().createMovieShowing("Ritorno al futuro", LocalDateTime.of(2020, 8, 27, 18,00), "theatre1", 4.6);
//		Cinema.getCinema().createMovieShowing("Ritorno al futuro", LocalDateTime.of(2020, 8, 27, 20,00), "theatre3", 6.6);
//		Cinema.getCinema().createMovieShowing("Ritorno al futuro", LocalDateTime.of(2020, 8, 27, 21,00), "theatre2", 5.6);
		
		automaticExecution();
		server.start();
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
