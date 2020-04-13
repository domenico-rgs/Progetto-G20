/**This interface organizes a cinema theatre. */
package it.unipv.www.g20.model.theatre;

import java.util.Calendar;

import it.unipv.www.g20.model.exception.NotFoundException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.MovieShowing;

public interface Organizable {
	/**It permits to print the showing list. */
	public void printShowingList();
	
	/**It permits to add a movie showing.
	 *@param movie movie to show
	 *@param date date of the showing
	 *@return true if the showing is confirmed.  */
	public boolean addMovieShowing(Movie movie, Calendar date);
	
	/**It permits to delete a movie showing
	 * @param showing the movie showing to delete
	 * @return true if the showing is deleted */
	public boolean deleteMovieShowing(MovieShowing showing);
	
	/**It permits to search a movie showing
	 * @param showing the showing to search
	 * @return return the position of the showing in the showing list.*/
	public int searchMovieShowing(MovieShowing showing) throws NotFoundException;
}
