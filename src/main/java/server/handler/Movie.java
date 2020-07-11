package server.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.SeatException;
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
		try {
			server.domain.cinema.Movie movie = Cinema.getCinema().getMovie(req.getParameter("title"));
			List<MovieShowing> showingsForMovie = Cinema.getCinema().getMovieShowingList(movie.getTitle());
			resp.getWriter().write(Rythm.render("movieInformation.html", movie, showingsForMovie));

		} catch (SQLException | IOException | SeatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}