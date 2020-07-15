package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;

public class LoadShowStat {

	public static String doAction(HttpServletRequest req) {

		String title = req.getParameter("title");
		String messagge;

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

}
