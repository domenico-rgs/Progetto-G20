package server.services.DB;

import java.util.ArrayList;

import org.hibernate.SessionFactory;

import server.domain.cinema.Movie;
import server.domain.showing.MovieShowing;
import server.domain.theatre.Theatre;

public class DBConnection {
	private SessionFactory sessionFactory;
	private CinemaServices cinemaServices;

	public DBConnection() {
		this.sessionFactory = HibernateUtil.getSessionFactory();
		cinemaServices = new CinemaServices(this.sessionFactory);
	}

	public void addMovie(Movie movie) {
		cinemaServices.addMovie(movie);
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
