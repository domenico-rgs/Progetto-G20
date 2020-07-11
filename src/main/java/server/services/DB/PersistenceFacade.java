package server.services.DB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.domain.cinema.Movie;
import server.domain.exception.SeatException;
import server.domain.showing.MovieShowing;
import server.domain.theatre.Seat;
import server.domain.theatre.Theatre;

public class PersistenceFacade {
	private static PersistenceFacade instance = null;
	private Map<Class, IMapper> mapper;


	private PersistenceFacade() throws IOException, SeatException {
		try {
			this.mapper = MapperFactory.getInstance().getMappers();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static PersistenceFacade getInstance() throws IOException, SeatException{
		if(instance == null)
			instance = new PersistenceFacade();
		return instance;
	}

	public Map<String, Movie> getAllMovies(){
		return ((MoviesMapper)mapper.get(MoviesMapper.class)).getMovies();
	}

	public Map<String, MovieShowing> getAllMovieShowings(){
		return ((ShowingsMapper)mapper.get(ShowingsMapper.class)).getShowings();
	}

	public void addMovie(String OID, Movie movie)throws SQLException{
		mapper.get(MoviesMapper.class).put(OID,movie);
	}

	public void addMovieShowing(String OID, MovieShowing show)throws SQLException{
		mapper.get(ShowingsMapper.class).put(OID,show);
	}

	public List<MovieShowing> getMovieShowingList(String OID_movie) throws SQLException{
		return ((ShowingsMapper)mapper.get(ShowingsMapper.class)).getMovieShowingList(OID_movie);
	}

	public HashMap<Seat,Boolean> getAvailabilityList(String OID_movieShowing) throws SQLException{
		AvailabilityMapper a = new AvailabilityMapper();

		return a.getAvailabilityList(OID_movieShowing);
		//return ((AvailabilityMapper)mapper.get(AvailabilityMapper.class)).getAvailabilityList(OID_movieShowing);
	}

	public HashMap<Seat,Boolean> getAvailableSeatsList(String OID_movieShowing) throws SQLException{
		AvailabilityMapper a = new AvailabilityMapper();

		return a.getAvailableSeatsList(OID_movieShowing);
	}

	//non era implementata
	public Map<String, Theatre> getAllTheatre(){
		return ((TheatreMapper)mapper.get(TheatreMapper.class)).getTheatres();
	}

	public void addTheatre(String OID, Theatre t) throws SQLException {
		mapper.get(TheatreMapper.class).put(OID,t);
	}

	public Object get(String OID, Class klass) throws SQLException{
		return this.mapper.get(klass).get(OID);
	}

	public void updateTable(Class klass,Object obj,String OID)throws SQLException{
		mapper.get(klass).updateTable(OID,obj);
	}
}