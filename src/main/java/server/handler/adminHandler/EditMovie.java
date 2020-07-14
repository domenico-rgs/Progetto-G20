package server.handler.adminHandler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.cinema.TypeCategory;
import server.domain.exception.SearchException;

public class EditMovie {

	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		String plot = req.getParameter("plot");
		TypeCategory category = TypeCategory.valueOf(req.getParameter("category"));
		String cover;

		if (req.getParameter("cover").contentEquals(""))
			cover = "../statics/images/cover/unavaliable.jpg";
		else
			cover = "../statics/images/cover/" + req.getParameter("cover");

		try {
			Cinema.getCinema().editMovie(title, cover, plot, category);

		}catch (IOException e) {
			System.out.println(e);
			return "Problem with I/O operation. Please try again.";
		}catch (SQLException e) {
			System.out.println(e);
			return e.toString();
		}catch (NumberFormatException e) {
			System.out.println(e);
			return "Incorrect value for duration";
		}catch (Exception e) {
			System.out.println(e);
			return e.toString();
		}
		return title + " succefully changed. Reload to see changes";
	}
}