package it.unipv.www.g20.model.booking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import it.unipv.www.g20.model.exception.NotFoundException;

public class Booking implements Bookable {
	private static int generateIdTicket = 0;
	private String idBooking;
	private final ArrayList<Ticket> ticketList;
	private final Calendar date;

	public Booking(Calendar date) {
		idBooking = UUID.randomUUID().toString();
		this.date = date;
		ticketList = new ArrayList<>();
	}

	@Override
	public Ticket createTicket(String info) {
		final Ticket ticket = new Ticket(idBooking+generateIdTicket, info);
		ticketList.add(ticket);
		generateIdTicket++;
		return ticket;
	}
	
	public boolean createTicket(String info, int numero) {
		for(int i=0; i<numero; i++) {
			final Ticket ticket = new Ticket(idBooking+generateIdTicket, info);
			ticketList.add(ticket);
			generateIdTicket++;
		}
		return true;
	}

	public Ticket getTicket(int idTicket) {
		return ticketList.get(idTicket);
	}

	public int searchTicket(String id) throws NotFoundException {
		for(int i=0; i<ticketList.size(); i++) {
			if(ticketList.get(i).getId().equalsIgnoreCase(id))
				return i;
		}
		throw new NotFoundException("Ticket not found!");
	}

	@Override
	public boolean deleteTicket(String idTicket) {
		try {
			final int pos = searchTicket(idTicket);
			ticketList.remove(pos);
			return true;
		}catch(final NotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public String getIdBooking() {
		return idBooking;
	}


	public String getDateBooking() {
		return date.toString();
	}
	
	public String printTicket() {
		String s="";
		for(int i=0; i<ticketList.size(); i++) {
			s+=ticketList.get(i).toString();
		}
		return s+"\n";
	}

	@Override
	public String toString() {
		return "Booking:\n[idBooking=" + idBooking + "\n Ticket=" + printTicket() + "\n date=" + date + "]";
	}
}
