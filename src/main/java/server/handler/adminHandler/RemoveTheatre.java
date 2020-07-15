package server.handler.adminHandler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;


/**this class has the task of eliminating a theater by receiving the parameters from the html page*/

public class RemoveTheatre {

	public static String doAction(HttpServletRequest req) {

		String name = req.getParameter("name");

		try {
			Cinema.getCinema().deleteTheatre(name);
			return name + " successfully removed. Recharge to see changes";
		}
		catch (SQLException e) {
			System.out.println(e.toString());
			return name + " not exist yet";
		} catch (Exception e) {
			System.out.println(e.toString());
			return e.toString();
		}
	}

}
