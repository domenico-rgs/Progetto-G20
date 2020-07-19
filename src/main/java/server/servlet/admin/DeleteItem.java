package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.MovieHandler;
import server.domain.controller.MovieShowingHandler;
import server.domain.controller.TheatreHandler;



/**This class together with others serve as a control on the
 * admin page, precisely this deals with connecting the web interface
 * to logic and transmitting information to eliminate a film or showing*/

public class DeleteItem {

	public static String doAction(HttpServletRequest req) {

		TypeItem type = TypeItem.valueOf(req.getParameter("object").toUpperCase());

		switch (type) {
		case MOVIE:
			String title = req.getParameter("title");
			if (title == null || title.contentEquals(""))
				return "Please enter a valid title";
			try {
				MovieHandler.getInstance().deleteMovie(title);
			} catch (SQLException e) {
				e.printStackTrace();
				return title + " not exists";
			}catch (Exception e) {
				System.out.println(e);
				return e.getMessage();
			}
			return title + " successfully deleted";

		case SHOWING:
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

		case THEATRE:
			String name = req.getParameter("name");
			if (name == null || name.contentEquals(""))
				return "Please enter valid name";

			try {
				TheatreHandler.getInstance().deleteTheatre(name);
			}
			catch (SQLException e) {
				e.printStackTrace();
				return name + " not exists yet";
			} catch (Exception e) {
				e.printStackTrace();
				return "impossible to remove " + name;
			}
			return name + " successfully removed. Recharge to see changes";
		}
		return "Error with javascript getRequests";
	}
}
