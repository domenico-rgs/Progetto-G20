package server.servlet.admin;

import javax.servlet.http.HttpServletRequest;

import org.rythmengine.Rythm;

import server.domain.controller.MovieShowingHandler;

public class LoadShowStat {

	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");
		String messagge;

		if (title == null || title.contentEquals(""))
			return "";

		try {
			messagge = Rythm.render("imported/showTableAdmin.html",
					MovieShowingHandler.getInstance().getMovieShowingList(title), title);
		} catch (Exception e) {
			e.printStackTrace();
			messagge = "Error with server";
		}

		return messagge;
	}
}
