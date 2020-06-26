package server.domain.cinema;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.payment.SimPaymentAdapter;
import server.domain.showing.MovieShowing;
import server.domain.theatre.Theatre;
import server.services.DB.MoviesMapper;
import server.services.DB.OIDCreator;
import server.services.DB.PersistenceFacade;
import server.services.DB.ShowingsMapper;
import server.services.DB.TheatreMapper;

/**
 * Facade controller for managing reservations in a cinema
 */
public class CinemaFacade {
	private SimPaymentAdapter payment;
	private Quotes quotes = new Quotes();

	private static CinemaFacade istance = null;

	private CinemaFacade() {
		payment= new SimPaymentAdapter();
	}

	synchronized public void createTheatre(String name, String config) throws SQLException, IOException, SeatException  {
		Theatre t = new Theatre(name, config);
		PersistenceFacade.getInstance().addTheatre(name,t);
	}

	public void editShowing(String showing, String theatre, double price) throws SearchException, SQLException, IOException, SeatException {
		MovieShowing s = getMovieShowing(showing);
		s.editShowing(theatre, price);
		PersistenceFacade.getInstance().updateTable(ShowingsMapper.class, s, showing);
	}

	synchronized public boolean deleteTheatre(String name) throws SearchException{
		return false;
		//OCCORRE CONTROLLARE CHE NON SIA USATO
		//TO-DO
	}

	synchronized public void createMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException, SQLException, IOException, SeatException{
		Movie m = new Movie(title, duration, plot, pathCover, category);
		PersistenceFacade.getInstance().addMovie(title,m);
	}

	synchronized public boolean deleteMovie(String title) throws SearchException{
		return false;
		//OCCORRE CONTROLLARE CHE NON SIA USATO
		//TO-DO
	}


	synchronized public void createMovieShowing(String movie, LocalDateTime date, String theatre, double price) throws SQLException, SearchException, IOException, SeatException {
		MovieShowing s = new MovieShowing(date, getTheatre(theatre), price);
		PersistenceFacade.getInstance().addMovieShowing(OIDCreator.getInstance().getShowingCode(),s);
	}

	synchronized public void deleteMovieShowing(String movie, String idShowing) throws SearchException {
		//OCCORRE CONTROLLARE CHE NON SIANO STATE FATTE PRENOTAZIONI PER QUESTA PROIEZIONE
		//TO-DO
	}

	//Se lancia l'eccezione ne cancella solo una parte, rivedere dopo aver deciso cosa fare del ledger
	synchronized public boolean deleteBooking(String string) throws SearchException{
		TicketLedger.getTicketLedger().removeTicketSale(string);
		return true;
	}

	//AGGIUNGERE LA VENDITA DEI TICKET

	public boolean pay(double money, String seat, String movieShowing) {
		return false;
		//TO-DO
	}

	public List<String> getQuotes() {
		return quotes.getQuotes();
	}

	public Movie getMovie(String title) throws SQLException, IOException, SeatException{
		return (Movie) PersistenceFacade.getInstance().get(title, MoviesMapper.class);
	}

	public Theatre getTheatre(String name) throws SQLException, IOException, SeatException{
		return (Theatre) PersistenceFacade.getInstance().get(name, TheatreMapper.class);
	}

	public List<String> getMovieList() throws IOException, SeatException {
		List<String> titleList = new ArrayList<>();
		titleList.addAll(PersistenceFacade.getInstance().getAllMovies().keySet());

		return titleList;
	}

	public MovieShowing getMovieShowing(String id) throws SQLException, IOException, SeatException{
		return (MovieShowing) PersistenceFacade.getInstance().get(id, ShowingsMapper.class);
	}

	public List<MovieShowing> getMovieShowings(String movie) throws IOException, SeatException {
		List<MovieShowing> titleList = new ArrayList<>();
		titleList.addAll((PersistenceFacade.getInstance().getAllMovieShowings().values()));

		return titleList;
	}

	public static CinemaFacade getCinema() {
		if (istance == null)
			istance = new CinemaFacade();
		return istance;
	}
}
