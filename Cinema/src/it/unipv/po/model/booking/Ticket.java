package it.unipv.po.model.booking;

import java.util.Date;

public class Ticket {
	// attributi
	private static int idTicketGeneral = 0;
	private int idTicket;
	private Date dateTicket;
	private double price;
	private String movieTicket;
	private String theaterName;
	private int seatNumber;
	
	

	
	public Ticket( Date dateTicket, double price, String movieTicket, String theaterName, int seatNumber) {
		this.dateTicket = dateTicket;
		this.price = price;
		this.movieTicket = movieTicket;
		this.theaterName = theaterName;
		this.seatNumber = seatNumber;
		setIdTicket(idTicketGeneral);
		idTicketGeneral++;
	}




	public int getIdTicket() {
		return idTicket;
	}




	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}
	
	
	
}
