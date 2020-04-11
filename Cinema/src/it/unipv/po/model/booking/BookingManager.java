package it.unipv.po.model.booking;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class BookingManager {
	
	private static BookingManager instance;
	private HashMap<Integer, Booking> bookingList; 
	
	
	/* Uso del Singleton pattern per evitare l'instaziamento di piu instanze
	 */
	private BookingManager() {
		bookingList = new HashMap<Integer, Booking>();
	}
	
	public static BookingManager getInstance() {
		
		if(instance == null) {
			instance = new BookingManager();
		}
		return instance;
	}
	//////// FINE SINGLETON /////////////
	
	// ricerca di un booking nella mappa tramite id (che si dovrà conoscere)
	public Booking getBooking(int id) {
		return bookingList.get(id);
	}
	
	/* Per informazioni su come usare LocalDate, guardare il commento sulla
	 * classe Booking
	 */
	public Booking getBooking(LocalDate date) {
		return null; //verificare come usare un for each in un hashmap
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
