package server.servlet.admin;

import javax.servlet.http.HttpServletRequest;

import org.rythmengine.Rythm;

import server.domain.controller.MovieShowingHandler;

/**this class is specifically for the admin page to give information on showing*/

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
			System.out.println(e);
			messagge = "Error with server";
		}

		return messagge;
	}

}
