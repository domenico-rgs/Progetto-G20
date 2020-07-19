package server.domain.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import server.domain.cinema.MovieShowing;
import server.domain.cinema.Ticket;
import server.domain.cinema.theatre.Seat;
import server.domain.payment.ShopCart;
import server.domain.payment.discount.PricingStrategyFactory;
import server.domain.payment.discount.TicketPricingStrategy;
import server.exception.DeleteTicketException;
import server.exception.ObjectNotFoundException;
import server.exception.PaymentException;
import server.exception.SearchException;
import server.services.mail.MailSender;
import server.services.payment.PaymentFactory;
import server.services.persistence.OIDCreator;
import server.services.persistence.PersistenceFacade;
import server.services.persistence.TicketsMapper;

public class BuyTicketHandler {
	private ShopCart shopCart;
	private static BuyTicketHandler instance = null;

	private BuyTicketHandler() {
		shopCart = new ShopCart();
	}

	/**
	 * it permits to buy a ticket
	 * @param codeCard code of a card
	 * @param date expiry date
	 * @param cvc card's cvc
	 * @param emailRecipient addressee of the email
	 * @return true if ticket is bought
	 * @throws ObjectNotFoundException
	 */
	synchronized public boolean buyTicket(String codeCard, String date, String cvc, String emailRecipient) throws SQLException, MessagingException, PaymentException, FileNotFoundException, ObjectNotFoundException {
		if(MovieShowingHandler.getInstance().getMovieShowing(shopCart.getIdSh()).getDate().isBefore(LocalDateTime.now()))
			throw new PaymentException("You are trying to buy a ticket for an old showing");

		List<Ticket> ticketList = createTickets(shopCart.getIdSh(), shopCart.getSeats());
		boolean result = PaymentFactory.getInstance().getPaymentAdapter().pay(getTotalPriceTickets(ticketList), codeCard, date, cvc);

		if(result) {
			MailSender.sendTicketMail(emailRecipient, ticketList);
			TheatreHandler.getInstance().setAvailability(shopCart.getIdSh(), false, shopCart.getSeats());
			shopCart.refresh();
			return true;
		} else
			return false;
	}

	/**
	 * it creates a list of tickets
	 * @param showingID showing's id
	 * @param seats booked seats
	 * @return a list of tickets
	 * @throws ObjectNotFoundException
	 */
	private List<Ticket> createTickets(String showingID, String[] seats) throws SQLException, ObjectNotFoundException {
		MovieShowing m = MovieShowingHandler.getInstance().getMovieShowing(showingID);

		List<Ticket> ticketList = new ArrayList<>();
		for(int i = 0; i<seats.length; i++) {
			Seat s = getShowingSeat(showingID, seats[i]);
			ticketList.add(new Ticket(OIDCreator.getInstance().getTicketCode(),m.getMovie(), seats[i],
					MovieShowingHandler.getInstance().getMovieShowing(showingID), (double) Math.round((m.getPrice()*s.getAddition()) * 100) / 100));
		}
		PersistenceFacade.getInstance().addTickets(ticketList);
		return ticketList;
	}

	/**
	 * it permits to apply a discount
	 * @param code discount's code
	 * @param price price without discount
	 * @return discounted price
	 * @throws ObjectNotFoundException
	 */
	synchronized public double applyDiscountOnPrice(String code, double price) throws SQLException, PaymentException, ObjectNotFoundException{
		if (shopCart.hasCode(code))
			return -1.0;

		TicketPricingStrategy discount = PricingStrategyFactory.getInstance().getDiscount(code.toUpperCase());

		if(discount != null) {
			double finalPrice = discount.getTotalPrice(price);
			shopCart.setTotal(finalPrice);
			shopCart.addCode(code);
			return finalPrice;
		}

		throw new PaymentException("Discount code not found");
	}

	/**
	 * this method permits to obtain the final cost of the ticket
	 * @param showingID showing's id
	 * @param seats bought seats
	 * @return final price
	 * @throws ObjectNotFoundException
	 */
	public synchronized double[] ticketsPrice(String showingID, String[] seats) throws SQLException, ObjectNotFoundException {
		MovieShowing m = MovieShowingHandler.getInstance().getMovieShowing(showingID);
		double[] doubleList = new double[seats.length];

		for(int i = 0; i<seats.length; i++) {
			Seat s = getShowingSeat(showingID, seats[i]);
			double price = (double) Math.round((m.getPrice()*s.getAddition()) * 100) / 100;
			doubleList[i] = price;
			shopCart.addTotal(price);
		}
		return doubleList;
	}

	private Seat getShowingSeat(String showingID, String seat) throws SQLException {
		Set<Seat> sList = TheatreHandler.getInstance().getAllSeatsForShowing(showingID).keySet();
		for(Seat sL : sList) {
			if(sL.getPosition().equalsIgnoreCase(seat))
				return sL;
		}
		return null; //it should never get here
	}

	/**
	 * Allows you to request a refund if the projection still exists and the date is later than the current one
	 * @param code ticket's code
	 * @param cardNumber card's number
	 * @throws ObjectNotFoundException
	 * @throws DeleteTicketException
	 */
	synchronized public void refundRequestTicket(String code, String cardNumber) throws SQLException, MessagingException, SearchException, FileNotFoundException, ObjectNotFoundException, DeleteTicketException{
		Ticket delTick = (Ticket)PersistenceFacade.getInstance().get(code, TicketsMapper.class);
		
		if(delTick.getShowing() == null || delTick.getDate().isBefore(LocalDateTime.now()))
			throw new DeleteTicketException();
		
		MailSender.sendRefundMail(code, cardNumber, (delTick).getTotalPrice());
	}
	
	/**
	 * Delete a ticket from the system and if still present the projection changes the availability of the seat to which it refers
	 * @param code
	 * @throws SQLException
	 * @throws ObjectNotFoundException
	 * @throws SearchException
	 */
	synchronized public void deleteTicket(String code) throws SQLException, ObjectNotFoundException, SearchException {
		Ticket delTick = (Ticket)PersistenceFacade.getInstance().get(code, TicketsMapper.class);

		if(delTick.getShowing() != null)
			TheatreHandler.getInstance().setAvailability(delTick.getShowingId(), true, delTick.getSeat());
		PersistenceFacade.getInstance().delete(code, TicketsMapper.class);
	}


	private double getTotalPriceTickets(List<Ticket> ticketList) {
		double total = 0.0;

		for(Ticket t : ticketList) {
			total += t.getTotalPrice();
		}
		return (double) Math.round(total * 100) / 100;
	}

	/**
	 * it updates the shop cart
	 * @param id showing's id
	 * @param seats booked seats
	 */
	synchronized public String updateShopCartItems(String id, String[] seats) throws SQLException {
		shopCart.refresh();
		shopCart.setIdSh(id);
		shopCart.setSeats(seats);

		return shopCart.generateID();
	}

	public String [] getSelectedSeats() {
		return shopCart.getSeats();
	}

	public String getShopCartID() {
		return shopCart.getID();
	}

	public String getShopCartIdSh() {
		return shopCart.getIdSh();
	}

	public String getShopCartTotal() {
		return String.valueOf(shopCart.getTotal());
	}

	public static BuyTicketHandler getInstance() {
		if (instance == null) {
			instance = new BuyTicketHandler();
		}
		return instance;
	}
}
