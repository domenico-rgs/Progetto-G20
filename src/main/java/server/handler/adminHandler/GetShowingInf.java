package server.handler.adminHandler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SeatException;
import server.domain.showing.MovieShowing;

public class GetShowingInf {
	public static String doAction(HttpServletRequest req) {
		String movie = req.getParameter("movie");
		String id = req.getParameter("id");

		try {
			MovieShowing m = Cinema.getCinema().getMovieShowing(id);
			String inf =  m.getTheatreName() + "@" + m.getPrice() +
					"@" + m.getDate();
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
