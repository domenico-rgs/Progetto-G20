package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.cinema.TypeCategory;

public class EditMovie {

	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		String plot = req.getParameter("plot");
		TypeCategory category = TypeCategory.valueOf(req.getParameter("category"));
		String cover;
		
		if (req.getParameter("cover").contentEquals(""))
			cover = "../statics/images/cover/unavaliable.jpg";
		else 
			cover = "../statics/images/cover/" + req.getParameter("cover");


		try {
			Cinema.getCinema().editMovie(title, cover, plot, category);

		}
		catch (Exception e1) {
			System.out.println(e1);
			return "Incorrect or missing data";
		}

		return title + " succefully added. Reload to see changes";
	}

}
