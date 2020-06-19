package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;
import server.domain.movie.TypeCategory;

public class Index implements IHandler {
	private List <server.domain.cinema.Movie> movieList = new ArrayList<>();
	private static Index instance = null;

	private Index() {
		//Per test
		//String title, int duration, String plot, String pathCover, TypeCategory category
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

		getMovieList();
	}


	public static Index getInstance() {

		if (instance == null)
			instance = new Index();

		return instance;
	}

	private void getMovieList() {
		HashMap<String, server.domain.cinema.Movie> cinemaMovie = Cinema.getCinema().getMovieCatalog();
		
		for(String m : cinemaMovie.keySet()) {
			movieList.add(cinemaMovie.get(m));
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


		/* Si occupera di prendere 5 titoli a caso e metterli in una lista
		 * da passare all'engine
		 */

		resp.getWriter().write(Rythm.render("index.html", movieList, Cinema.getCinema().getCitation()));


	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/* prende usern e pass, valuta la loro correttezza,
		 * ed in caso positivo ritorna la pagina dell'utente
		 */


		String username = req.getParameter("username");
		String password = req.getParameter("password");

		System.out.println(username + password);

		//fai qualcosa per la verifica
		//poi rendirizza alla pagina corretta

		resp.getWriter().write("FALSE");

		//ritorna la pagina personale dell'utente
		//resp.getWriter().write(Rythm.render("indexUser.html", titoli));


	}

}
