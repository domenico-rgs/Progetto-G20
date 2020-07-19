package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.TypeCategory;
import server.domain.controller.MovieHandler;
import server.exception.SearchException;

public class AddMovie {

	public static String doAction(HttpServletRequest req) {

		String title = req.getParameter("title");
		String plot = req.getParameter("plot");
		String cover;

		if (title.contentEquals(""))
			return "Please insert a value for title";

		if (req.getParameter("cover").contentEquals("")) {
			cover = "../statics/images/cover/unavaliable.jpg";
		} else {
			cover = "../statics/images/cover/" + req.getParameter("cover");
		}

		try {
			int duration = Integer.valueOf(req.getParameter("duration"));

			MovieHandler.getInstance().createMovie(title, duration, plot, cover,
					TypeCategory.valueOf(req.getParameter("category")));
		}catch (SearchException e) {
			return "Error: " + title + " already exists";
		}catch (SQLException e) {
			return title+ " already exists or problem with database";
		}catch (NumberFormatException e) {
			return "Incorrect value for duration";
		}catch (IllegalArgumentException e) {
			return "The category was not chosen or something else went wrong";
		}catch (Exception e) {
			e.getStackTrace();
			return e.getMessage();
		}
		return title + " succefully added. Reload to see changes";
	}

}
