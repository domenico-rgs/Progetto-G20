package server.handler.adminHandler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;

public class DeleteItem {
	
	public static String doAction(HttpServletRequest req) {
		
		switch (req.getParameter("object")) {
		case "movie":
			String title = req.getParameter("title");
			try {
				Cinema.getCinema().deleteMovie(title);
				return title + " successfully deleted";
			} catch (SQLException e) {
				System.out.println(e);
				return title + " not exist";
			}catch (Exception e) {
				System.out.println(e);
				return e.toString();
			}
			
		case "showing":
			String id = req.getParameter("id");
			try {
				Cinema.getCinema().deleteMovieShowing(id);
				return id + " successfully deleted";
			} catch (SQLException e) {
				System.out.println(e);
				return id + " not exist";
			}catch (Exception e) {
				System.out.println(e);
				return e.toString();
			}

		
		}
		return "Error with javascript getRequests";
	}

}
