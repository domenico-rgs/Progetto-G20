package server.handler.adminHandler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.cinema.TypeCategory;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;

public class AddMovie{
	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		String plot = req.getParameter("plot");
		String cover;

		if (req.getParameter("cover").contentEquals(""))
			cover = "../statics/images/cover/unavaliable.jpg";
		else
			cover = "../statics/images/cover/" + req.getParameter("cover");

		try {
			int duration = Integer.valueOf(req.getParameter("duration"));

			Cinema.getCinema().createMovie(title, duration, plot, cover,
					TypeCategory.valueOf(req.getParameter("category")));
		}catch (SearchException e) {
			System.out.println(e);
			return "Error: " + title + " already exists";
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
		return title + " succefully added. Reload to see changes";
	}
}
