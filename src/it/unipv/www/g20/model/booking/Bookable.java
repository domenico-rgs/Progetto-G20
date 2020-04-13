package it.unipv.www.g20.model.booking;

public interface Bookable {
	public Ticket createTicket(String info);
	public boolean deleteTicket(String idTicket);
}
