package server.handler.adminHandler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;


/**this class is specifically for the admin page to give general information*/

public class LoadGeneral {

	public static String doAction(HttpServletRequest req) {

		switch (req.getParameter("object")) {

		case "general":
			Map<String,Integer> forMovie = new HashMap<>();
			Map<String,Integer> forTheatre = new HashMap<>();
			String messagge;

			// I create the first map for the movies (see adminGeneral.html)
			try {
				for (String movie: Cinema.getCinema().getMovieList()) {
					Integer value = Cinema.getCinema().getMovieShowingList(movie).size();
					forMovie.put(movie, value);
				}
				for (String theatre: Cinema.getCinema().getTheatreList()) {
					Integer value = 0;
					forTheatre.put(theatre, value);
				}
				messagge = Rythm.render("imported/adminGeneral.html",forMovie, forTheatre, Cinema.getCinema().getAllShowingList().size());

			} catch (Exception e) {

				e.printStackTrace();
				messagge = "General error occured. Please try later";
			}
			return messagge;

		case "showingsStat":
			String title = req.getParameter("title");

			if (title == null || title.contentEquals(""))
				return "";

			try {
				messagge = Rythm.render("imported/showTableAdmin.html",
						Cinema.getCinema().getMovieShowingList(title), title);
			} catch (Exception e) {
				System.out.println(e);
				messagge = "Error with server";
			}

			return messagge;
		}

		return "Error with javascript server";

	}
}
