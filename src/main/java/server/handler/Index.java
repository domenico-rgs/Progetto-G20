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
import server.domain.exception.SeatException;

public class Index implements IHandler {
	private static Index instance = null;

	private Index() {}

	public static Index getInstance() {
		if (instance == null)
			instance = new Index();
		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> quote = Cinema.getCinema().getQuotes();
		int intexCatalog = 5;  //quanti elementi visualizzare in prima pagina

		resp.getWriter().write(Rythm.render("index.html", this.getIndexMovie(intexCatalog),
				quote));
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	private List<server.domain.cinema.Movie> getIndexMovie(int n) {
		List<String> title;
		try {
			title = Cinema.getCinema().getMovieList();
			List<server.domain.cinema.Movie> movieList = new ArrayList<>();

			//per evitare errori di sforamento
			if (n > title.size())
				n = title.size();

			for (int i = 0; i<n; i++)
				movieList.add(Cinema.getCinema().getMovie(title.get(i)));
			return movieList;
		} catch (IOException | SeatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
