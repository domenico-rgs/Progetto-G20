package it.unipv.www.g20.controller.cinema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import it.unipv.www.g20.model.exception.NotAvailableException;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.theatre.Theatre;

/**
 * Facade controller for managing reservations in a cinema
 */
//aggiungere metodo per prenotazione cumulativa
public class Cinema {
	private final String name;
	private HashMap <String,Theatre> theatreList;
	private HashMap <String, Movie> movieCatalog;


	public Cinema(String name) {
		this.name = name;
		theatreList=new HashMap<>();
		movieCatalog=new HashMap<>();
	}

	public boolean addMovie(String title) throws SearchException {

		if (movieCatalog.containsKey(title))
			throw new SearchException("Esiste già un film con il titolo specificato");

		movieCatalog.put(title, new Movie(title));
		return true;

	}

	public boolean createTheatre(String name, int row, int col)
			throws SearchException {

		if (theatreList.containsKey(name))
			throw new SearchException("Esiste già un teatro con il nome specificato");

		theatreList.put(name, new Theatre(name, row, col));
		return true;
	}

	public boolean deleteMovie(String title) throws  SearchException {

		if (!(movieCatalog.containsKey(title)))
			throw new SearchException("Il film indicato non è stato trovato");

		movieCatalog.remove(title);
		return true;
	}

	public boolean deleteTheatre(String name) throws SearchException {

		if (!(theatreList.containsKey(name)))
			throw new SearchException("Il teatro specificato non è presente in questo cinema");

		theatreList.remove(name);
		return true;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Cinema: " + name;
	}
	
}
