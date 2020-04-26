package it.unipv.www.g20.controller.cinema;

import java.util.HashMap;
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

	public boolean addMovie(String title) {

		if (movieCatalog.containsKey(title))
			return false;
		movieCatalog.put(title, new Movie(title));
		return true;

	}

	public boolean createTheatre(String name, int row, int col){
		if (theatreList.containsKey(name))
			return false;

		theatreList.put(name, new Theatre(name, row, col));
		return true;
	}

	public boolean deleteMovie(String title){

		if (!(movieCatalog.containsKey(title)))
			return false;

		movieCatalog.remove(title);
		return true;
	}

	public boolean deleteTheatre(String name){

		if (!(theatreList.containsKey(name)))
			return false;
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
