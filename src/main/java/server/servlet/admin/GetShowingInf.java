package server.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.MovieShowing;
import server.domain.controller.MovieShowingHandler;
import server.exception.SeatException;

public class GetShowingInf {
	public static String doAction(HttpServletRequest req) throws IOException, SeatException, SQLException {
		switch (req.getParameter("action")) {
		case "getID":
			String title = req.getParameter("title");
			String message = "";

			if(title == null || title.contentEquals("")) return "";

			for (MovieShowing sh : MovieShowingHandler.getInstance().getMovieShowingList(title) ) {
				message += sh.getId() + "@";
			}

			return message;

		case "getINF":
			String id = req.getParameter("id");
			if (id == null || id.contentEquals("")) return "";
			try {
				MovieShowing m = MovieShowingHandler.getInstance().getMovieShowing(id);
				String inf =  m.getTheatreName() + "@" + m.getPrice() +
						"@" + m.getDateToString();
				return inf;
			}catch (Exception e) {
				return "Error@" +"Get information for " + id;
			}
		}
		return "There was a problem with javascript getRequest";
	}
}
