package server;

import java.time.LocalDateTime;

import server.domain.cinema.CinemaFacade;

public class Main {
	public static void main(String[] argv) throws Exception {


		ApplicationServer server = new ApplicationServer(8080, new CinemaServlet());
		CinemaFacade.getCinema().createTheatre("theatre1", "fileTest/configTheatre/theatre1");
		CinemaFacade.getCinema().createTheatre("theatre2", "fileTest/configTheatre/theatre2");
		CinemaFacade.getCinema().createTheatre("theatre3", "fileTest/configTheatre/theatre3");
		CinemaFacade.getCinema().createTheatre("theatre4", "fileTest/configTheatre/theatre4");

		CinemaFacade.getCinema().createMovieShowing("Ritorno al futuro", LocalDateTime.of(2020, 8, 27, 18,00), "theatre1", 4.6);
		CinemaFacade.getCinema().createMovieShowing("Ritorno al futuro", LocalDateTime.of(2020, 8, 27, 20,00), "theatre3", 6.6);
		CinemaFacade.getCinema().createMovieShowing("Ritorno al futuro", LocalDateTime.of(2020, 8, 27, 21,00), "theatre2", 5.6);
		server.start();


	}
}
