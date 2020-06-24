package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;

public class EditMovie {
	
	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		String plot = req.getParameter("plot");
		String cover = "../statics/images/cover/" + req.getParameter("cover") + ".jpg";


		try {
			int duration = Integer.valueOf(req.getParameter("duration"));

			//Cinema.getCinema().searchMovie(title).setCategory(TypeCategory.valueOf(req.getParameter("category")));
			Cinema.getCinema().searchMovie(title).setDuration(duration);
			Cinema.getCinema().searchMovie(title).setPathCover(cover);
			Cinema.getCinema().searchMovie(title).setPlot(plot);
			Cinema.getCinema().searchMovie(title).setTitle(title);

		}
		catch (SearchException e1) {
			System.out.println(e1);
			return "Error: " + title + " already exists";
		}
		catch (Exception e1) {
			System.out.println(e1);
			return "Incorrect or missing data";
		}

		return title + " succefully added. Reload to see changes";
	}

}
