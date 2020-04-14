/** this interface organizes all activities of the Cinema*/
package it.unipv.www.g20.model.cinema;

import java.util.Calendar;

import it.unipv.www.g20.model.booking.Booking;
import it.unipv.www.g20.model.movie.TypeCategory;
import it.unipv.www.g20.model.operator.Operator;
import it.unipv.www.g20.model.operator.TypeOperator;

public interface Manageable {

	/**It permits to add a theatre.
	 * @param name theatre's name
	 * @param row number of rows of seats
	 * @param column number of seat columns
	 * @return true if theatre is added.*/
	public boolean addTheatre(String name, int row, int column);
	
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
	public Booking addBooking(Calendar date);
	
	/**It permits to delete a booking.
	 * @param nome booking's name
	 * @return true if booking is deleted */
	public boolean deleteBooking(String nome);
	
	/**
	 * It permits to add a Cinema Cashier or a Cinema Manager.
	 * 
	 * @param type It refers to the type of operator: cashier or manager
	 * @return true if an operator is added.
	 */
	public Operator addOperator(TypeOperator type);
	
	/**
	 * It permits to delete a cashier or a manager.
	 * 
	 * @param id operator's id
	 * @return true if the operator is deleted.
	 */
	public boolean deleteOperator(int id);
}
