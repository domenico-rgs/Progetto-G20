package server.domain.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.domain.cinema.Movie;
import server.domain.cinema.TypeCategory;
import server.exception.ObjectNotFoundException;
import server.exception.SearchException;
import server.services.persistence.MoviesMapper;
import server.services.persistence.PersistenceFacade;

public class MovieHandler {
	private static MovieHandler instance = null;

	private MovieHandler() {}

	/**
	 * This method permits to create movie
	 * @param title movie's title
	 * @param duration movie's duration
	 * @param plot movie's plot
	 * @param pathCover cover's path
	 * @param category movie's category
	 */
	synchronized public void createMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException, SQLException{
		Movie m = new Movie(title, duration, plot, pathCover, category);
		PersistenceFacade.getInstance().put(title,MoviesMapper.class,m);
	}

	/**
	 * It permits to modify a movie
	 * @param title movie's title
	 * @param pathCover cover's path
	 * @param plot movie's plot
	 * @param category movie's category
	 * @throws ObjectNotFoundException
	 */
	synchronized public void editMovie(String title, String pathCover, String plot, TypeCategory category) throws SearchException, SQLException, ObjectNotFoundException{
		Movie m = getMovie(title);
		m.editMovie(pathCover, plot, category);
		PersistenceFacade.getInstance().updateTable(MoviesMapper.class, m, title);
	}

	/**
	 * it permits to delete a movie
	 * @param title movie's title
	 */
	synchronized public void deleteMovie(String title) throws SearchException, SQLException{
		PersistenceFacade.getInstance().delete(title, MoviesMapper.class);
	}

	synchronized public Movie getMovie(String title) throws SQLException, ObjectNotFoundException{
		return (Movie) PersistenceFacade.getInstance().get(title, MoviesMapper.class);
	}


	synchronized public List<String> getMovieList() throws SQLException{
		List<String> titleList = new ArrayList<>();
		titleList.addAll(PersistenceFacade.getInstance().getAllMovies().keySet());
		return titleList;
	}
	
	/**
	 * Given a string, it searches for movies that contain it in the title
	 * @param search string that must be contained in the title
	 * @return
	 * @throws ObjectNotFoundException
	 */
	public List<Movie> searchMovieForString(String search) throws ObjectNotFoundException {
		List<server.domain.cinema.Movie> movieList = new ArrayList<>();

		try {
			List<String> movieTitle = MovieHandler.getInstance().getMovieList();

			for (String title: movieTitle)
				if (title.toLowerCase().contains(search.toLowerCase())) {
					movieList.add(MovieHandler.getInstance().getMovie(title));
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return movieList;
	}

	public static MovieHandler getInstance() {
		if (instance == null) {
			instance = new MovieHandler();
		}
		return instance;
	}
}
