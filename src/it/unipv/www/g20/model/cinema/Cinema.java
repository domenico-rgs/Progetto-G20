package it.unipv.www.g20.model.cinema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

import it.unipv.www.g20.model.exception.NotAvailableException;
import it.unipv.www.g20.model.exception.NotPermittedException;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.operator.Cashier;
import it.unipv.www.g20.model.operator.Manager;
import it.unipv.www.g20.model.operator.Operator;
import it.unipv.www.g20.model.operator.TypeOperator;
import it.unipv.www.g20.model.theatre.Theatre;

/**
 * Facade controller for managing reservations in a cinema
 */
//aggiungere metodo per prenotazione cumulativa
public class Cinema implements Manageable {
	private final String name;

	private final TreeMap<String, Theatre> theatreList; //uso le tree map poichè potrebbe servirmi ordinare sia i teatri che i film in qualche modo sulla base dei loro nomi
	private final TreeMap<String, Movie> movieList;
	private final HashMap<String, Operator> operatorList;
	private final HashMap<String, Ticket> ticketList;

	public Cinema(String name) {
		this.name = name;

		theatreList = new TreeMap<>();
		movieList = new TreeMap<>();
		operatorList = new HashMap<>();
		ticketList = new HashMap<>();
	}

	@Override
	public boolean addMovie(String title, Operator op) throws NotPermittedException, SearchException {
		checkPermission(op);

		if (movieList.containsKey(title))
			throw new SearchException("Esiste già un film con il titolo specificato");

		movieList.put(title, new Movie(title));
		return true;

	}

	@Override
	public void addOperator(String nickname, TypeOperator type, Operator op) throws NotPermittedException {
		checkPermission(op);

		if (type.equals(TypeOperator.CASHIER)) {
			operatorList.put(nickname, new Cashier(nickname));
		} else {
			operatorList.put(nickname, new Manager(nickname));
		}
	}

	@Override
	public boolean addTheatre(String name, int row, int column, Operator op)
			throws NotPermittedException, SearchException {
		checkPermission(op);

		if (theatreList.containsKey(name))
			throw new SearchException("Esiste già un teatro con il nome specificato");

		theatreList.put(name, new Theatre(name, row, column));
		return true;
	}

	@Override
	public void addTicket(String theatreName, String date, String seatCode)
			throws ParseException, NotAvailableException, SearchException {
		final Theatre t = theatreList.get(theatreName);
		final String ticketCode = UUID.randomUUID().toString(); //potremmo pensare di sostituirlo con qualcosa di più corto e semplice da ricordare
		t.getShow(getDate(date)).bookSeat(seatCode);
		ticketList.put(ticketCode, new Ticket(ticketCode, t, t.getShow(getDate(date))));
	}

	@Override
	public void createMovieShowing(String movieTitle, String theatreName, String date, Double price, Operator op)
			throws NotPermittedException, ParseException, SearchException {
		checkPermission(op);

		theatreList.get(theatreName).addMovieShowing(movieList.get(movieTitle), getDate(date), price);
	}

	@Override
	public boolean deleteMovie(String title, Operator op) throws NotPermittedException, SearchException {
		checkPermission(op);

		if (!(movieList.containsKey(title)))
			throw new SearchException("Il film indicato non è stato trovato");

		movieList.remove(title);
		return true;
	}

	@Override
	public boolean deleteOperator(String id, Operator op) throws NotPermittedException, SearchException {
		checkPermission(op);

		if (!(operatorList.containsKey(id)))
			throw new SearchException("L'operatore indicato non è stato trovato");

		operatorList.remove(id);
		return true;

	}

	@Override
	public boolean deleteTheatre(String name, Operator op) throws NotPermittedException, SearchException {
		checkPermission(op);

		if (!(theatreList.containsKey(name)))
			throw new SearchException("Il teatro specificato non è presente in questo cinema");

		theatreList.remove(name);
		return true;
	}

	@Override
	public void deleteTicket(String code) throws SearchException {
		if (!(ticketList.containsKey(code)))
			throw new SearchException("Il ticket indicato non è stato trovato");

		ticketList.remove(code);
	}

	public String getName() {
		return name;
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
	 * Check if the operator has the necessary grants to do some action in the cinema like add movieShowing
	 */
	private void checkPermission(Operator op) throws NotPermittedException {
		if (!op.getType().equals(TypeOperator.MANAGER))
			throw new NotPermittedException("Non hai i permessi necessari!");
	}
	
	/**
	 * Create a string with all the recorded programming
	 * @return the string with all the recorded programming
	 */
	public String printProgramming() {
		String string="PROGRAMMAZIONE "+this.getName().toUpperCase()+"\n";
		for(String s : theatreList.keySet()) {
			string+="Sala: "+theatreList.get(s).getName()+"\n";
			string+=theatreList.get(s).printMovieShowing();
		}
		return string;	
	}

	/*
	 * Used to get a Date given a String in the form dd/MM/yyyy HH:mm
	 */
	private Date getDate(String date) throws ParseException {
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return format.parse(date);
	}
}
