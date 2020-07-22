package server.domain.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.domain.cinema.theatre.Seat;
import server.domain.cinema.theatre.Theatre;
import server.exception.ObjectNotFoundException;
import server.exception.SearchException;
import server.exception.SeatException;
import server.services.persistence.PersistenceFacade;
import server.services.persistence.TheatresMapper;

public class TheatreHandler {
	private static TheatreHandler instance = null;

	private TheatreHandler() {}

	/**
	 * This method creates a theatre
	 * @param name this is the theatre's name
	 * @param config this is the configuration of the theatre
	 * @throws SeatException
	 * @throws SearchException
	 */
	synchronized public void createTheatre(String name, String config) throws SQLException, IOException, SeatException, SearchException {
		Theatre t = new Theatre(name);
		t.createSeats(config);
		PersistenceFacade.getInstance().put(name, TheatresMapper.class,t);
	}

	/**
	 * this method permits to delete a theatre
	 * @param name theatre's name
	 * @throws ObjectNotFoundException
	 */
	synchronized public void deleteTheatre(String name) throws SearchException, SQLException, ObjectNotFoundException{
		File f = new File(getTheatre(name).getFilePath());
		PersistenceFacade.getInstance().delete(name, TheatresMapper.class);
		f.delete();
	}

	synchronized public Theatre getTheatre(String name) throws SQLException, ObjectNotFoundException{
		return (Theatre) PersistenceFacade.getInstance().get(name, TheatresMapper.class);
	}

	synchronized public List<String> getTheatreList() throws SQLException{
		List<String> theatreList = new ArrayList<>();
		theatreList.addAll(PersistenceFacade.getInstance().getAllTheatre().keySet());
		return theatreList;
	}

	synchronized public HashMap<Seat, Boolean> getAllSeatsForShowing(String idShowing) throws SQLException {
		return PersistenceFacade.getInstance().getAvailabilityList(idShowing);
	}

	synchronized public List<Seat> getFreeSeatsForShowing(String idShowing) throws SQLException {
		List<Seat> freeSeats = new ArrayList<>();
		HashMap<Seat,Boolean> tmp = PersistenceFacade.getInstance().getAvailableSeatsList(idShowing);

		freeSeats.addAll(tmp.keySet());
		return freeSeats;
	}

	synchronized public void setAvailability(String idShowing, boolean availability, String... seats) throws SQLException {
		for(String s : seats) {
			PersistenceFacade.getInstance().changeAvailability(idShowing, s, availability);
		}
	}

	public static TheatreHandler getInstance() {
		if (instance == null) {
			instance = new TheatreHandler();
		}
		return instance;
	}
}
