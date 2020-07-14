package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.SeatException;

public class MovieList implements IHandler {
	private static MovieList instance = null;

	/** this class is used in the catalog to keep track of a list of films */
	private List<String> titleMovieList = new ArrayList<>();

	private MovieList() {}

	
	//*singleton*/
	public static MovieList getInstance() {
		if (instance == null) {
			instance = new MovieList();
		}
		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int startPoint = Integer.parseInt(req.getParameter("startPoint"));
		int finalPoint = Integer.parseInt(req.getParameter("finalPoint"));

		List<server.domain.cinema.Movie> movieList =
				this.updateMovieList(startPoint, finalPoint);

		resp.getWriter().write(Rythm.render("imported/movieItem.html", movieList));

		movieList.clear();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	private List<server.domain.cinema.Movie> updateMovieList(int start, int end) {
		List<server.domain.cinema.Movie> movieList = new ArrayList<>();

		/**check if they are at the end of the list*/
		if (end > this.titleMovieList.size()) {
			end = this.titleMovieList.size();
		}

		if (start >= this.titleMovieList.size())
			return movieList;   //actually empty

		/*
		 * we take the films from the cinema and put them on a proprietary list
		 * to evaluate if not to keep all the films but only the necessary values
		 * for efficiency reasons
		 * as the list is born and dies on the spot after use
		 */

		for (int i=start; i<end; i++) {
			try {
				movieList.add(Cinema.getCinema().getMovie(this.titleMovieList.get(i)));
			}catch (Exception e) {
				e.toString();
			}
		}
		return movieList;
	}

	public void updateMovieList() {
		try {
			this.titleMovieList = Cinema.getCinema().getMovieList();
		} catch (IOException | SeatException e) {
			e.toString();
		}
	}
}
