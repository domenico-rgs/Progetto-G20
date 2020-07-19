package server.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.MovieShowing;
import server.domain.controller.MovieHandler;
import server.domain.controller.MovieShowingHandler;
import server.exception.ObjectNotFoundException;

/**
 * This class is a controller that manages the movie page
 *
 * Singleton class (State pattern)
 */
public class Movie implements IHandlerState {
	private static Movie instance = null;

	private Movie() {}

	public static Movie getInstance() {
		if (instance == null) {
			instance = new Movie();
		}

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			server.domain.cinema.Movie movie = MovieHandler.getInstance().getMovie(req.getParameter("title"));
			List<MovieShowing> showingsForMovie = MovieShowingHandler.getInstance().getMovieShowingList(movie.getTitle());
			resp.getWriter().write(Rythm.render("movieInformation.html", movie, showingsForMovie));

		} catch (SQLException | IOException | ObjectNotFoundException e) {
			resp.getWriter().write(Rythm.render("404.html"));
			e.toString();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}