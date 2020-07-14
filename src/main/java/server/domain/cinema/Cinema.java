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

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import server.MailSender;
import server.domain.cinema.theatre.Seat;
import server.domain.cinema.theatre.Theatre;
import server.domain.exception.OverlapException;
import server.domain.exception.PaymentException;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.payment.ServiceFactory;
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
	synchronized public void createTheatre(String name, String config) throws SQLException, IOException, SeatException  {
		Theatre t = new Theatre(name);
		String file = t.createConfigFile(config);
		t.createSeats(file);
		PersistenceFacade.getInstance().put(name, TheatresMapper.class,t);
	}

	synchronized public void createMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException, SQLException, IOException, SeatException{
		Movie m = new Movie(title, duration, plot, pathCover, category);
		PersistenceFacade.getInstance().put(title,MoviesMapper.class,m);
	}

	synchronized public String createMovieShowing(String movie, LocalDateTime date, String theatre, double price) throws SQLException, SearchException, IOException, SeatException, OverlapException {
		controlOverlapping(date, theatre, movie);
		MovieShowing sh = new MovieShowing(OIDCreator.getInstance().getShowingCode(), movie, date, getTheatre(theatre), price);
		PersistenceFacade.getInstance().put(sh.getId(),ShowingsMapper.class,sh);
		return sh.getId();
	}

	// Called at the time of purchase by the buyTicket method
	private List<Ticket> createTickets(String showingID, String[] seats) throws SQLException, IOException, SeatException {
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
	synchronized public void createDiscountCode(String code, double percent) throws SQLException, IOException, SeatException  {
		PricingStrategyFactory.getInstance().createDiscountCode(code.toUpperCase(), percent);
	}

	synchronized public double applyDiscountOnPrice(String code, double price) throws SQLException, IOException, SeatException, PaymentException{
		TicketPricingStrategy discount = PricingStrategyFactory.getInstance().getCodeStrategy(code.toUpperCase());

		if(discount != null) {
			discount.getTotalPrice(price);
		}

		throw new PaymentException("Discount code not found");
	}
	
	public List<String> getDiscountList() throws IOException, SeatException, NumberFormatException, SQLException {
		List<String> discountCodeList = new ArrayList<>();
		
		for (TicketPricingStrategy disc: PersistenceFacade.getInstance().getDiscountList()) {
			discountCodeList.add(disc.getCode());
		}
		
		return discountCodeList;
	}


	/* Edit Methods */
	public void editShowing(String showing, String theatre, double price) throws SearchException, SQLException, IOException, SeatException {
		MovieShowing s = getMovieShowing(showing);
		s.editShowing(this.getTheatre(theatre), price);
		PersistenceFacade.getInstance().updateTable(ShowingsMapper.class, s, showing);
	}

	public void editMovie(String title, String pathCover, String plot, TypeCategory category) throws SearchException, SQLException, IOException, SeatException {
		Movie m = getMovie(title);
		m.editMovie(pathCover, plot, category);
		PersistenceFacade.getInstance().updateTable(MoviesMapper.class, m, title);
	}

	/*Delete Methods */
	synchronized public void deleteTicket(String code, String cardNumber) throws SQLException, IOException, SeatException, MessagingException, SearchException{
		MailSender.sendRefundMail(code, cardNumber, ((Ticket)PersistenceFacade.getInstance().get(code, TicketsMapper.class)).getTotalPrice());
		PersistenceFacade.getInstance().delete(code, TicketsMapper.class);
	}

	synchronized public void deleteTheatre(String name) throws SearchException, SQLException, IOException, SeatException{
		PersistenceFacade.getInstance().delete(name, TheatresMapper.class);
	}

	synchronized public void deleteMovie(String title) throws SearchException, SQLException, IOException, SeatException{
		PersistenceFacade.getInstance().delete(title, MoviesMapper.class);
	}

	synchronized public void deleteMovieShowing(String idShowing) throws SearchException, SQLException, IOException, SeatException {
		PersistenceFacade.getInstance().delete(idShowing, ShowingsMapper.class);
	}
	
	public void deleteDiscount(String code) throws SearchException, SQLException, IOException, SeatException {
		PricingStrategyFactory.getInstance().removeDiscountCode(code);
	}

	//OVERLAP SHOWING CONTROLL
	private void controlOverlapping(LocalDateTime date, String theatre, String movie) throws SQLException, IOException, SeatException, OverlapException {

		//in millisecondi
		ZonedDateTime zdt = date.atZone(ZoneId.systemDefault());
		long dateShowingSec = zdt.toInstant().getEpochSecond();
		long movieDurationSec = this.getMovie(movie).getDuration()*60;

		//testing
		System.out.println(dateShowingSec);
		System.out.println(movieDurationSec);

		//ricavo le proiezioni alla data attuale (spero che funzioni)
		List<MovieShowing> showingList = PersistenceFacade.getInstance().getMovieShowingList(theatre, date);

		//test
		System.out.println(showingList);

		//variabili delle proiezioni gia esistenti nella lista restituita
		long dateShowingToControll;
		long filmToControllDuration;
		for (MovieShowing sh: showingList) {

			zdt = sh.getDate().atZone(ZoneId.systemDefault());
			dateShowingToControll = zdt.toInstant().getEpochSecond();

			//test
			System.out.println(dateShowingToControll);

			//se l'orario � lo stesso
			if (dateShowingToControll == dateShowingSec)
				throw new OverlapException();

			//se l'orario � successivo a quella che voglio aggiungere
			if (dateShowingToControll > dateShowingSec) {

				//se si accavalla con una successiva
				if ((dateShowingSec + movieDurationSec) >= dateShowingToControll)
					throw new OverlapException();
			}

			//se l'orario � precendete a quella che voglio aggiungere
			if (dateShowingToControll < dateShowingSec) {
				filmToControllDuration = getMovie(sh.getMovie()).getDuration()*60;

				//se si accavalla con una precedente
				if ((dateShowingToControll + filmToControllDuration) >= dateShowingSec)
					throw new OverlapException();
			}
		}
	}

	/* Methods for searching already saved objects */
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

	public List<String> getTheatreList() throws IOException, SeatException {
		List<String> theatreList = new ArrayList<>();
		theatreList.addAll(PersistenceFacade.getInstance().getAllTheatre().keySet());
		return theatreList;
	}

	public List<MovieShowing> getAllShowingList() throws IOException, SeatException {
		return PersistenceFacade.getInstance().getAllMovieShowings();
	}

	public HashMap<Seat, Boolean> getAllSeatsForShowing(String idShowing) throws SQLException, IOException, SeatException {
		return PersistenceFacade.getInstance().getAvailabilityList(idShowing);
	}


	public List<Seat> getFreeSeatsForShowing(String idShowing) throws SQLException, IOException, SeatException {
		List<Seat> freeSeats = new ArrayList<>();

		HashMap<Seat,Boolean> tmp = PersistenceFacade.getInstance().getAvailableSeatsList(idShowing);

		for(Seat s : tmp.keySet()) {
			freeSeats.add(s);
		}
		return freeSeats;
	}

	public List<MovieShowing> getMovieShowingList(String movie) throws IOException, SeatException, SQLException {
		List<MovieShowing> showList = PersistenceFacade.getInstance().getMovieShowingList(movie);
		return showList;
	}


	public void setAvailability(String idShowing, String[] seats, boolean availability) throws SQLException, IOException, SeatException {
		for(String s : seats) {
			PersistenceFacade.getInstance().changeAvailability(idShowing, s, availability);
		}
	}

	/* ShopCart management */
	public void updateShopCartItems(String id, String[] seats) throws SQLException, IOException, SeatException {

		this.shopCart.setIdSh(id);
		this.shopCart.setSeats(seats);
	}

	public ShopCart getShopCart () {
		return this.shopCart;
	}

	/* Purchase management */
	public double[] ticketsPrice(String showingID, String[] seats) throws SQLException, IOException, SeatException {
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

	public boolean buyTicket(String codeCard, String date, String cvc, String emailRecipient) throws SQLException, IOException, SeatException, MessagingException, PaymentException {
		List<Ticket> ticketList = createTickets(this.shopCart.getIdSh(), this.shopCart.getSeats());
		boolean result = ServiceFactory.getInstance().getPaymentAdapter().pay(getTotalPriceTickets(ticketList), codeCard, date, cvc);
		if(result) {
			MailSender.sendTicketMail(emailRecipient, genPDF(ticketList));
			this.shopCart.refresh();
			return true;

		} else
			return false;
	}

	private File genPDF(List<Ticket> ticketList) throws FileNotFoundException {
		PdfWriter writer = new PdfWriter("G20Ticket", new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
		PdfDocument pdfDocument = new PdfDocument(writer);
		pdfDocument.setTagged();
		Document document = new Document(pdfDocument);
		for(Ticket t : ticketList) {
			document.add(new Paragraph(t.toString()));
		}
		document.close();

		return new File("G20Ticket");
	}

	private double getTotalPriceTickets(List<Ticket> ticketList) {
		double total = 0.0;

		for(Ticket t : ticketList) {
			total += t.getTotalPrice();
		}
		return total;
	}


	//singleton method
	public static Cinema getCinema() {
		if (istance == null) {
			istance = new Cinema();
		}
		return istance;
	}
}
