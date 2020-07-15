package server.handler.adminHandler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.cinema.TypeCategory;
import server.domain.exception.SearchException;

public class EditItem {

	public static String doAction(HttpServletRequest req) {

		switch (req.getParameter("object")) {

		case "movie":
			String title = req.getParameter("title");
			String plot = req.getParameter("plot");
			TypeCategory category = TypeCategory.valueOf(req.getParameter("category"));
			String cover;

			if (req.getParameter("cover").contentEquals("")) {
				cover = "../statics/images/cover/unavaliable.jpg";
			} else {
				cover = req.getParameter("cover");
			}

			try {
				Cinema.getCinema().editMovie(title, cover, plot, category);

			}catch (SQLException e) {
				System.out.println(e);
				return "Impossible found the movie " + title;
			}catch (NumberFormatException e) {
				System.out.println(e);
				return "Incorrect value for duration";
			}catch (SearchException e) {
				return title + " is used!";
			}catch (Exception e) {
				System.out.println(e);
				return e.getMessage();
			}
			return title + " succefully changed. Reload to see changes";
		}


		return "Error with javascript scripts";
	}
}


