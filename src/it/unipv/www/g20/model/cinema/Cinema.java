package it.unipv.www.g20.model.cinema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
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

	private final TreeMap<String, Theatre> theatreList; //uso le tree map poichè potrebbe servirmi ordinare sia i teatri che i film in qualche modo sulla base dei loro nomi
	private final TreeMap<String, Movie> movieList;
	private final HashMap<String, Ticket> ticketList;

	public Cinema(String name) {
		this.name = name;

		theatreList = new TreeMap<>();
		movieList = new TreeMap<>();
		ticketList = new HashMap<>();
	}

	public boolean addMovie(String title) throws SearchException {

		if (movieList.containsKey(title))
			throw new SearchException("Esiste già un film con il titolo specificato");

		movieList.put(title, new Movie(title));
		return true;

	}

	public boolean addTheatre(String name, int row, int column)
			throws SearchException {

		if (theatreList.containsKey(name))
			throw new SearchException("Esiste già un teatro con il nome specificato");

		theatreList.put(name, new Theatre(name, row, column));
		return true;
	}

	public void addTicket(String theatreName, String date, String seatCode)
			throws ParseException, NotAvailableException, SearchException {
		final Theatre t = theatreList.get(theatreName);
		final String ticketCode = UUID.randomUUID().toString(); //potremmo pensare di sostituirlo con qualcosa di più corto e semplice da ricordare
		t.getShow(getDate(date)).bookSeat(seatCode);
		ticketList.put(ticketCode, new Ticket(ticketCode, t, t.getShow(getDate(date))));
	}

	public void createMovieShowing(String movieTitle, String theatreName, String date, Double price)
			throws ParseException, SearchException {

		theatreList.get(theatreName).addMovieShowing(movieList.get(movieTitle), getDate(date), price);
	}

	public boolean deleteMovie(String title) throws  SearchException {

		if (!(movieList.containsKey(title)))
			throw new SearchException("Il film indicato non è stato trovato");

		movieList.remove(title);
		return true;
	}

	public boolean deleteTheatre(String name) throws SearchException {

		if (!(theatreList.containsKey(name)))
			throw new SearchException("Il teatro specificato non è presente in questo cinema");

		theatreList.remove(name);
		return true;
	}

	public void deleteTicket(String code) throws SearchException {
		if (!(ticketList.containsKey(code)))
			throw new SearchException("Il ticket indicato non è stato trovato");

		ticketList.remove(code);
	}

	public String getName() {
		return name;
	}

	/**
	 * Create a string with all the recorded programming
	 * @return the string with all the recorded programming
	 */
	public String printProgramming() {
		String string="PROGRAMMAZIONE "+getName().toUpperCase()+"\n";
		for(final String s : theatreList.keySet()) {
			string+="Sala: "+theatreList.get(s).getName()+"\n";
			string+=theatreList.get(s).printMovieShowing();
		}
		return string;
	}

	/**
	 * @return a String with the entire details of all ticket saved
	 */
	public String printTicketList() {
		final StringBuilder string = new StringBuilder();
		for (final String s : ticketList.keySet())
			string.append(ticketList.get(s).toString() + "\n\n");

		return string.toString();
	}

	@Override
	public String toString() {
		return "Cinema: " + name;
	}

	/*
	 * Used to get a Date given a String in the form dd/MM/yyyy HH:mm
	 */
	private Date getDate(String date) throws ParseException {
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return format.parse(date);
	}
}
