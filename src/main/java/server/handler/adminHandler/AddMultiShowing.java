package server.handler.adminHandler;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;

/** this class together with others serve as a control on the admin page, precisely this deals
 *  with connecting the web interface to logic and transmitting information to create a multishowing*/

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
		int duration;
		
		if (movie == null || theatre == null || price == null) {
			return "please insert correct data";
		}
		
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
	
		if (finalD.isBefore(startD)) {
			return "Date and/or hour not correct";
		}
		
		
		//prendo la durata del film
		try {
			duration = Cinema.getCinema().getMovie(movie).getDuration();
		} catch (Exception e) {
			return e.getMessage();
		}
		
		LocalDateTime date;
		//splitto per tempo
		for (date = startD; date.isBefore(finalD.plusDays(1)); date = date.plusDays(1)) {
		
			try {
				Cinema.getCinema().createMovieShowing(movie, date, theatre, Double.valueOf(price));
			}catch (SearchException e) {
				return "Some showings Overlaps with anothers";
			}catch (NullPointerException e) {
				return "Please enter correct data";
			}
			catch (Exception e) {
				e.toString();
				return "Errore creazione proiezioni";
			} 
		}
		
		return "Showings created";

	}

}
