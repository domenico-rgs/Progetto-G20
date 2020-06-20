package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;

public class Catalog implements IHandler {
	ArrayList <server.domain.cinema.Movie> movieList = new ArrayList<>(); //testing
	private static Catalog instance = null;


	private Catalog() {
		getMovieList();
	}


	public static Catalog getInstance() {

		if (instance == null)
			instance = new Catalog();

		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write(Rythm.render("catalog.html", movieList));


	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("search") == null ||
				req.getParameter("search").equals("all"))
			resp.getWriter().write(Rythm.render("catalog.html", movieList));
	}

	private void getMovieList() {
		HashMap<String, server.domain.cinema.Movie> cinemaMovie = Cinema.getCinema().getMovieCatalog();

		for(String m : cinemaMovie.keySet())
			movieList.add(cinemaMovie.get(m));
	}


}
