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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import server.MailSender;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.payment.ServiceFactory;
import server.domain.payment.ShopCard;
import server.services.DB.MoviesMapper;
import server.services.DB.OIDCreator;
import server.services.DB.PersistenceFacade;
import server.services.DB.ShowingsMapper;
import server.services.DB.TheatresMapper;
import server.services.DB.TicketsMapper;
import server.domain.cinema.*;
import server.domain.cinema.theatre.Seat;
import server.domain.cinema.theatre.Theatre;

/**
 * Facade controller for managing reservations in a cinema
 */
public class Cinema {
	private ShopCard shopCard;
	private Quotes quotes = new Quotes();

	private static Cinema istance = null;

	private Cinema() {
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
		s.editShowing(this.getTheatre(theatre), price);
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

	synchronized public void deleteTicket(String code, String cardNumber) throws SQLException, IOException, SeatException, MessagingException{
		MailSender.sendRefundMail(code, cardNumber, ((Ticket)PersistenceFacade.getInstance().get(code, TicketsMapper.class)).getTotalPrice());
		PersistenceFacade.getInstance().deleteTicket(code);
	}


	synchronized public String createMovieShowing(String movie, LocalDateTime date, String theatre, double price) throws SQLException, SearchException, IOException, SeatException {
		MovieShowing s = new MovieShowing(OIDCreator.getInstance().getShowingCode(), movie, date, getTheatre(theatre), price);
		PersistenceFacade.getInstance().addMovieShowing(s.getId(),s);

		return s.getId();
	}
	
	
	
	//da verificare
	synchronized private boolean controlOverlapping() {
		//get prenotazione
		//get film
		//orario prenotazione convertito in secondi + durata film convertito in secondi
		LocalDateTime date = LocalDateTime.of(2020, 7, 22, 18, 00, 01);   // da sostituire con la data che ricavo dal database
		ZonedDateTime zoneDate = date.atZone(ZoneId.of("Europe/Rome"));
		long millisDate1 = zoneDate.toInstant().toEpochMilli();
		int minutiFilm = 200;    // da sostituire con il getduration del film
		long millisDateFilm1 = TimeUnit.MINUTES.toMillis(minutiFilm);
		long dateEnd = (millisDate1 + millisDateFilm1); 
		//orario inizio -----> millisDateFilm1    orario fine ----> dateEnd
		//orario prenotazione casuale 121212313123
		long casual = 12144212;
		//ciclo for
		if(casual>= millisDateFilm1 && casual <= dateEnd) {
			return false;
		}
		//fuori dal ciclo
		return true;
		
	}

	synchronized public void deleteMovieShowing(String movie, String idShowing) throws SearchException {
		//OCCORRE CONTROLLARE CHE NON SIANO STATE FATTE PRENOTAZIONI PER QUESTA PROIEZIONE
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

	public void setAvailability(String idShowing, String[] seats, boolean availability) throws SQLException, IOException, SeatException {
		for(String s : seats) {
			PersistenceFacade.getInstance().changeAvailability(idShowing, s, availability);
		}
	}

	// METODI DI GESTIONE DELLO SHOPCARD //
	public void updateShopCardItems(String id, String[] seats) throws SQLException, IOException, SeatException {
		
		this.shopCard.setIdSh(id);
		this.shopCard.setSeats(seats);
	}

	public ShopCard getShopCard () {
		return this.shopCard;
	}

	private List<Ticket> createTickets(String showingID, String[] seats) throws SQLException, IOException, SeatException {
		MovieShowing m = getMovieShowing(showingID);
		List<Seat> sList = getFreeSeatsForShowing(showingID);

		List<Ticket> ticketList = new ArrayList<>();
		for(String s : seats)
			for(Seat sL : sList)
				if(sL.getPosition().equalsIgnoreCase(s))
					ticketList.add(new Ticket(OIDCreator.getInstance().getTicketCode(),m.getMovie(), s, getMovieShowing(showingID).getDate(), (m.getPrice()+sL.getAddition()*100)));
		PersistenceFacade.getInstance().addTickets(ticketList);


		for (Ticket t: ticketList) {
			this.shopCard.addTotal(t.getTotalPrice());;
		}
		return ticketList;

	}
	
	public List<Double> ticketsPrice(String showingID, String[] seats) throws SQLException, IOException, SeatException {
		List<Double> doubleList = new LinkedList<>();
		MovieShowing m = getMovieShowing(showingID);
		List<Seat> sList = getFreeSeatsForShowing(showingID);

		for(String s : seats) {
			for(Seat sL : sList) {
				if(sL.getPosition().equalsIgnoreCase(s))
					doubleList.add(m.getPrice()+sL.getAddition()*100);
			}
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

	public void setShopCardTotal() throws SQLException, IOException, SeatException {
		//resetto il carrello
		this.shopCard.setZeroTotal();
		
		double total = 0.0;
		MovieShowing m = getMovieShowing(this.shopCard.getIdSh());
		List<Seat> sList = getFreeSeatsForShowing(this.shopCard.getIdSh());

		for(String s : this.shopCard.getSeats())
			for(Seat sL : sList)
				if(sL.getPosition().equalsIgnoreCase(s))
					total+=m.getPrice()+sL.getAddition()*100;
		
		System.out.println(total);

		this.shopCard.addTotal((double) Math.round(total * 100)/100);;
	}
	
	///// METODI DA IMPLEMENTARE ///////
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

	public static Cinema getCinema() {
		if (istance == null)
			istance = new Cinema();
		return istance;
	}
}
