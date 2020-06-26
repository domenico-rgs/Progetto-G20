package server.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.CinemaFacade;
import server.domain.exception.SearchException;
import server.domain.showing.MovieShowing;

public class Movie implements IHandler {

	private static Movie instance = null;
	private final String coverPath = "../statics/images/cover/";

	private Movie() {
	}


	public static Movie getInstance() {
		if (instance == null)
			instance = new Movie();

		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		try {
			server.domain.cinema.Movie movie = CinemaFacade.getCinema().searchMovie(req.getParameter("title"));
			List<MovieShowing> showings = CinemaFacade.getCinema().getSchedule(movie);

			resp.getWriter().write(Rythm.render("movieInformation.html", movie, showings));

		}
		//se non trova il film
		catch (SearchException e) {
			resp.getWriter().write(Rythm.render("404.html"));
		}


	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}


