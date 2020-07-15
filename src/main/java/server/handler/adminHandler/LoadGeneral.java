package server.handler.adminHandler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;


/**this class is specifically for the admin page to give general information*/

public class LoadGeneral {

	public static String doAction(HttpServletRequest req) {
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
			messagge = "Error from server, sorry :(";
		}
		return messagge;
	}
}
