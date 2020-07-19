package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.MovieShowingHandler;
import server.exception.ObjectNotFoundException;

public class DeleteShowing {

	public static String doAction(HttpServletRequest req) {

		String id = req.getParameter("id");
		if (id == null || id.contentEquals(""))
			return "Please enter valid id";
		try {
			MovieShowingHandler.getInstance().deleteMovieShowing(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return id + " not exists";
		}catch (Exception e) {
			return e.getMessage();
		}
		return id + " successfully deleted";
	}

}
