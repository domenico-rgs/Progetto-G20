package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;

public class EditShowing {
	public static String doAction(HttpServletRequest req) {
		String id = req.getParameter("id");
		String movie = req.getParameter("movie");
		String theatre = req.getParameter("theatre");
		
		try {
			double price = Double.parseDouble(req.getParameter("price"));

			Cinema.getCinema().editShowing(movie, id, theatre, price);

		}
		catch (SearchException e1) {
			System.out.println(e1);
			return "Error: showing " + id + " already exists";
		}
		catch (Exception e1) {
			System.out.println(e1);
			return "Incorrect or missing data";
		}

		return "Showing " + id + " succefully added. Reload to see changes";
	}

}
