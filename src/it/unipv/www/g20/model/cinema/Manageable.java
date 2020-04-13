/** this interface organizes all activities of the Cinema*/
package it.unipv.www.g20.model.cinema;

import java.util.Calendar;

import it.unipv.www.g20.model.movie.TypeCategory;

public interface Manageable {
	/**It permits to add a theatre.
	 * @param name theatre's name
	 * @param capienza theatre's capacity
	 * @return true if theatre is added.*/
	public boolean addTheatre(String name, int capienza);
	
	/**It permits to delete a theatre.
	 * @param nome theatre's name
	 * @return true if theatre is deleted */
	public boolean deleteTheatre(String nome);
	
	/**It permits to add a movie.
	 * @param title movie's title
	 * @param duration movie's duration
	 * @param category movie's category
	 * @param ticketPrice ticket's price for this movie
	 * @return true if movie is added */
	public boolean addMovie(String title, int duration, TypeCategory category, Double ticketPrice);
	
	/**It permits to delete a movie 
	 * @param title movie's title
	 * @return true if movie is deleted.*/
	public boolean deleteMovie(String title);
	
	/**It permits to add a booking.
	 * @param date booking's date
	 * @return true if booking is added. */
	public boolean addBooking(Calendar date);
	
	/**It permits to delete a booking.
	 * @param nome booking's name
	 * @return true if booking is deleted */
	public boolean deleteBooking(String nome);
}
