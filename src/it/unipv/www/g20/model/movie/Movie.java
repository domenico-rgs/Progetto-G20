package it.unipv.www.g20.model.movie;

import java.util.Date;
import java.util.HashMap;

import it.unipv.www.g20.model.theatre.Theatre;

/**
 * This class is referred to a movie
 * @see MovieShowing
 */
public class Movie {
	private String title;
	private int duration; //in minutes
	private TypeCategory category;
	private String plot;
	private HashMap<String, MovieShowing> showingList;

	public Movie(String title, int duration) {
		showingList = new HashMap<>();
		setTitle(title);
		setDuration(duration);
		setPlot("");
	}

	public MovieShowing addMovieShowing(Date date, Theatre theatre, Double price) {
		MovieShowing tmp = new MovieShowing(date, theatre, price);
		return showingList.put(tmp.getId(), tmp);
	}

	public MovieShowing deleteMovieShowing(String id) {
		return showingList.remove(id);
	}

	/**
	 * Prints all the projections programmed in the theatre
	 * @return a string with the list of projections
	 */
	public String printMovieShowing() {
		String s="";
		for(String key : showingList.keySet()) {
			s+=showingList.get(key).toString()+"\n";
		}
		return s;
	}

	public MovieShowing searchShowing(String id) {
		return showingList.get(id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public TypeCategory getCategory() {
		return category;
	}

	public int getDuration() {
		return duration;
	}

	public String getPlot() {
		return plot;
	}

	public String getTitle() {
		return title;
	}

	public void setCategory(TypeCategory category) {
		this.category = category;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		String s = "MOVIE INFORMATION";
		s += " Title: " + title + "\n";
		s += " Duration: " + duration + " minutes\n";
		s += " Category: " + category.toString().toLowerCase() + "\n";
		s += " Plot: \n" + plot + "\n"; //fare qualcosa per mandare a capo
		return s;
	}
}
