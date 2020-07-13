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
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.mysql.cj.jdbc.SuspendableXAConnection;

import server.MailSender;
import server.domain.cinema.theatre.Seat;
import server.domain.cinema.theatre.Theatre;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.payment.ServiceFactory;
import server.domain.payment.ShopCart;
import server.domain.payment.discount.PricingStrategyFactory;
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
	private ShopCart shopCard;
	private Quotes quotes = new Quotes();

	private static Cinema istance = null;

	private Cinema() {
		shopCard = new ShopCart();
	}

	synchronized public void createTheatre(String name, String config) throws SQLException, IOException, SeatException  {
		Theatre t = new Theatre(name);
		String file = t.createConfigFile(config);
		t.createSeats(file);
		PersistenceFacade.getInstance().addTheatre(name,t);
	}

	synchronized public void createDiscountCode(String code, double percent) throws SQLException, IOException, SeatException  {
		PricingStrategyFactory.getInstance().createDiscountCode(code, percent);
	}
	
	synchronized public double applyDiscountOnPrice(String code, double price) throws SQLException, IOException, SeatException{
		return PricingStrategyFactory.getInstance().getCodeStrategy(code).getTotalPrice(price);	
		
	}
	
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

	synchronized public void createMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException, SQLException, IOException, SeatException{
		Movie m = new Movie(title, duration, plot, pathCover, category);
		PersistenceFacade.getInstance().addMovie(title,m);
	}

	synchronized public void deleteTicket(String code, String cardNumber) throws SQLException, IOException, SeatException, MessagingException{
		MailSender.sendRefundMail(code, cardNumber, ((Ticket)PersistenceFacade.getInstance().get(code, TicketsMapper.class)).getTotalPrice());
		PersistenceFacade.getInstance().deleteTicket(code);
	}


	synchronized public String createMovieShowing(String movie, LocalDateTime date, String theatre, double price) throws SQLException, SearchException, IOException, SeatException {
		String s = "";
		
		//passo la data, non creo la showing prima!!!
		boolean test = controlOverlapping(date, theatre, movie);
		if(test) {
			PersistenceFacade.getInstance().addMovieShowing(ms.getId(),ms);
		} else
			s = "null";
		return s;
	}

	//da verificare (domenico ci sto pensando io)
	private boolean controlOverlapping(LocalDateTime date, String theatre, String movie) throws SQLException, IOException, SeatException {
		
		//in millisecondi
		ZonedDateTime zdt = date.atZone(ZoneId.systemDefault());
		long dateShowingSec = zdt.toInstant().getEpochSecond();
		long movieDurationSec = this.getMovie(movie).getDuration()*60;
		
		//ricavo le proiezioni alla data attuale (spero che funzioni)
		List<MovieShowing> showingList = PersistenceFacade.getInstance().getMovieShowingList(theatre, date);
		
		//controllo se non ci sono film che DOPO si accavallano
		long durationShowingToControll;
		for (MovieShowing sh: showingList) {
			//controllo solo se le sale sono uguali
			if (!(sh.getTheatreName().contentEquals(theatre))) continue;
			
			zdt = sh.getDate().atZone(ZoneId.systemDefault());
			durationShowingToControll = zdt.toInstant().getEpochSecond();
			
			//se trovo un film dopo (intuitivo?)
			if ((durationShowingToControll - dateShowingSec) < movieDurationSec) 
				return false;
		}
		
		//controllo se non ci sono film che PRIMA SI accavallano
		long filmToControllDuration;
		for(MovieShowing sh: showingList) {
			
			//se la sala è diversa skippo
			if (!(sh.getTheatreName().contentEquals(theatre))) continue;
			
			zdt = sh.getDate().atZone(ZoneId.systemDefault());
			durationShowingToControll = zdt.toInstant().getEpochSecond();
			
			//se la data è successiva non controllo
			if (durationShowingToControll > dateShowingSec) continue;
			
			filmToControllDuration = getMovie(sh.getMovie()).getDuration()*60;
			
			//se si accavalla con una precedente
			if ((durationShowingToControll + filmToControllDuration) >= dateShowingSec)
				return false;
		}
		
		
		
		return true;
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

	public HashMap<Seat, Boolean> getAllSeatsForShowing(String idShowing) throws SQLException, IOException, SeatException {
		return PersistenceFacade.getInstance().getAvailabilityList(idShowing);
	}

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

	public void setAvailability(String idShowing, String[] seats, boolean availability) throws SQLException, IOException, SeatException {
		for(String s : seats)
			PersistenceFacade.getInstance().changeAvailability(idShowing, s, availability);
	}

	// METODI DI GESTIONE DELLO SHOPCARD //
	public void updateShopCardItems(String id, String[] seats) throws SQLException, IOException, SeatException {

		this.shopCard.setIdSh(id);
		this.shopCard.setSeats(seats);
	}

	public ShopCart getShopCard () {
		return this.shopCard;
	}

	private List<Ticket> createTickets(String showingID, String[] seats) throws SQLException, IOException, SeatException {
		MovieShowing m = getMovieShowing(showingID);
		Set<Seat> sList = getAllSeatsForShowing(showingID).keySet();

		List<Ticket> ticketList = new ArrayList<>();
		for(String s : seats)
			for(Seat sL : sList)
				if(sL.getPosition().equalsIgnoreCase(s))
					ticketList.add(new Ticket(OIDCreator.getInstance().getTicketCode(),m.getMovie(), s, getMovieShowing(showingID), (m.getPrice()*sL.getAddition())));
		PersistenceFacade.getInstance().addTickets(ticketList);
		return ticketList;
	}

	public double[] ticketsPrice(String showingID, String[] seats) throws SQLException, IOException, SeatException {
		MovieShowing m = getMovieShowing(showingID);
		Set<Seat> sList = getAllSeatsForShowing(showingID).keySet();
		double[] doubleList = new double[seats.length];

		int count = 0;
		for(String s : seats) {
			for(Seat sL : sList)
				if(sL.getPosition().equalsIgnoreCase(s)) {
					double price = m.getPrice()*sL.getAddition();
					doubleList[count] = price;
					this.shopCard.addTotal(price);
				}
			count++;
		}
		return doubleList;
	}

	private File genPDF(List<Ticket> ticketList) throws FileNotFoundException {
		PdfWriter writer = new PdfWriter("G20Ticket", new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
		PdfDocument pdfDocument = new PdfDocument(writer);
		pdfDocument.setTagged();
		Document document = new Document(pdfDocument);
		for(Ticket t : ticketList)
			document.add(new Paragraph(t.toString()));
		document.close();

		return new File("G20Ticket");
	}


	public boolean buyTicket(String codeCard, String date, String cvc, String emailRecipient) throws SQLException, IOException, SeatException, MessagingException {
		List<Ticket> ticketList = createTickets(this.shopCard.getIdSh(), this.shopCard.getSeats());
		boolean result = ServiceFactory.getInstance().getPaymentAdapter().pay(getTotalPriceTickets(ticketList), codeCard, date, cvc);
		if(result) {
			MailSender.sendTicketMail(emailRecipient, genPDF(ticketList));
			this.shopCard.refresh();
			return true;

		} else
			return false;
	}

	private double getTotalPriceTickets(List<Ticket> ticketList) {
		double total = 0.0;

		for(Ticket t : ticketList)
			total += t.getTotalPrice();
		return total;
	}

	synchronized public void deleteTheatre(String name) throws SearchException, SQLException, IOException, SeatException{
		PersistenceFacade.getInstance().deleteTheatre(name);
	}

	synchronized public void deleteMovie(String title) throws SearchException, SQLException, IOException, SeatException{
		PersistenceFacade.getInstance().deleteMovie(title);
	}

	synchronized public void deleteMovieShowing(String idShowing) throws SearchException, SQLException, IOException, SeatException {
		PersistenceFacade.getInstance().deleteMovieShowing(idShowing);
	}

	public static Cinema getCinema() {
		if (istance == null)
			istance = new Cinema();
		return istance;
	}
}
