package it.unipv.www.g20.model.theatre;

import java.util.Calendar;

import it.unipv.www.g20.model.exception.NotFoundException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.MovieShowing;

public interface Organizable {
	public void printShowingList();
	public boolean addMovieShowing(Movie movie, Calendar date);
	public boolean deleteMovieShowing(MovieShowing showing);
	public int searchMovieShowing(MovieShowing showing) throws NotFoundException;
}
