package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.MovieHandler;

public class DeleteMovie {

	public static String doAction(HttpServletRequest req) {

		String title = req.getParameter("title");
		if (title == null || title.contentEquals(""))
			return "Please enter a valid title";
		try {
			MovieHandler.getInstance().deleteMovie(title);
		} catch (SQLException e) {
			return title + " not exists";
		}
		catch (Exception e) {
			System.out.println(e);
			return e.getMessage();
		}
		return title + " successfully deleted";
	}

}
