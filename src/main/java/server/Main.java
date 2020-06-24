package server;

import java.util.Date;

import server.domain.cinema.Cinema;
import server.domain.cinema.TypeCategory;
import server.domain.exception.SearchException;

public class Main {
	public static void main(String[] argv) throws Exception {


		ApplicationServer server = new ApplicationServer(8080, new CinemaServlet());
		System.out.println(Cinema.getCinema().getTitleMovieList());
		Cinema.getCinema().createMovie("Armageddon", 120, "Bruce Willis", "../statics/images/cover/armageddon.jpg", TypeCategory.ACTION);
		Cinema.getCinema().createMovie("Interstellar", 120, "Bellissimo", "../statics/images/cover/interstellar.jpg", TypeCategory.FANTASY);
		Cinema.getCinema().createMovie("Indiana Jones", 120, "Indy", "../statics/images/cover/indiana.jpg", TypeCategory.ADVENTURE);
		Cinema.getCinema().createMovie("Pulp Fiction", 120, "PF", "../statics/images/cover/pulp.jpg", TypeCategory.ROMANCE);
		Cinema.getCinema().createMovie("Ritorno al futuro", 120, "RF", "../statics/images/cover/futuro.jpg", TypeCategory.FANTASY);
		Cinema.getCinema().createMovie("Jurassic Park", 150, "Jurassico", "../statics/images/cover/jurassic.jpg", TypeCategory.ADVENTURE);
		Cinema.getCinema().createMovie("The Wolf of Wall street", 180, "Di Caprio docet", "../statics/images/cover/wolf.jpg", TypeCategory.ACTION);
		Cinema.getCinema().createMovie("Hugo Cabret", 80, "Time is money", "../statics/images/cover/hugo.jpg", TypeCategory.FANTASY);

		Cinema.getCinema().createTheatre("theatre1", "fileTest/configTheatre/theatre1");
		Cinema.getCinema().createTheatre("theatre2", "fileTest/configTheatre/theatre2");
		Cinema.getCinema().createTheatre("theatre3", "fileTest/configTheatre/theatre3");
		Cinema.getCinema().createTheatre("theatre4", "fileTest/configTheatre/theatre4");
		
		Cinema.getCinema().createMovieShowing("Ritorno al futuro", new Date(120, 7, 27, 18,00), "theatre1", 4.6);
		Cinema.getCinema().createMovieShowing("Ritorno al futuro", new Date(120, 7, 28, 20,00), "theatre3", 6.6);
		Cinema.getCinema().createMovieShowing("Ritorno al futuro", new Date(120, 7, 29, 21,00), "theatre2", 5.6);
		server.start();


	}
}
