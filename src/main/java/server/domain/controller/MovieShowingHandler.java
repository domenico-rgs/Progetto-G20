package server.domain.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import server.domain.cinema.MovieShowing;
import server.exception.ObjectNotFoundException;
import server.exception.OverlapException;
import server.exception.SearchException;
import server.exception.SeatException;
import server.services.persistence.OIDCreator;
import server.services.persistence.PersistenceFacade;
import server.services.persistence.ShowingsMapper;

public class MovieShowingHandler {
	private static MovieShowingHandler instance = null;

	private MovieShowingHandler() {}

	/**
	 * it creates a movie showing
	 * @param movie showed movie
	 * @param date showing's date
	 * @param theatre showing's theatre
	 * @param price showing's price
	 * @return showing's id
	 * @throws ObjectNotFoundException
	 */
	synchronized public String createMovieShowing(String movie, LocalDateTime date, String theatre, double price) throws SQLException, SearchException, OverlapException, IOException, SeatException, ObjectNotFoundException {
		controlOverlapping(date, theatre, movie);
		MovieShowing sh = new MovieShowing(OIDCreator.getInstance().getShowingCode(), movie, date, TheatreHandler.getInstance().getTheatre(theatre), price);
		PersistenceFacade.getInstance().put(sh.getId(),ShowingsMapper.class,sh);
		return sh.getId();
	}

	/**
	 * it modifies a showing
	 * @param showing showing's id
	 * @param theatre showing's theatre
	 * @param price showing's price
	 * @throws ObjectNotFoundException
	 */
	synchronized public void editShowing(String showing, String theatre, double price) throws SearchException, SQLException, ObjectNotFoundException {
		MovieShowing s = getMovieShowing(showing);
		s.editShowing(TheatreHandler.getInstance().getTheatre(theatre), price);
		PersistenceFacade.getInstance().updateTable(ShowingsMapper.class, s, showing);
	}

	/**
	 * This method deletes a movieshowing
	 * @param idShowing showing's id
	 */
	synchronized public void deleteMovieShowing(String idShowing) throws SearchException, SQLException {
		PersistenceFacade.getInstance().delete(idShowing, ShowingsMapper.class);
	}

	synchronized public MovieShowing getMovieShowing(String id) throws SQLException, ObjectNotFoundException{
		return (MovieShowing) PersistenceFacade.getInstance().get(id, ShowingsMapper.class);
	}

	synchronized public List<MovieShowing> getAllShowingList() throws SQLException {
		return PersistenceFacade.getInstance().getAllMovieShowings();
	}

	synchronized public List<MovieShowing> getMovieShowingList(String movie) throws SQLException {
		List<MovieShowing> showList = PersistenceFacade.getInstance().getMovieShowingList(movie);
		return showList;
	}

	/**
	 * This method controls that each showing is not superimposed to others
	 * @param date showing's date
	 * @param theatre showing's theatre
	 * @param movie showing's movie
	 * @throws ObjectNotFoundException
	 */
	// gia ridotto al minimo, richiede una certa complessità ma non esistono metodi migliori di controllo a mio parere
	private void controlOverlapping(LocalDateTime date, String theatre, String movie) throws SQLException, OverlapException, ObjectNotFoundException {
		ZonedDateTime zdt = date.atZone(ZoneId.systemDefault());
		long dateShowingSec = zdt.toInstant().getEpochSecond();
		long movieDurationSec = MovieHandler.getInstance().getMovie(movie).getDuration()*60;

		List<MovieShowing> showingList = PersistenceFacade.getInstance().getMovieShowingList(theatre, date);
		long dateShowingToControll;
		long filmToControllDuration;
		for (MovieShowing sh: showingList) {
			zdt = sh.getDate().atZone(ZoneId.systemDefault());
			dateShowingToControll = zdt.toInstant().getEpochSecond();

			if (dateShowingToControll == dateShowingSec)
				throw new OverlapException();

			if (dateShowingToControll > dateShowingSec) {
				if ((dateShowingSec + movieDurationSec) >= dateShowingToControll)
					throw new OverlapException();
			}

			if (dateShowingToControll < dateShowingSec) {
				filmToControllDuration = MovieHandler.getInstance().getMovie(sh.getMovie()).getDuration()*60;

				if ((dateShowingToControll + filmToControllDuration) >= dateShowingSec)
					throw new OverlapException();
			}
		}
	}

	public static MovieShowingHandler getInstance() {
		if (instance == null) {
			instance = new MovieShowingHandler();
		}
		return instance;
	}
}
