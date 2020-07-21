package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.TypeCategory;
import server.domain.controller.MovieHandler;
import server.exception.ObjectNotFoundException;
import server.exception.SearchException;

public class EditMovie {

	public static String doAction(HttpServletRequest req) {


		String title = req.getParameter("title");
		String plot = req.getParameter("plot");
		TypeCategory category = TypeCategory.valueOf(req.getParameter("category"));
		String cover;

		if (req.getParameter("cover").contentEquals("")) {
			cover = "../statics/images/cover/unavaliable.jpg";
		} else {
			cover = req.getParameter("cover");
		}

		try {
			MovieHandler.getInstance().editMovie(title, cover, plot, category);
			return title + " succefully changed. Reload to see changes";

		}catch (SQLException e) {
			return "Impossible found the movie " + title;
		}catch (NumberFormatException e) {
			return "Incorrect value for duration";
		}catch (SearchException e) {
			return title + " is used!";
		} catch (ObjectNotFoundException e) {
			return "Movie not found";
		}catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong";
		}
	}
}


