package server.servlet.admin;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.MovieShowingHandler;
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

			//se i valori non sono nulli
			if (movie.contentEquals("") || theatre.contentEquals("") ||
					d[0].contentEquals("") || h[0].contentEquals(""))
				return "Please insert correct data";

			//se la data non è antecedete a now
			if (date.isBefore(LocalDateTime.now()))
				return "Please insert data after now";

			String id;

			try {
				double price = Double.parseDouble(req.getParameter("price"));

				id = MovieShowingHandler.getInstance().createMovieShowing(movie, date, theatre, price);
			}catch (OverlapException e) {
				e.printStackTrace();
				return "The showing overlaps with another";
			}catch (NumberFormatException e){
				e.printStackTrace();
				return "Invalid data: double check the date, price and/or time";
			}catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
			return "Showing succefully added with id: " +id+ ". Reload to see changes";
		}

}
