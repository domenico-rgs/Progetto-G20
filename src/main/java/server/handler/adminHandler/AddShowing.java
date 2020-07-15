package server.handler.adminHandler;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.OverlapException;


/** this class together with others serve as a control on the admin page, precisely this deals
 *  with connecting the web interface to logic and transmitting information to create a showing*/
public class AddShowing {

	public static String doAction(HttpServletRequest req) {
		String movie = req.getParameter("movie");
		String theatre = req.getParameter("theatre");

		String[] d = req.getParameter("date").split("-");
		String[] h = req.getParameter("hour").split(":");

		//se i valori non sono nulli
		if (movie.contentEquals("") || theatre.contentEquals("") ||
				d[0].contentEquals("") || h[0].contentEquals(""))
			return "Please insert correct data";

		String id;

		try {
			double price = Double.parseDouble(req.getParameter("price"));

			id = Cinema.getCinema().createMovieShowing(movie, LocalDateTime.of(Integer.parseInt(d[0]),
					Integer.parseInt(d[1]), Integer.parseInt(d[2]), Integer.parseInt(h[0]),Integer.parseInt(h[1])), theatre, price);
		}catch (OverlapException e) {
			System.out.println(e);
			return "The showing overlaps with another";
		}catch (NumberFormatException e){
			System.out.println(e);
			return "Invalid data: double check the date, price and/or time";
		}catch (Exception e) {
			System.out.println(e);
			return e.toString();
		}
		return "Showing succefully added with id: " +id+ ". Reload to see changes";
	}
}
