package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.cinema.TypeCategory;
import server.domain.exception.SearchException;

public class AddMovie{

	//idee migliori?
	public static String doAction(HttpServletRequest req) {

		String title = req.getParameter("title");
		String plot = req.getParameter("plot");
		String cover = "../statics/images/cover/" + req.getParameter("cover");


		try {
			int duration = Integer.valueOf(req.getParameter("duration"));

			Cinema.getCinema().createMovie(title, duration, plot, cover,
					TypeCategory.valueOf(req.getParameter("category")));
		}
		catch (SearchException e) {
			System.out.println(e);
			return "Error: " + title + " already exists";
		}
		catch (Exception e) {
			System.out.println(e);
			return "Incorrect or missing data";
		}

		return title + " succefully added. Reload to see changes";

	}

}
