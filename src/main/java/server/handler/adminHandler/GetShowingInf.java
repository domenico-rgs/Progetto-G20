package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;
import server.domain.showing.MovieShowing;

public class GetShowingInf {
	public static String doAction(HttpServletRequest req) {
		String movie = req.getParameter("movie");
		String id = req.getParameter("id");

		MovieShowing m = Cinema.getCinema().searchShowing(id);
		String inf =  m.getTheatreName() + "@" + m.getPrice() +
				"@" + m.getDate();
		return inf;
	}
}
