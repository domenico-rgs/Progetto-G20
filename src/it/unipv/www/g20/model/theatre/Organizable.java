/**This interface organizes a cinema theatre. */
package it.unipv.www.g20.model.theatre;

import java.util.Date;

import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.movie.Movie;

public interface Organizable {
	/**
	 * Add a movie showing to the system
	 *
	 * @param movie movie to show
	 * @param date  date of the showing
	 * @throws SearchException
	 */
	public void addMovieShowing(Movie movie, Date date, Double price) throws SearchException;

	/**
	 * Delete a movie showing from the system
	 *
	 * @param date the date of the movie showing in the theatre to delete
	 * @throws SearchException
	 */
	public void deleteMovieShowing(Date date) throws SearchException;
}
