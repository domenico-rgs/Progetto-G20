package server.domain.cinema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.mail.MessagingException;
import server.MailSender;
import server.domain.cinema.theatre.Seat;
import server.domain.cinema.theatre.Theatre;
import server.domain.exception.OverlapException;
import server.domain.exception.PaymentException;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.payment.PaymentFactory;
import server.domain.payment.ShopCart;
import server.domain.payment.discount.PricingStrategyFactory;
import server.domain.payment.discount.TicketPricingStrategy;
import server.services.DB.MoviesMapper;
import server.services.DB.OIDCreator;
import server.services.DB.PersistenceFacade;
import server.services.DB.ShowingsMapper;
import server.services.DB.TheatresMapper;
import server.services.DB.TicketsMapper;

/**
 * Facade controller for managing reservations in a cinema
 * Singleton
 */
public class Cinema {
	private ShopCart shopCart;
	private Quotes quotes;
	private static Cinema istance = null;

	private Cinema() {
		try {
			quotes = new Quotes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		shopCart = new ShopCart();
	}

	/* Creation methods */
	/**
	 * This method creates a theatre
	 * @param name this is the theatre's name
	 * @param config this is the configuration of the theatre
	 */
	synchronized public void createTheatre(String name, String config) throws SQLException, IOException, SeatException  {
		Theatre t = new Theatre(name);
		String file = t.createConfigFile(config);
		t.createSeats(file);
		PersistenceFacade.getInstance().put(name, TheatresMapper.class,t);
	}
	
	/**
	 * This method permits to create movie
	 * @param title movie's title
	 * @param duration movie's duration
	 * @param plot movie's plot
	 * @param pathCover cover's path
	 * @param category movie's category
	 */
	synchronized public void createMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException, SQLException{
		Movie m = new Movie(title, duration, plot, pathCover, category);
		PersistenceFacade.getInstance().put(title,MoviesMapper.class,m);
	}
	
	/**
	 * it creates a movie showing
	 * @param movie showed movie
	 * @param date showing's date
	 * @param theatre showing's theatre
	 * @param price showing's price
	 * @return showing's id
	 */
	synchronized public String createMovieShowing(String movie, LocalDateTime date, String theatre, double price) throws SQLException, SearchException, OverlapException, IOException, SeatException {
		controlOverlapping(date, theatre, movie);
		MovieShowing sh = new MovieShowing(OIDCreator.getInstance().getShowingCode(), movie, date, getTheatre(theatre), price);
		PersistenceFacade.getInstance().put(sh.getId(),ShowingsMapper.class,sh);
		return sh.getId();
	}

	// Called at the time of purchase by the buyTicket method
	/**
	 * it creates a list of tickets
	 * @param showingID showing's id
	 * @param seats booked seats
	 * @return a list of tickets
	 */
	private List<Ticket> createTickets(String showingID, String[] seats) throws SQLException {
		MovieShowing m = getMovieShowing(showingID);
		Set<Seat> sList = getAllSeatsForShowing(showingID).keySet();

		List<Ticket> ticketList = new ArrayList<>();
		for(String s : seats) {
			for(Seat sL : sList)
				if(sL.getPosition().equalsIgnoreCase(s)) {
					ticketList.add(new Ticket(OIDCreator.getInstance().getTicketCode(),m.getMovie(), s, getMovieShowing(showingID), (m.getPrice()*sL.getAddition())));
				}
		}
		PersistenceFacade.getInstance().addTickets(ticketList);
		return ticketList;
	}

	/* Discounts */
	/**
	 * this method creates a discount code
	 * @param code discount's code
	 * @param percent discount's percent
	 */
	synchronized public void createDiscountCode(String code, double percent) throws SQLException  {
		PricingStrategyFactory.getInstance().createDiscountCode(code.toUpperCase(), percent);
	}
	
	/**
	 * it permits to apply a discount
	 * @param code discount's code
	 * @param price price without discount
	 * @return discounted price
	 */
	synchronized public double applyDiscountOnPrice(String code, double price) throws SQLException, PaymentException{
		TicketPricingStrategy discount = PricingStrategyFactory.getInstance().getCodeStrategy(code.toUpperCase());

		if(discount != null)
			return discount.getTotalPrice(price);

		throw new PaymentException("Discount code not found");
	}
	/**
	 * it permits to obtain the discounts list
	 * @return discounts' list
	 */
	synchronized public List<String> getDiscountList() throws NumberFormatException, SQLException {
		List<String> discountCodeList = new ArrayList<>();

		for (TicketPricingStrategy disc: PersistenceFacade.getInstance().getDiscountList()) {
			discountCodeList.add(disc.getCode());
		}
		return discountCodeList;
	}


	/* Edit Methods */
	/**
	 * it modifies ashowing
	 * @param showing showing's id
	 * @param theatre showing's theatre
	 * @param price showing's price
	 */
	synchronized public void editShowing(String showing, String theatre, double price) throws SearchException, SQLException {
		MovieShowing s = getMovieShowing(showing);
		s.editShowing(this.getTheatre(theatre), price);
		PersistenceFacade.getInstance().updateTable(ShowingsMapper.class, s, showing);
	}
	
	/**
	 * It permits to modify a movie 
	 * @param title movie's title
	 * @param pathCover cover's path
	 * @param plot movie's plot
	 * @param category movie's category
	 */
	synchronized public void editMovie(String title, String pathCover, String plot, TypeCategory category) throws SearchException, SQLException{
		Movie m = getMovie(title);
		m.editMovie(pathCover, plot, category);
		PersistenceFacade.getInstance().updateTable(MoviesMapper.class, m, title);
	}

	/*Delete Methods */
	/**
	 * it permits to delete a tickets
	 * @param code ticket's code
	 * @param cardNumber card's number
	 */
	synchronized public void deleteTicket(String code, String cardNumber) throws SQLException, MessagingException, SearchException, FileNotFoundException{
		MailSender.sendRefundMail(code, cardNumber, ((Ticket)PersistenceFacade.getInstance().get(code, TicketsMapper.class)).getTotalPrice());
		PersistenceFacade.getInstance().delete(code, TicketsMapper.class);
	}
	
	/**
	 * this method permits to delete a theatre
	 * @param name theatre's name
	 */
	synchronized public void deleteTheatre(String name) throws SearchException, SQLException{
		File f = new File(this.getTheatre(name).getFilePath());
		f.delete();
		PersistenceFacade.getInstance().delete(name, TheatresMapper.class);
	}
	
	/**
	 * it permits to delete a movie
	 * @param title movie's title
	 */
	synchronized public void deleteMovie(String title) throws SearchException, SQLException{
		PersistenceFacade.getInstance().delete(title, MoviesMapper.class);
	}
	
	/**
	 * This method deletes a movieshowing
	 * @param idShowing showing's id
	 */
	synchronized public void deleteMovieShowing(String idShowing) throws SearchException, SQLException {
		PersistenceFacade.getInstance().delete(idShowing, ShowingsMapper.class);
	}
	
	/**
	 * it permits to delete a discount
	 * @param code discount's code
	 */
	synchronized public void deleteDiscount(String code) throws SearchException, SQLException {
		PricingStrategyFactory.getInstance().removeDiscountCode(code);
	}

	//OVERLAP SHOWING CONTROLL
	/**
	 * This method controls that each showing is not superimposed to others
	 * @param date showing's date
	 * @param theatre showing's theatre
	 * @param movie showing's movie
	 */
	private void controlOverlapping(LocalDateTime date, String theatre, String movie) throws SQLException, OverlapException {
		ZonedDateTime zdt = date.atZone(ZoneId.systemDefault());
		long dateShowingSec = zdt.toInstant().getEpochSecond();
		long movieDurationSec = this.getMovie(movie).getDuration()*60;

		List<MovieShowing> showingList = PersistenceFacade.getInstance().getMovieShowingList(theatre, date);
		long dateShowingToControll;
		long filmToControllDuration;
		for (MovieShowing sh: showingList) {
			zdt = sh.getDate().atZone(ZoneId.systemDefault());
			dateShowingToControll = zdt.toInstant().getEpochSecond();

			if (dateShowingToControll == dateShowingSec)
				throw new OverlapException();

			if (dateShowingToControll > dateShowingSec) {
				if ((dateShowingSec + movieDurationSec) >= dateShowingToControll)
					throw new OverlapException();
			}

			if (dateShowingToControll < dateShowingSec) {
				filmToControllDuration = getMovie(sh.getMovie()).getDuration()*60;

				if ((dateShowingToControll + filmToControllDuration) >= dateShowingSec)
					throw new OverlapException();
			}
		}
	}

	/* Methods for searching already saved objects */
	synchronized public List<String> getQuotes() {
		return quotes.getQuotes();
	}

	synchronized public Movie getMovie(String title) throws SQLException{
		return (Movie) PersistenceFacade.getInstance().get(title, MoviesMapper.class);
	}

	synchronized public Theatre getTheatre(String name) throws SQLException{
		return (Theatre) PersistenceFacade.getInstance().get(name, TheatresMapper.class);
	}

	synchronized public List<String> getMovieList() throws SQLException{
		List<String> titleList = new ArrayList<>();
		titleList.addAll(PersistenceFacade.getInstance().getAllMovies().keySet());
		return titleList;
	}

	synchronized public MovieShowing getMovieShowing(String id) throws SQLException{
		return (MovieShowing) PersistenceFacade.getInstance().get(id, ShowingsMapper.class);
	}

	synchronized public List<String> getTheatreList() throws SQLException{
		List<String> theatreList = new ArrayList<>();
		theatreList.addAll(PersistenceFacade.getInstance().getAllTheatre().keySet());
		return theatreList;
	}

	synchronized public List<MovieShowing> getAllShowingList() throws SQLException {
		return PersistenceFacade.getInstance().getAllMovieShowings();
	}

	synchronized public HashMap<Seat, Boolean> getAllSeatsForShowing(String idShowing) throws SQLException {
		return PersistenceFacade.getInstance().getAvailabilityList(idShowing);
	}


	synchronized public List<Seat> getFreeSeatsForShowing(String idShowing) throws SQLException {
		List<Seat> freeSeats = new ArrayList<>();
		HashMap<Seat,Boolean> tmp = PersistenceFacade.getInstance().getAvailableSeatsList(idShowing);

		for(Seat s : tmp.keySet()) {
			freeSeats.add(s);
		}
		return freeSeats;
	}

	synchronized public List<MovieShowing> getMovieShowingList(String movie) throws SQLException {
		List<MovieShowing> showList = PersistenceFacade.getInstance().getMovieShowingList(movie);
		return showList;
	}

	synchronized public void setAvailability(String idShowing, String[] seats, boolean availability) throws SQLException {
		for(String s : seats) {
			PersistenceFacade.getInstance().changeAvailability(idShowing, s, availability);
		}
	}

	/* ShopCart management */
	/**
	 * it updates the shop cart
	 * @param id showing's id
	 * @param seats booked seats
	 */
	synchronized public void updateShopCartItems(String id, String[] seats) throws SQLException {
		this.shopCart.refresh();
		this.shopCart.setIdSh(id);
		this.shopCart.setSeats(seats);
	}

	public ShopCart getShopCart () {
		return this.shopCart;
	}

	/* Purchase management */
	/**
	 * this method permits to obtain the final cost of the ticket
	 * @param showingID showing's id
	 * @param seats bought seats
	 * @return final price
	 */
	synchronized public double[] ticketsPrice(String showingID, String[] seats) throws SQLException {
		MovieShowing m = getMovieShowing(showingID);
		Set<Seat> sList = getAllSeatsForShowing(showingID).keySet();
		double[] doubleList = new double[seats.length];

		int count = 0;
		for(String s : seats) {
			for(Seat sL : sList)
				if(sL.getPosition().equalsIgnoreCase(s)) {
					double price = (double) Math.round((m.getPrice()*sL.getAddition()) * 100) / 100;
					doubleList[count] = price;
					this.shopCart.addTotal(price);
				}
			count++;
		}
		return doubleList;
	}
	
	/**
	 * it permits to buy a ticket
	 * @param codeCard code of a card
	 * @param date expiry date
	 * @param cvc card's cvc
	 * @param emailRecipient addressee of the email
	 * @return true if ticket is bought
	 */
	synchronized public boolean buyTicket(String codeCard, String date, String cvc, String emailRecipient) throws SQLException, MessagingException, PaymentException, FileNotFoundException {
		List<Ticket> ticketList = createTickets(this.shopCart.getIdSh(), this.shopCart.getSeats());
		boolean result = PaymentFactory.getInstance().getSimPaymentAdapter().pay(getTotalPriceTickets(ticketList), codeCard, date, cvc);
		if(result) {
			MailSender.sendTicketMail(emailRecipient, ticketList);
			return true;

		} else
			return false;
	}

	synchronized private double getTotalPriceTickets(List<Ticket> ticketList) {
		double total = 0.0;

		for(Ticket t : ticketList) {
			total += t.getTotalPrice();
		}
		return total;
	}
	
	public static Cinema getCinema() {
		if (istance == null) {
			istance = new Cinema();
		}
		return istance;
	}
}
