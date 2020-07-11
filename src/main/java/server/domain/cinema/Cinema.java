package server.domain.cinema;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.MailSender;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.payment.ServiceFactory;
import server.domain.payment.SimPaymentAdapter;
import server.domain.shopCard.ShopCard;
import server.domain.showing.MovieShowing;
import server.domain.theatre.Seat;
import server.domain.theatre.Theatre;
import server.services.DB.MoviesMapper;
import server.services.DB.OIDCreator;
import server.services.DB.PersistenceFacade;
import server.services.DB.ShowingsMapper;
import server.services.DB.TheatresMapper;

/**
 * Facade controller for managing reservations in a cinema
 */
public class Cinema {
	private SimPaymentAdapter payment;
	private ShopCard shopCard;
	private Quotes quotes = new Quotes();

	private static Cinema istance = null;

	private Cinema() {
		payment= new SimPaymentAdapter();
		shopCard = new ShopCard();
	}

	synchronized public void createTheatre(String name, String config) throws SQLException, IOException, SeatException  {
		Theatre t = new Theatre(name);
		String file = t.createConfigFile(config);
		t.createSeats(file);
		PersistenceFacade.getInstance().addTheatre(name,t);
	}

	public void editShowing(String showing, String theatre, double price) throws SearchException, SQLException, IOException, SeatException {
		MovieShowing s = getMovieShowing(showing);
		s.editShowing(theatre, price);
		PersistenceFacade.getInstance().updateTable(ShowingsMapper.class, s, showing);
	}

	public void editMovie(String title, String pathCover, String plot, TypeCategory category) throws SearchException, SQLException, IOException, SeatException {
		Movie m = getMovie(title);
		m.editMovie(pathCover, plot, category);
		PersistenceFacade.getInstance().updateTable(MoviesMapper.class, m, title);
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


	synchronized public String createMovieShowing(String movie, LocalDateTime date, String theatre, double price) throws SQLException, SearchException, IOException, SeatException {
		MovieShowing s = new MovieShowing(OIDCreator.getInstance().getShowingCode(), movie, date, getTheatre(theatre), price);
		PersistenceFacade.getInstance().addMovieShowing(s.getId(),s);

		return s.getId();
	}

	synchronized public void deleteMovieShowing(String movie, String idShowing) throws SearchException {
		//OCCORRE CONTROLLARE CHE NON SIANO STATE FATTE PRENOTAZIONI PER QUESTA PROIEZIONE
		//TO-DO
	}

	//Se lancia l'eccezione ne cancella solo una parte, rivedere dopo aver deciso cosa fare del ledger
	synchronized public void deleteBooking(String string) throws SearchException{
		//TO-DO
	}

	public List<String> getQuotes() {
		return quotes.getQuotes();
	}

	public Movie getMovie(String title) throws SQLException, IOException, SeatException{
		return (Movie) PersistenceFacade.getInstance().get(title, MoviesMapper.class);
	}

	public Theatre getTheatre(String name) throws SQLException, IOException, SeatException{
		return (Theatre) PersistenceFacade.getInstance().get(name, TheatresMapper.class);
	}

	public List<String> getMovieList() throws IOException, SeatException {
		List<String> titleList = new ArrayList<>();
		titleList.addAll(PersistenceFacade.getInstance().getAllMovies().keySet());
		return titleList;
	}

	public MovieShowing getMovieShowing(String id) throws SQLException, IOException, SeatException{
		return (MovieShowing) PersistenceFacade.getInstance().get(id, ShowingsMapper.class);
	}


	public List<MovieShowing> getAllShowingList() throws IOException, SeatException {
		List<MovieShowing> allList = new ArrayList<>();
		allList.addAll((PersistenceFacade.getInstance().getAllMovieShowings().values()));

		return allList;
	}

	public HashMap<Seat, Boolean> getAllSeatsForShowing (String idShowing) throws SQLException, IOException, SeatException {
		return PersistenceFacade.getInstance().getAvailabilityList(idShowing);
	}

	//restituisce solo i posti liberi per proiezione
	public List<Seat> getFreeSeatsForShowing(String idShowing) throws SQLException, IOException, SeatException {
		List<Seat> freeSeats = new ArrayList<>();

		HashMap<Seat,Boolean> tmp = PersistenceFacade.getInstance().getAvailableSeatsList(idShowing);

		for(Seat s : tmp.keySet())
			freeSeats.add(s);
		return freeSeats;
	}

	public List<MovieShowing> getMovieShowingList(String movie) throws IOException, SeatException, SQLException {
		List<MovieShowing> showList = PersistenceFacade.getInstance().getMovieShowingList(movie);
		return showList;
	}

	public List<String> getTheatreList() throws IOException, SeatException {
		List<String> theatreList = new ArrayList<>();
		theatreList.addAll(PersistenceFacade.getInstance().getAllTheatre().keySet());
		return theatreList;
	}

	///// METODI DA IMPLEMENTARE ///////
	//settare i posti scelti occupati, prima del pagamento (magari un timer?), ed eccezioni
	public boolean setOccupedSeats (String idShowing, List<String> seats) {
		return false;
	}

	//rendere i posti liberi nel caso di rinuncia
	public boolean setFreeSeats (String idShowing, List<String> seats) {
		return false;
	}

	// METODI DI GESTIONE DELLO SHOPCARD //
	public void updateShopCardItems(String id, String[] seats) throws SQLException, IOException, SeatException {

		this.shopCard.setIdSh(id);	
		
		this.shopCard.setSeats(seats);
	}

	public ShopCard getShopCard () {
		return this.shopCard;
	}

	//METODI NEL PAGAMENTO//
	public double getDiscount(String code) {

		//controllo i buffer in shopcard (evito di usarlo due volte di seguito prima del pagamento)
		//if (this.shopCard.getCode().contains(code)) return -1;

		/*ricerca il ticket nel database, se lo trova,
		 * aggiorna lo shopcard e ritorna il valore, senno ritorna 0
		 */
		double discount = 4;
		this.shopCard.addCode(code);
		this.shopCard.changeTotal(discount);

		return 4;
	}

	private List<Ticket> createTickets(String showingID, String[] seats) throws SQLException, IOException, SeatException {
		MovieShowing m = getMovieShowing(showingID);
		List<Seat> sList = getFreeSeatsForShowing(showingID);
		
		List<Ticket> ticketList = new ArrayList<>();
		for(String s : seats) {
			for(Seat sL : sList) {
				if(sL.getPosition().equalsIgnoreCase(s))
					ticketList.add(new Ticket(OIDCreator.getInstance().getTicketCode(),m.getMovie(), s, showingID, (m.getPrice()+sL.getAddition()*100)));
			}
		}
		PersistenceFacade.getInstance().addTickets(ticketList);
		
		for (Ticket t: ticketList) {
			this.shopCard.addTotal(t.getTotalPrice());;
		}
		
		return ticketList;
		
	}
	
	public boolean buyTicket(String codeCard, String date, String cvc, String emailRecipient) throws SQLException, IOException, SeatException {
		double total = 0.0;
		List<Ticket> ticketList = createTickets(this.shopCard.getIdSh(), this.shopCard.getSeats());
		for(Ticket t : ticketList) {
			total += t.getTotalPrice();
		}
		
		boolean result = ServiceFactory.getInstance().getPaymentAdapter().pay(total, codeCard, date, cvc);
		if(result) {
			MailSender.sendTicketMail(emailRecipient, ticketList);
			return true;
		}else {
			return false;
		}
	}

//	private void removeBufferDiscount() {
//		//metodi per rimuovere gli elementi dal database
//
//		this.shopCard.getCode().clear();
//	}

	public static Cinema getCinema() {
		if (istance == null)
			istance = new Cinema();
		return istance;
	}
}
