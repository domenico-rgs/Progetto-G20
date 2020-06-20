package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;

public class Index implements IHandler {
	private List <server.domain.cinema.Movie> movieList = new ArrayList<>();
	private static Index instance = null;

	private Index() {
		//Per test
		getMovieList();
	}


	public static Index getInstance() {
		if (instance == null)
			instance = new Index();

		return instance;
	}

	private void getMovieList() {
		HashMap<String, server.domain.cinema.Movie> cinemaMovie = Cinema.getCinema().getMovieCatalog();
		int i =0;
		for(String m : cinemaMovie.keySet()) {
			if(i>=5)
				break;
			movieList.add(cinemaMovie.get(m));
			i++;
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.getWriter().write(Rythm.render("index.html", movieList, Cinema.getCinema().getCitation()));
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
