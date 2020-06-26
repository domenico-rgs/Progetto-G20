package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.CinemaFacade;
import server.domain.exception.SearchException;
import server.domain.showing.MovieShowing;

public class GetShowingInf {
	public static String doAction(HttpServletRequest req) {
		String movie = req.getParameter("movie");
		String id = req.getParameter("id");

		try {
			MovieShowing m = CinemaFacade.getCinema().getShowing(movie, id);
			String inf =  m.getTheatreName() + "@" + m.getPrice() +
					"@" + m.getDate();
			return inf;
		}
		catch (SearchException e) {
			return "Error@" + id + " not found. Reload to see changes";
		}
	}
}
