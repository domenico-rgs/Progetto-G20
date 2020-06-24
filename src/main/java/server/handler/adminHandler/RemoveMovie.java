package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;

public class RemoveMovie {
	
	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");

		try {
			Cinema.getCinema().deleteMovie(title);
		}
		catch (SearchException e) {
			return title + " not found. Reload to see changes";
		}

		return title + " succefully removed. Reload to see changes";
	}

}
