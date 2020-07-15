package server.handler.adminHandler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;

/**his class together with others serve as a control on the
 * admin page, precisely this deals with connecting the web interface
 * to logic and transmitting information to eliminate a film or showing*/

public class DeleteItem {

	public static String doAction(HttpServletRequest req) {

		switch (req.getParameter("object")) {
		case "movie":
			String title = req.getParameter("title");
			if (title == null || title.contentEquals(""))
				return "Please enter a valid title";
			try {
				Cinema.getCinema().deleteMovie(title);
				return title + " successfully deleted";
			} catch (SQLException e) {
				System.out.println(e);
				return title + " not exists";
			}catch (Exception e) {
				System.out.println(e);
				return e.toString();
			}

		case "showing":
			String id = req.getParameter("id");
			if (id == null || id.contentEquals(""))
				return "Please enter valid id";
			try {
				Cinema.getCinema().deleteMovieShowing(id);
				return id + " successfully deleted";
			} catch (SQLException e) {
				System.out.println(e);
				return id + " not exists";
			}catch (Exception e) {
				e.getStackTrace();
			}

		case "theatre":
			String name = req.getParameter("name");
			if (name == null || name.contentEquals(""))
				return "Please enter valid name";

			try {
				Cinema.getCinema().deleteTheatre(name);
				return name + " successfully removed. Recharge to see changes";
			}
			catch (SQLException e) {
				System.out.println(e.toString());
				return name + " not exists yet";
			} catch (Exception e) {
				System.out.println(e);
				return "impossible to remove " + name;
			}
		}
		return "Error with javascript getRequests";
	}
}
