package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.MovieHandler;
import server.exception.SearchException;

public class DeleteMovie {

	public static String doAction(HttpServletRequest req) {

		String title = req.getParameter("title");
		if (title == null || title.contentEquals(""))
			return "Please enter a valid title";

		try {
			MovieHandler.getInstance().deleteMovie(title);
			return title + " successfully deleted";

		} catch (SQLException e) {
			return title + " not exists";
		} catch (SearchException e) {
			return "Cannot remove the movie because it's used somewhere";
		}catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong";
		}
	}

}
