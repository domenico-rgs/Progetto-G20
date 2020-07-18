package server.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.cinema.MovieShowing;
import server.exception.ObjectNotFoundException;

/**this class is a controller that manages the movie page*/

public class Movie implements IHandler {
	private static Movie instance = null;

	private Movie() {}


	//*singleton*/
	public static Movie getInstance() {
		if (instance == null) {
			instance = new Movie();
		}

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			server.domain.cinema.Movie movie = Cinema.getCinema().getMovie(req.getParameter("title"));
			List<MovieShowing> showingsForMovie = Cinema.getCinema().getMovieShowingList(movie.getTitle());
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