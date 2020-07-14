package server.services.DB;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.domain.cinema.Movie;
import server.domain.cinema.MovieShowing;
import server.domain.cinema.Ticket;
import server.domain.cinema.theatre.Seat;
import server.domain.cinema.theatre.Theatre;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.payment.discount.TicketPricingStrategy;

public class PersistenceFacade {
	private static PersistenceFacade instance = null;
	private Map<Class<?>, IMapper> mapper;

	private PersistenceFacade() throws IOException, SeatException {
		try {
			this.mapper = MapperFactory.getInstance().getMappers();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static PersistenceFacade getInstance() throws IOException, SeatException{
		if(instance == null) {
			instance = new PersistenceFacade();
		}
		return instance;
	}

	public Map<String, Movie> getAllMovies(){
		return ((MoviesMapper)mapper.get(MoviesMapper.class)).getMovies();
	}

	public List<MovieShowing> getAllMovieShowings(){
		return ((ShowingsMapper)mapper.get(ShowingsMapper.class)).getShowings();
	}

	public List<MovieShowing> getMovieShowingList(String OID_movie) throws SQLException{
		return ((ShowingsMapper)mapper.get(ShowingsMapper.class)).getMovieShowingList(OID_movie);
	}

	public List<MovieShowing> getMovieShowingList(String OID_theatre, LocalDateTime date) throws SQLException{
		return ((ShowingsMapper)mapper.get(ShowingsMapper.class)).getMovieShowingList(OID_theatre, date);
	}

	public HashMap<Seat,Boolean> getAvailabilityList(String OID_movieShowing) throws SQLException{
		return ((AvailabilityMapper)mapper.get(AvailabilityMapper.class)).getAvailabilityList(OID_movieShowing);
	}

	public HashMap<Seat,Boolean> getAvailableSeatsList(String OID_movieShowing) throws SQLException{
		return ((AvailabilityMapper)mapper.get(AvailabilityMapper.class)).getAvailableSeatsList(OID_movieShowing);
	}

	public Map<String, Theatre> getAllTheatre(){
		return ((TheatresMapper)mapper.get(TheatresMapper.class)).getTheatres();
	}

	public void deleteExpiredShowing(long millis) throws SQLException {
		((ShowingsMapper)mapper.get(ShowingsMapper.class)).deleteExpiredShowing(millis);
	}

	public void changeAvailability(String OID_showing, String OID_seat, boolean availability) throws SQLException {
		((AvailabilityMapper)mapper.get(AvailabilityMapper.class)).changeAvailability(OID_showing, OID_seat, availability);
	}

	public void addTickets(List<Ticket> ticketList) throws SQLException {
		for(Ticket t : ticketList) {
			put(t.getCode(), TicketsMapper.class, t);
		}
	}

	public Object get(String OID, Class<?> klass) throws SQLException{
		return this.mapper.get(klass).get(OID);
	}

	public void put(String OID, Class<?> klass, Object obj) throws SQLException{
		this.mapper.get(klass).put(OID, obj);
	}

	public void updateTable(Class<?> klass,Object obj,String OID)throws SQLException{
		mapper.get(klass).updateTable(OID,obj);
	}

	public void delete(String OID, Class<?> klass) throws SQLException, SearchException{
		this.mapper.get(klass).delete(OID);
	}

	public List<TicketPricingStrategy> getDiscountList() throws NumberFormatException, SQLException {
		return ((DiscountCodesMapper)mapper.get(DiscountCodesMapper.class)).getDiscountList();
	}
}