package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.CinemaFacade;
import server.domain.cinema.TypeCategory;

public class EditMovie {

	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		String plot = req.getParameter("plot");
		TypeCategory category = TypeCategory.valueOf(req.getParameter("category"));
		String cover = "../statics/images/cover/" + req.getParameter("cover") + ".jpg";


		try {
			CinemaFacade.getCinema().getMovie(title).editMovie(cover, plot, category);

		}
		catch (Exception e1) {
			System.out.println(e1);
			return "Incorrect or missing data";
		}

		return title + " succefully added. Reload to see changes";
	}

}
