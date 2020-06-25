package server.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;
import server.domain.showing.MovieShowing;

public class Movie implements IHandler {

	private static Movie instance = null;

	private Movie() {}

	public static Movie getInstance() {
		if (instance == null)
			instance = new Movie();

		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		server.domain.cinema.Movie movie = Cinema.getCinema().searchMovie(req.getParameter("title"));
		List<MovieShowing> showings = Cinema.getCinema().getShowingList(movie);

		resp.getWriter().write(Rythm.render("movieInformation.html", movie, showings));
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}