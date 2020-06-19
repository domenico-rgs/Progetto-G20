package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

public class MovieItem implements IHandler {

	private static MovieItem instance = null;

	private int position;
	List <String> movies = new ArrayList<>(); //testing

	private MovieItem() {
		this.position = 0;
		this.prova(); //testing
	}


	public static MovieItem getInstance() {

		if (instance == null)
			instance = new MovieItem();

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/* usato per il ritorno dei film tramite AJAX, ritornato di volta in volta
		 *	5 o 10 film, spostando il contatore e stampandoli nell'app
		 */

		List<String> stampa = new ArrayList<>();

		//metodi per la creazione della lista dei film da app

		for (int i=0; i<5; i++) {
			stampa.add(movies.get(position));
			position++;
		}

		resp.getWriter().write(Rythm.render("imported/movieItem.html", stampa));

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	public void resetPosition() {
		this.position = 0;
	}

	//testing
	public void prova() {
		for(int i=0; i<20; i++)
			movies.add(String.valueOf(i));

	}

}
