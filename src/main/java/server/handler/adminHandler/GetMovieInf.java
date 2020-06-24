package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;

public class GetMovieInf {
	
	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		server.domain.cinema.Movie movie;

		try {
			movie = Cinema.getCinema().searchMovie(title);
			String inf = movie.getTitle() + "@" + String.valueOf(movie.getDuration()) +
					"@" + movie.getPlot();

			return inf;
		}
		catch (SearchException e) {
			return "Error@" + title + " not found. Reload to see changes";
		}
	}

}
