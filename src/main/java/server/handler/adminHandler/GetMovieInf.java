package server.handler.adminHandler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.CinemaFacade;
import server.domain.exception.SeatException;

public class GetMovieInf {

	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		server.domain.cinema.Movie movie;

		try {
			movie = CinemaFacade.getCinema().getMovie(title);
			String inf = movie.getTitle() + "@" + String.valueOf(movie.getDuration()) +
					"@" + movie.getPlot() + "@" + movie.getPathCover() + "@" +
					movie.getCategory().toString();

			return inf;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
