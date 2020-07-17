package server.servlet.admin;

import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.cinema.TypeCategory;
import server.exception.OverlapException;
import server.exception.SearchException;
import server.exception.SeatException;

public class AddItem {

	public static String doAction(HttpServletRequest req) {

		switch (req.getParameter("object")) {

		case "movie":
			String title = req.getParameter("title");
			String plot = req.getParameter("plot");
			String cover;

			if (title.contentEquals(""))
				return "Please insert a value for title";

			if (req.getParameter("cover").contentEquals("")) {
				cover = "../statics/images/cover/unavaliable.jpg";
			} else {
				cover = "../statics/images/cover/" + req.getParameter("cover");
			}

			try {
				int duration = Integer.valueOf(req.getParameter("duration"));

				Cinema.getCinema().createMovie(title, duration, plot, cover,
						TypeCategory.valueOf(req.getParameter("category")));
			}catch (SearchException e) {
				return "Error: " + title + " already exists";
			}catch (SQLException e) {
				return title+ " already exists or problem with database";
			}catch (NumberFormatException e) {
				return "Incorrect value for duration";
			}catch (IllegalArgumentException e) {
				return "The category was not chosen or something else went wrong";
			}catch (Exception e) {
				e.getStackTrace();
				return e.getMessage();
			}
			return title + " succefully added. Reload to see changes";

		case "theatre":
			String theatreName = req.getParameter("name");
			String config = req.getParameter("config");

			if(theatreName.equals(""))
				return "Please insert correct value for name";

			try {
				Cinema.getCinema().createTheatre(theatreName, config);
			} catch (SQLException e) {
				System.out.println(e);
				return theatreName+ " already exists or problem with database";
			}catch(SeatException e) {
				System.out.println(e);
				return "Configuration is errated";
			}catch (Exception e) {
				System.out.println(e);
				return e.getMessage();
			}
			return theatreName + " succefully added. Reload to see changes";

		case "showing":
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

				id = Cinema.getCinema().createMovieShowing(movie, date, theatre, price);
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

		case "multiShowing":
			movie = req.getParameter("movie");
			theatre = req.getParameter("theatre");
			String price = req.getParameter("price");
			String [] dateStart = req.getParameter("dateStart").split("-");
			String [] dateFinal = req.getParameter("dateFinal").split("-");
			String []hour = req.getParameter("hour").split(":");
			LocalDateTime startD;
			LocalDateTime finalD;

			if (movie == null || theatre == null || price == null)
				return "please insert correct data";


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
			for (date = startD; date.isBefore(finalD.plusDays(1)); date = date.plusDays(1)) {

				try {
					Cinema.getCinema().createMovieShowing(movie, date, theatre, Double.valueOf(price));
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

		return "Error with javascript script";
	}

}
