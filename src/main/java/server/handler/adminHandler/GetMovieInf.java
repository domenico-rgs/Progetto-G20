package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;

public class GetMovieInf {

	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		server.domain.cinema.Movie movie;

		movie = Cinema.getCinema().searchMovie(title);
		String inf = movie.getTitle() + "@" + String.valueOf(movie.getDuration()) +
				"@" + movie.getPlot() + "@" + movie.getPathCover() + "@" +
				movie.getCategory().toString();

		return inf;
	}

}
