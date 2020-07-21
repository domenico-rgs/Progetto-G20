package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.MovieShowingHandler;
import server.exception.SearchException;

public class DeleteShowing {

	public static String doAction(HttpServletRequest req) {

		String id = req.getParameter("id");
		if (id == null || id.contentEquals(""))
			return "Please enter valid id";

		try {
			MovieShowingHandler.getInstance().deleteMovieShowing(id);
			return id + " successfully deleted";

		} catch (SQLException e) {
			return id + " not exists";
		} catch (SearchException e) {
			return "Cannot remove the showing because there are some tickets sold";
		}catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong";
		}
	}

}
