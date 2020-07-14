package server.handler.adminHandler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SeatException;

public class GetMovieInf {

	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		server.domain.cinema.Movie movie;
		try {
			movie = Cinema.getCinema().getMovie(title);
			String inf = movie.getTitle() + "@" + String.valueOf(movie.getDuration()) +
					"@" + movie.getPlot() + "@" + movie.getPathCover() + "@" +
					movie.getCategory().toString();

			return inf;
		}catch (Exception e) {
			System.out.println(e);
			return "Error@"+ e.toString();
		}
	}
}
