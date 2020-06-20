package server;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;
import server.domain.movie.TypeCategory;

public class Main {
	public static void main(String[] argv) throws Exception {


		ApplicationServer server = new ApplicationServer(8080, new CinemaServlet());
		try {
			Cinema.getCinema().createMovie("Armageddon", 120, "Bruce Willis", "../statics/images/cover/armageddon.jpg", TypeCategory.ACTION);
			Cinema.getCinema().createMovie("Interstellar", 120, "Bellissimo", "../statics/images/cover/interstellar.jpg", TypeCategory.FANTASY);
			Cinema.getCinema().createMovie("Indiana Jones", 120, "Indy", "../statics/images/cover/indiana.jpg", TypeCategory.ADVENTURE);
			Cinema.getCinema().createMovie("Pulp Fiction", 120, "PF", "../statics/images/cover/pulp.jpg", TypeCategory.ROMANCE);
			Cinema.getCinema().createMovie("Ritorno al futuro", 120, "RF", "../statics/images/cover/futuro.jpg", TypeCategory.FANTASY);

		} catch (SearchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.start();


	}
}
