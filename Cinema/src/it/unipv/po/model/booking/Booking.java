package it.unipv.po.model.booking;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

//import java.util.Date;

public class Booking {
	
	//attributi
	private static int generateIdBooking = 0;
	private int idBooking;
	private HashMap<Integer, Ticket> ticketList;
	private LocalDate date;
	
	/* LocalDate non si può istanziare. Per ricavare la data
	 * usare il metodo statico LocalDate.now()
	 * se voglio confrontare la corrispondeza tra due date, usare il metodo
	 * di equals() (come le stringhe)
	 */
	private LocalDate dateBooking;
	
	public Booking(LocalDate date) {
		
		this.date = date;
		ticketList = new HashMap<>();
		
	}
	
	public boolean addTicket(Date dateTicket, double price, String movieTicket, String theaterName, int seatNumber) {
		try{
			Ticket ticket = new Ticket(dateTicket, price, movieTicket, theaterName, seatNumber);
			ticketList.put(ticket.getIdTicket(), ticket);
			return true;	
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public Ticket getTicket(int idTicket) {
		return ticketList.get(idTicket);
	}	
	
	public boolean removeTicket(int idTicket) {
		if(ticketList.remove(idTicket) == null) {
			return false;
		}
		return true;
	}

	
	//getter and setter utili
	public int getIdBooking() {
		return idBooking;
	}


	public String getDateBooking() {
		return date.toString();
	}
	
	
	
}
