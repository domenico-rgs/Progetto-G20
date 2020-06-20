package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;
import server.domain.showing.MovieShowing;

public class Movie implements IHandler {

	private static Movie instance = null;

	private Movie() {
	}


	public static Movie getInstance() {
		if (instance == null)
			instance = new Movie();

		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/* verrà salvato il titolo del fiml da cercare, da passarre
		 * all'applicazione che lo cercherà nella base di dati, e ne ritornerà
		 * le informazioni, da renderizzare nell'html
		 */
		try {
			server.domain.cinema.Movie movie =
					Cinema.getCinema().searchMovie(req.getParameter("title"));

			ArrayList<MovieShowing> showings = new ArrayList<>();
			HashMap<String, MovieShowing> map = Cinema.getCinema().getSchedule(movie);
			for(String s : map.keySet())
				showings.add(map.get(s));
			resp.getWriter().write(Rythm.render("movieInformation.html", movie, showings));

		} catch (SearchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}


