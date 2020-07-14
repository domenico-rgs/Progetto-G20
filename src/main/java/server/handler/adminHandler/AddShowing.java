package server.handler.adminHandler;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.OverlapException;

public class AddShowing {

	public static String doAction(HttpServletRequest req) {
		String movie = req.getParameter("movie");
		String theatre = req.getParameter("theatre");

		String[] d = req.getParameter("date").split("/");
		String[] h = req.getParameter("hour").split(":");

		String id;

		try {
			double price = Double.parseDouble(req.getParameter("price"));

			id = Cinema.getCinema().createMovieShowing(movie, LocalDateTime.of(Integer.parseInt(d[2]),
					Integer.parseInt(d[1]), Integer.parseInt(d[0]), Integer.parseInt(h[0]),Integer.parseInt(h[1])), theatre, price);
		}catch (OverlapException e) {
			e.printStackTrace();
			return "The showing overlaps with another";
		}catch (Exception e){
			e.printStackTrace();
			return "Incorrect or missing data";
		}
		return "Showing succefully added with id: " +id+ ". Reload to see changes";
	}
}
