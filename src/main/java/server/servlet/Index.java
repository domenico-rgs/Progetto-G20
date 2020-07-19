package server.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.Quotes;
import server.domain.controller.MovieHandler;
import server.exception.ObjectNotFoundException;


/**
 * The servlet class that links the index.html page to logic
 *
 * Singleton class (State pattern)
 */
public class Index implements IHandlerState {
	private static Index instance = null;
	private Quotes quote;

	private Index() {
		quote = new Quotes();
	}

	public static Index getInstance() {
		if (instance == null) {
			instance = new Index();
		}
		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int intexCatalog = 5;  		// how many items to display on the first page

		/*Publish 5 films on the main page*/
		try {
			resp.getWriter().write(Rythm.render("index.html", this.getIndexMovie(intexCatalog),
					quote.getQuotes()));
		} catch (IOException | SQLException | ObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	/**
	 * It gives me back a list of films to be displayed on the homepage with only n elements
	 * @param n number of movies to display
	 * @return
	 * @throws SQLException
	 * @throws ObjectNotFoundException
	 */
	private List<server.domain.cinema.Movie> getIndexMovie(int n) throws SQLException, ObjectNotFoundException {
		List<String> title = MovieHandler.getInstance().getMovieList();
		List<server.domain.cinema.Movie> movieList = new ArrayList<>();

		/*to avoid overflow errors*/
		if (n > title.size()) {
			n = title.size();
		}

		for (int i = 0; i<n; i++) {
			movieList.add(MovieHandler.getInstance().getMovie(title.get(i)));
		}
		return movieList;
	}
}
