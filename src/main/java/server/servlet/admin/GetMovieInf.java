package server.servlet.admin;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.MovieHandler;

public class GetMovieInf {

	public static String doAction(HttpServletRequest req) {
		String title = req.getParameter("title");

		if (title == null || title.contentEquals("")) return "";

		server.domain.cinema.Movie movie;
		try {
			movie = MovieHandler.getInstance().getMovie(title);
			String inf = movie.getTitle() + "@" + String.valueOf(movie.getDuration()) +
					"@" + movie.getPlot() + "@" + movie.getPathCover() + "@" +
					movie.getCategory().toString();

			return inf;
		}catch (Exception e) {
			return "Error@"+ "Get information for " + title;
		}
	}
}
