package server.servlet;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.controller.MovieHandler;



public class MovieList implements IHandlerState {
	private static MovieList instance = null;

	/** this class is used in the catalog to keep track of a list of films */
	private LinkedList<String> titleMovieList = new LinkedList<>();
	private final int margin = 10;


	private MovieList() {
	}


	//*singleton*/
	public static MovieList getInstance() {
		if (instance == null) {
			instance = new MovieList();
		}
		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		resp.getWriter().write(Rythm.render("imported/movieItem.html", this.updateMovieList()));

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	private List<server.domain.cinema.Movie> updateMovieList() {
		List<server.domain.cinema.Movie> tmpList = new ArrayList<>();


		//evito overflow della lista
		if (titleMovieList.size() == 0)
			return tmpList; //empty

		String title;
		for (int i=0; i<margin; i++) {
			//se la lista vuota
			if (titleMovieList.size() == 0)
				return tmpList;

			title = titleMovieList.pop();
			try {
				tmpList.add(MovieHandler.getInstance().getMovie(title));
			}catch (Exception e) {
				e.printStackTrace();
				return tmpList;
			}
		}
		return tmpList;
	}

	public void refreshMovieList() throws SQLException {
		this.titleMovieList.clear();
		for (String movie: MovieHandler.getInstance().getMovieList()) {
			titleMovieList.push(movie);
		}
	}
}
