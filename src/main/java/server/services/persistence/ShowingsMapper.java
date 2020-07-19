package server.services.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.domain.cinema.MovieShowing;
import server.exception.ObjectNotFoundException;
import server.exception.SearchException;

/**
 * It is the mapper of the table "Showings"
 */
public class ShowingsMapper extends AbstractPersistenceMapper {
	private TheatresMapper tm;
	private AvailabilityMapper am;
	private Map<String, MovieShowing> showing;

	/**
	 * Initialize showing with
	 * the showings which are registered  when the system is set up.
	 * @throws SQLException
	 */
	public ShowingsMapper(TheatresMapper tm, AvailabilityMapper am) throws SQLException {
		super("MOVIESHOWINGS");
		this.showing = new HashMap<>();
		this.am=am;
		this.tm=tm;
		setUp();
	}

	protected synchronized String getLastObjectCode(String keyName) throws SQLException{
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("Select max("+keyName+") from (select CAST((substring(id,2)) As double) id from "+super.tableName+")a order by id");
		if(rs.next())
			return rs.getString(1);
		return null;
	}

	@Override
	protected Object getObjectFromTable(String OID) throws SQLException, ObjectNotFoundException {
		PreparedStatement pstm = conn.prepareStatement("SELECT * FROM "+tableName+" WHERE id = ?");
		pstm.setString(1,OID);
		ResultSet rs = pstm.executeQuery();
		if(!rs.next())
			throw new ObjectNotFoundException();
		return new MovieShowing(rs.getString(1), rs.getString(2), rs.getTimestamp(3).toInstant().atZone(ZoneOffset.ofTotalSeconds(0)).toLocalDateTime(),
				(server.domain.cinema.theatre.Theatre)tm.get(rs.getString(4)),Double.parseDouble(rs.getString(5)));
	}

	@Override
	protected Object getObjectFromCache(String OID) {
		if(this.showing.containsKey(OID))
			return this.showing.get(OID);
		return null;
	}

	@Override
	protected void updateCache(String OID,Object obj) {
		this.showing.remove(OID);
		this.showing.put(OID,(MovieShowing)obj);
	}

	@Override
	public synchronized void delete(String OID) throws SQLException, SearchException {
		if(!isUsed(OID)) {
			PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + " WHERE id!='' and id= ?");
			stm.setObject(1, OID);
			stm.execute();
		}else
			throw new SearchException("The showing is used!");
	}


	@Override
	public synchronized void put(String OID, Object obj) throws SQLException{
		MovieShowing s = (MovieShowing)obj;
		updateCache(OID,s);

		PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?)");
		pstm.setString(1,OID);
		pstm.setString(2,s.getMovie());
		pstm.setObject(3, new java.sql.Timestamp(s.getDate().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
		pstm.setString(4,s.getTheatreName());
		pstm.setDouble(5,s.getPrice());

		pstm.execute();

		am.put(null, s);
	}

	@Override
	public synchronized void updateTable(String OID, Object obj)throws SQLException {
		MovieShowing s = (MovieShowing)obj;
		updateCache(OID,s);

		PreparedStatement pstm = conn.prepareStatement("UPDATE " + tableName+" SET theatre=?, price=?" +
				"WHERE BINARY id=? ");
		pstm.setString(1,s.getTheatreName());
		pstm.setDouble(2,s.getPrice());
		pstm.setString(3,OID);
		pstm.execute();
	}

	protected void setUp() throws SQLException {
		Statement stm = super.conn.createStatement();
		ResultSet rs = stm.executeQuery("select * from "+super.tableName);
		while (rs.next()){
			MovieShowing tmp;
			try {
				tmp = new MovieShowing(rs.getString(1), rs.getString(2), rs.getTimestamp(3).toInstant().atZone(ZoneOffset.ofTotalSeconds(0)).toLocalDateTime(),
						(server.domain.cinema.theatre.Theatre)tm.get(rs.getString(4)),Double.parseDouble(rs.getString(5)));
				this.showing.put(rs.getString(1),tmp);
			} catch (NumberFormatException | ObjectNotFoundException e) {
				e.printStackTrace();
			}
		}

		OIDCreator.getInstance().setShowingCode(this.getLastObjectCode("id"));
	}

	protected List<MovieShowing> getMovieShowingList(String OID_movie) throws SQLException {
		List<MovieShowing> showings = new ArrayList<>();
		PreparedStatement stm = conn.prepareStatement("select * from "+super.tableName + " where movieTitle = ?");
		stm.setString(1, OID_movie);
		ResultSet rs = stm.executeQuery();
		while (rs.next()){

			try {
				MovieShowing ms = new MovieShowing(rs.getString(1), rs.getString(2), rs.getTimestamp(3).toInstant().atZone(ZoneOffset.ofTotalSeconds(0)).toLocalDateTime(),
						(server.domain.cinema.theatre.Theatre)tm.get(rs.getString(4)),Double.parseDouble(rs.getString(5)));
				updateCache(ms.getId(),ms);
				showings.add(ms);
			} catch (NumberFormatException | ObjectNotFoundException e) {
				e.printStackTrace();
			}
		}
		return showings;
	}

	protected List<MovieShowing> getMovieShowingList(String OID_theatre, LocalDateTime date) throws SQLException {
		List<MovieShowing> showings = new ArrayList<>();
		PreparedStatement stm = conn.prepareStatement("select * from "+super.tableName + " where theatre=? and (dateShow<? and dateShow>=?)");
		stm.setString(1, OID_theatre);
		stm.setObject(2, new java.sql.Timestamp(date.toLocalDate().atTime(LocalTime.MIDNIGHT).toInstant(ZoneOffset.ofTotalSeconds(0)).plus(Duration.ofDays(1)).toEpochMilli()));
		stm.setObject(3, new java.sql.Timestamp(date.toLocalDate().atTime(LocalTime.MIDNIGHT).toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
		ResultSet rs = stm.executeQuery();
		while (rs.next()){

			try {
				MovieShowing ms = new MovieShowing(rs.getString(1), rs.getString(2), rs.getTimestamp(3).toInstant().atZone(ZoneOffset.ofTotalSeconds(0)).toLocalDateTime(),
						(server.domain.cinema.theatre.Theatre)tm.get(rs.getString(4)),Double.parseDouble(rs.getString(5)));
				updateCache(ms.getId(),ms);
				showings.add(ms);
			} catch (NumberFormatException | ObjectNotFoundException e) {
				e.printStackTrace();
			}
		}
		return showings;
	}

	/**
	 * Method that checks whether a showing has some seats occupied
	 * @param OID identifier of the theatre
	 * @return
	 * @throws SQLException
	 */
	private boolean isUsed(String OID) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement("SELECT COUNT(*) FROM AVAILABILITY WHERE SHOWINGID=? AND AVAILABLE=0");
		pstm.setString(1,OID);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		if(rs.getInt(1)==0)
			return false;
		else
			return true;
	}

	/**
	 * Delete showings if its date has passed compared to that indicated
	 * @param millis date in milliseconds before which to cancel all projections
	 * @throws SQLException
	 */
	protected void deleteExpiredShowing(long millis) throws SQLException {
		PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + " WHERE id!='' and dateShow< ?");
		stm.setObject(1, new java.sql.Timestamp(millis));
		stm.execute();
	}

	protected synchronized List<MovieShowing> getShowings() {
		return new ArrayList<MovieShowing>(showing.values());
	}
}
