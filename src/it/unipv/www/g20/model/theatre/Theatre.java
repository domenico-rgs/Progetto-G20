package it.unipv.www.g20.model.theatre;

import java.util.Date;
import java.util.HashMap;

import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.MovieShowing;

/**
 * The theatre of a Cinema, allows you to manage its projections
 */
public class Theatre implements Organizable {
	private String name;
	private final HashMap<Date, MovieShowing> movieShowingList;
	private final int row;
	private final int col;

	public Theatre(String theaterName, int row, int column) {
		movieShowingList = new HashMap<>();
		setName(theaterName);
		this.row = row;
		col = column;
	}
	
	/**
	 * Prints all the projections programmed in the theatre
	 * @return a string with the list of projections
	 */
	@SuppressWarnings("deprecation")
	public String printMovieShowing() {
		String s="";
		for(Date d : movieShowingList.keySet())
			s+=d.toLocaleString()+" - "+movieShowingList.get(d).getMovie().getTitle()+"\n";
		return s;
	}

	@Override
	public void addMovieShowing(Movie movie, Date date, Double price) throws SearchException {
		if (movieShowingList.containsKey(date))
			throw new SearchException("È già presente una proiezione nella data specificata");

		//!!! occorre controllare che due date non si sovrappongano
		movieShowingList.put(date, new MovieShowing(movie, date, price, row, col));
	}

	@Override
	public void deleteMovieShowing(Date date) throws SearchException {
		if (!(movieShowingList.containsKey(date)))
			throw new SearchException("Non è presente alcuna proiezione nella data specificata");

		movieShowingList.remove(date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Theatre other = (Theatre) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * @return the capacity of the theatre
	 */
	public int getCapacity() {
		return row * col;
	}

	public int getColumn() {
		return col;
	}

	public String getName() {
		return name;
	}

	public int getRow() {
		return row;
	}

	/**
	 * Returns, if any, the specified movie show scheduled in the theatre given the projection date 
	 * (only a projection in a specific room is possible on a specific date)
	 * @param d the date of the projection
	 * @return the MovieShowing if exists
	 * @throws SearchException
	 */
	public MovieShowing getShow(Date d) throws SearchException {
		if (!(movieShowingList.containsKey(d)))
			throw new SearchException("Non è presente alcuna proiezione nella data specificata");

		return movieShowingList.get(d);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Theatre: " + name;
	}
}
