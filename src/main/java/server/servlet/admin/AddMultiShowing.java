package server.servlet.admin;



import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;


import server.domain.controller.MovieShowingHandler;

import server.exception.OverlapException;
import server.exception.SearchException;



public class AddMultiShowing {

	public static String doAction(HttpServletRequest req) {

			String movie = req.getParameter("movie");
			String theatre = req.getParameter("theatre");
			String price = req.getParameter("price");
			String [] dateStart = req.getParameter("dateStart").split("-");
			String [] dateFinal = req.getParameter("dateFinal").split("-");
			String []hour = req.getParameter("hour").split(":");
			LocalDateTime startD;
			LocalDateTime finalD;
			

			if (movie == null || theatre == null || price == null)
				return "Please insert correct data";


			try {
				startD = LocalDateTime.of(Integer.valueOf(dateStart[0]), Integer.valueOf(dateStart[1]),
						Integer.valueOf(dateStart[2]), Integer.valueOf(hour[0]),
						Integer.valueOf(hour[1]));

				finalD = LocalDateTime.of(Integer.valueOf(dateFinal[0]), Integer.valueOf(dateFinal[1]),
						Integer.valueOf(dateFinal[2]), Integer.valueOf(hour[0]),
						Integer.valueOf(hour[1]));
			}catch (Exception e) {
				return "Data and/or hour not correct";
			}

			if (startD.isBefore(LocalDateTime.now()))
				return "Please insert data after now";

			if (finalD.isBefore(startD))
				return "Date and/or hour not correct";

			//splitto per tempo
			for (LocalDateTime date = startD; date.isBefore(finalD.plusDays(1)); date = date.plusDays(1)) {

				try {
					MovieShowingHandler.getInstance().createMovieShowing(movie, date, theatre, Double.valueOf(price));
				}catch (SearchException e) {
					return "Some showings Overlaps with anothers";
				}catch (NullPointerException e) {
					return "Please enter correct data";
				}catch (OverlapException e) {
					return "Some showings Overlaps with anothers";
				}
				catch (Exception e) {
					e.toString();
					return "General error occured. Please try again";
				}
			}
			return "Showings created";

	}

}
