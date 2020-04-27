package it.unipv.www.g20.model.ticket;

import java.util.HashMap;

import it.unipv.www.g20.model.exception.SearchException;

public class TicketLedger {
	private HashMap<String, Ticket> ticketList;
	private static TicketLedger istanza = null;

	private TicketLedger() {
		ticketList=new HashMap<>();
	}

	public Ticket addTicketSale(Ticket ticket) throws SearchException{
		if(ticketList.putIfAbsent(ticket.getCode(), ticket)!=null)
			throw new SearchException("Ticket already exists");
		return ticketList.putIfAbsent(ticket.getCode(), ticket);
	}

	public Ticket removeTicketSale(String code) throws SearchException{
		if (!(ticketList.containsKey(code)))
			throw new SearchException(code+"'s not exists.");
		return ticketList.remove(code);
	}

	public Ticket searchTicket(String id) {
		return ticketList.get(id);
	}

	// Metodo della classe impiegato per accedere al singleton
	public static TicketLedger getTicketLedger() {
		if (istanza == null) {
			istanza = new TicketLedger();
		}
		return istanza;
	}
}
