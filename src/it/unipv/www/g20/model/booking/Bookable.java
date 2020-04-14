/** This interface allows to create and delete ticket.*/
package it.unipv.www.g20.model.booking;

import it.unipv.www.g20.model.exception.NotFoundException;

public interface Bookable {
	/**
	 * It permits to create a ticket.
	 * @param info ticket's information 
	 * @return the created ticket
	 */
	public Ticket createTicket(String info);
	
	/**
	 * It permits to delete a ticket.
	 * @param idTicket ticket's id
	 * @return true if ticket is deleted
	 */
	public boolean deleteTicket(String idTicket);
	
	/** 
	 * It permits to search a ticket in the booking
	 * @param id ticket's id
	 * @return index of the ticket in the list into the booking
	 * */
	public int searchTicket(String id) throws NotFoundException;
}
