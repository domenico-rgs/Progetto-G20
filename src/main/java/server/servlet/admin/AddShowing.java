package server.servlet.admin;

import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.MovieShowingHandler;
import server.exception.ObjectNotFoundException;
import server.exception.OverlapException;

public class AddShowing {
	public static String doAction(HttpServletRequest req)  {

		String movie = req.getParameter("movie");
		String theatre = req.getParameter("theatre");

		String[] d = req.getParameter("date").split("-");
		String[] h = req.getParameter("hour").split(":");
		LocalDateTime date ;

		try {
			date = LocalDateTime.of(Integer.parseInt(d[0]),
					Integer.parseInt(d[1]), Integer.parseInt(d[2]), Integer.parseInt(h[0]),Integer.parseInt(h[1]));
		}catch (Exception e) {
			return "Incorrect or missing data";
		}

		//if values not null
		if (movie.contentEquals("") || theatre.contentEquals("") ||
				d[0].contentEquals("") || h[0].contentEquals(""))
			return "Please insert correct data";

		//if the date is not before now
		if (date.isBefore(LocalDateTime.now()))
			return "Please insert data after now";

		try {
			double price = Double.parseDouble(req.getParameter("price"));

			String id = MovieShowingHandler.getInstance().createMovieShowing(movie, date, theatre, price);
			return "Showing succefully added with id: " +id+ ". Reload to see changes";

		}catch (OverlapException e) {
			e.printStackTrace();
			return "The showing overlaps with another";
		}catch (NumberFormatException e){
			return "Invalid data: double check the date, price and/or time";
		} catch (SQLException e) {
			return "Something went wrong with the database, we apologise for the inconvenience";
		}catch (ObjectNotFoundException e) {
			return "Movie or theatre not found";
		}catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong";
		}
	}
}
