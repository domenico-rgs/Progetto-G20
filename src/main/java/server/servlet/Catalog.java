package server.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.controller.MovieHandler;
import server.exception.ObjectNotFoundException;

/**
 * Servlet class that manages the catalog page
 * Singleton class (State pattern)
 */
public class Catalog implements IHandlerState {
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

		MovieList.getInstance().refreshMovieList();

		if (req.getParameter("search").contentEquals("all") || req.getParameter("search").contentEquals("")) {
			resp.getWriter().write(Rythm.render("catalog.html"));
		} else {
			List<server.domain.cinema.Movie> movieList = null;
			try {
				movieList = MovieHandler.getInstance().searchMovieForString(req.getParameter("search"));
				resp.getWriter().write(Rythm.render("searchCatalog.html",movieList,""));
			} catch (ObjectNotFoundException e) {
				String messagge = req.getParameter("search") + " does not exist";
				resp.getWriter().write(Rythm.render("searchCatalog.html",movieList,messagge));
			}
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
