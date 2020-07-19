package server.servlet.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.rythmengine.Rythm;

import server.domain.controller.MovieHandler;
import server.domain.controller.MovieShowingHandler;
import server.domain.controller.TheatreHandler;


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
				for (String movie: MovieHandler.getInstance().getMovieList()) {
					Integer value = MovieShowingHandler.getInstance().getMovieShowingList(movie).size();
					forMovie.put(movie, value);
				}
				for (String theatre: TheatreHandler.getInstance().getTheatreList()) {
					Integer value = 0;
					forTheatre.put(theatre, value);
				}
				messagge = Rythm.render("imported/adminGeneral.html",forMovie, forTheatre, MovieShowingHandler.getInstance().getAllShowingList().size());

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
						MovieShowingHandler.getInstance().getMovieShowingList(title), title);
			} catch (Exception e) {
				System.out.println(e);
				messagge = "Error with server";
			}

			return messagge;
		}

		return "Error with javascript server";

	}
}
