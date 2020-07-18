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
		List<Ticket> ticketList = createTickets(shopCart.getIdSh(), shopCart.getSeats());
		boolean result = PaymentFactory.getInstance().getPaymentAdapter().pay(getTotalPriceTickets(ticketList), codeCard, date, cvc);
		//Demeter
		if(result) {
			MailSender.sendTicketMail(emailRecipient, ticketList);
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
		Set<Seat> sList = TheatreHandler.getInstance().getAllSeatsForShowing(showingID).keySet();

		List<Ticket> ticketList = new ArrayList<>();
		for(String s : seats) {
			for(Seat sL : sList)
				if(sL.getPosition().equalsIgnoreCase(s)) {
					ticketList.add(new Ticket(OIDCreator.getInstance().getTicketCode(),m.getMovie(), s, MovieShowingHandler.getInstance().getMovieShowing(showingID), (m.getPrice()*sL.getAddition())));
				}
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
		TicketPricingStrategy discount = PricingStrategyFactory.getInstance().getDiscount(code.toUpperCase());

		if(discount != null)
			return discount.getTotalPrice(price);

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
		Set<Seat> sList = TheatreHandler.getInstance().getAllSeatsForShowing(showingID).keySet();
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
	 * it permits to delete a tickets
	 * @param code ticket's code
	 * @param cardNumber card's number
	 * @throws ObjectNotFoundException
	 * @throws DeleteTicketException
	 */
	synchronized public void deleteTicket(String code, String cardNumber) throws SQLException, MessagingException, SearchException, FileNotFoundException, ObjectNotFoundException, DeleteTicketException{
		Ticket delTick = (Ticket)PersistenceFacade.getInstance().get(code, TicketsMapper.class);

		if(delTick.getShowing().getDate().isBefore(LocalDateTime.now()))
			throw new DeleteTicketException();
		else {
			MailSender.sendRefundMail(code, cardNumber, (delTick).getTotalPrice());
			TheatreHandler.getInstance().setAvailability(delTick.getShowing().getId(), true, delTick.getSeat());
			PersistenceFacade.getInstance().delete(code, TicketsMapper.class);
		}
	}


	private double getTotalPriceTickets(List<Ticket> ticketList) {
		double total = 0.0;

		for(Ticket t : ticketList) {
			total += t.getTotalPrice();
		}
		return total;
	}

	public ShopCart getShopCart () {
		return this.shopCart;
	}

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

	public static BuyTicketHandler getInstance() {
		if (instance == null) {
			instance = new BuyTicketHandler();
		}
		return instance;
	}
}
