package it.unipv.po.model.booking;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class BookingManager {
	
	private static BookingManager instance;
	private HashMap<Integer, Booking> bookingList; 
	
	
	public BookingManager() {
		bookingList = new HashMap<Integer, Booking>();
	}
	
	
	// ricerca di un booking nella mappa tramite id (che si dovrà conoscere)
	public Booking getBooking(int id) {
		return bookingList.get(id);
	}
	
	/* Per informazioni su come usare LocalDate, guardare il commento sulla
	 * classe Booking
	 * metodo da implementare
	 */
	public Booking getBooking(LocalDate date) {
		return null; 
	}
	
	public boolean removeBooking(int id) {
		if(bookingList.remove(id) == null) {
			return false;
		}
		return true;
	}
	
	public void addBooking() {
		Booking book = new Booking(LocalDate.now());
		bookingList.put(book.getIdBooking(), book);
	}
	
}
