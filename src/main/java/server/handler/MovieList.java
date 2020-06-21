package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.domain.cinema.Cinema;

public class MovieList implements IHandler {
	
	private static MovieList instance = null;

	private MovieList() {
	}


	public static MovieList getInstance() {
		if (instance == null)
			instance = new MovieList();

		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int startPoint = Integer.parseInt(req.getParameter("startPoint"));
		int finalPoint = Integer.parseInt(req.getParameter("finalPoint"));
		
		List<server.domain.cinema.Movie> movieList = 
				this.updateMovieList(startPoint, finalPoint);
		
		resp.getWriter().write(Rythm.render("imported/movieItem.html", movieList));

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
	
	private List<server.domain.cinema.Movie> updateMovieList(int start, int end) {
		
		List<server.domain.cinema.Movie> movieList = new ArrayList<>();
		
		/*
		 * prendo i film dal cinema e li metto in una lista proprietaria
		 * da valutare se non conservare tutti i film ma solo i valori necessari
		 * per motivi di efficienza
		 * in quanto la lista nasce e muore sul momento dopo l'utilizzo
		 */
		
		for (int i=start; i<end; i++) {
			/*
			 * dovrebbe essistere una lista ordinata di movie da cui attingere
			 * oppure usiamo lo stesso hasmap ordinato
			 */
			//movieList.add(Cinema.getCinema().getMovie(i))
		}
		
		return movieList;
	}

}
