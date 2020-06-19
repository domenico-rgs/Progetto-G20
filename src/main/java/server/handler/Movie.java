package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;

public class Movie implements IHandler {

	private static Movie instance = null;

	private String selectedMovie;

	private Movie() {
	}


	public static Movie getInstance() {

		if (instance == null)
			instance = new Movie();

		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/* verrà salvato il titolo del fiml da cercare, da passarre
		 * all'applicazione che lo cercherà nella base di dati, e ne ritornerà
		 * le informazioni, da renderizzare nell'html
		 */
		this.selectedMovie = req.getParameter("title");
		//metodi di caricamento della pagina del film

		try {
			server.domain.cinema.Movie movie = Cinema.getCinema().searchMovie(req.getParameter("title"));
			resp.getWriter().write(Rythm.render("movieInformation.html", movie));
		} catch (SearchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// non serve probabilmente

	}

/*
	private void getAvaliableShow(Map<String, Object> params) {

		List<String> idShowList = new ArrayList<>();

		idShowList.add("show1");
		idShowList.add("ishow");
		idShowList.add("sfdadf");

		params.put("idShowList", idShowList);

		return;


	}
	*/

}


