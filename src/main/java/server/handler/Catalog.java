package server.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;



/**controller class that manages the catalog page*/
public class Catalog implements IHandler {
	private static Catalog instance = null;

	private Catalog() {}


	//*singleton*/
	public static Catalog getInstance() {
		if (instance == null) {
			instance = new Catalog();
		}
		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		if (req.getParameter("search").contentEquals("all") || req.getParameter("search").contentEquals("")) {
			resp.getWriter().write(Rythm.render("catalog.html"));
		} else {
			List<server.domain.cinema.Movie> movieList = this.searchMovieForString(req.getParameter("search"));

			if (movieList.size() == 0) {
				String messagge = req.getParameter("search") + " does not exist";
				resp.getWriter().write(Rythm.render("searchCatalog.html",movieList,messagge));
			} else {
				resp.getWriter().write(Rythm.render("searchCatalog.html",movieList,""));
			}
		}

		MovieList.getInstance().updateMovieList();


	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}


	private List<server.domain.cinema.Movie> searchMovieForString(String search) {
		List<server.domain.cinema.Movie> movieList = new ArrayList<>();

		List<String> movieTitle;
		try {
			movieTitle = Cinema.getCinema().getMovieList();

			/**search if the titles contain that word*/
			for (String title: movieTitle)
				if (title.toLowerCase().contains(search.toLowerCase())) {
					movieList.add(Cinema.getCinema().getMovie(title));
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return movieList;
	}
}
