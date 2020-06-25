package server.handler.adminHandler;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;

public class AddShowing {

	public static String doAction(HttpServletRequest req) {

		String movie = req.getParameter("movie");
		String theatre = req.getParameter("theatre");


		String[] d = req.getParameter("date").split("/"); //lanciare eccezioni
		String[] h = req.getParameter("hour").split(":");

		try {
			double price = Double.parseDouble(req.getParameter("price"));

			Cinema.getCinema().createMovieShowing(movie, LocalDateTime.of(Integer.parseInt(d[2]),
					Integer.parseInt(d[1]), Integer.parseInt(d[0]), Integer.parseInt(h[0]),Integer.parseInt(h[1])), theatre, price);
		}
		catch (Exception e){
			System.out.println(e);
			return "Incorrect or missing data";
		}


		return "Showing succefully added. Reload to see changes";

	}
}
