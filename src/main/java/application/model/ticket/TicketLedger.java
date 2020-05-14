package application.model.ticket;

import java.util.HashMap;

import application.model.exception.SearchException;

public class TicketLedger {
	private HashMap<String, Ticket> ticketList;
	private static TicketLedger istance = null;

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

	public Ticket searchTicket(String id) throws SearchException{
		if (!(ticketList.containsKey(id)))
			throw new SearchException(id+"'s not exists.");
		return ticketList.get(id);
	}

	public static TicketLedger getTicketLedger() {
		if (istance == null)
			istance = new TicketLedger();
		return istance;
	}
}
