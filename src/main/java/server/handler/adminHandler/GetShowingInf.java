package server.handler.adminHandler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.cinema.MovieShowing;
import server.domain.exception.SeatException;

/**this class gives me the information of the showing*/

public class GetShowingInf {
	public static String doAction(HttpServletRequest req) throws IOException, SeatException, SQLException {
		switch (req.getParameter("action")) {
		case "getID":
			String title = req.getParameter("title");
			String message = "";

			for (MovieShowing sh : Cinema.getCinema().getMovieShowingList(title) ) {
				message += sh.getId() + "@";
			}

			return message;

		case "getINF":
			String id = req.getParameter("id");
			if (id == null) return "";
			try {
				MovieShowing m = Cinema.getCinema().getMovieShowing(id);
				String inf =  m.getTheatreName() + "@" + m.getPrice() +
						"@" + m.getDateToString();
				return inf;
			}catch (Exception e) {
				e.printStackTrace();
				return "Error@" +e.toString();
			}
		}
		return "problem with javascript getRequest";
	}
}
