package it.unipv.www.g20.model.ticket;

import java.util.HashMap;

public class TicketLedger {
	private HashMap<String, Ticket> ticketList;
	private static TicketLedger istanza = null;

	private TicketLedger() {
		ticketList=new HashMap<>();
	}

	public Ticket addTicketSale(Ticket ticket) {
		return ticketList.putIfAbsent(ticket.getCode(), ticket);
	}

	public Ticket removeTicketSale(String code) {
		return ticketList.remove(code);
	}

	// Metodo della classe impiegato per accedere al singleton
	public static TicketLedger getTicketLedger() {
		if (istanza == null) {
			istanza = new TicketLedger();
		}
		return istanza;
	}
}
