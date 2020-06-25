package server.services.DB;

import java.util.ArrayList;

import org.hibernate.SessionFactory;

import server.domain.cinema.Movie;
import server.domain.cinema.TypeCategory;
import server.domain.exception.SearchException;
import server.domain.showing.MovieShowing;
import server.domain.theatre.Theatre;

public class DBConnection {
	private SessionFactory sessionFactory;
	private CinemaServices cinemaServices;

	public DBConnection() {
		this.sessionFactory = HibernateUtil.getSessionFactory();
		cinemaServices = new CinemaServices(this.sessionFactory);
	}

	public void addMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException {
		cinemaServices.addMovie(title, duration, plot, pathCover, category);	
	}

	public Theatre searchTheatre(String theatreName) {
		return cinemaServices.searchTheatre(theatreName);
	}
	
	public Movie searchMovie(String movieTitle) {
		return cinemaServices.searchMovie(movieTitle);
	}
	
	public MovieShowing searchShowing(String showingID) {
		return cinemaServices.searchShowing(showingID);
	}
	
	public void editShowing(String showing, Theatre theatre, double price){
		cinemaServices.editShowing(showing, theatre, price);
	}

	public ArrayList<MovieShowing> getShowingList(Movie movie) {
		return cinemaServices.getShowingList(movie);
	}

	public ArrayList<String> movieList(){
		return cinemaServices.movieList();
	}

	public void addTheatre(Theatre theatre) {
		cinemaServices.addTheatre(theatre);
	}

	public void addMovieShowing(MovieShowing show) {
		cinemaServices.addMovieShowing(show);
	}

	//manca? (si deve fare meglio, ora mi serve cosi per testare)
	public Movie getMovie() {
		return null;
	}

}
