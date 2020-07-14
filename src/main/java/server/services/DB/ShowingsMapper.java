package server.services.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.domain.cinema.MovieShowing;
import server.domain.exception.SearchException;

public class ShowingsMapper extends AbstractPersistenceMapper {
	private TheatresMapper tm;
	private AvailabilityMapper am;
	private Map<String, MovieShowing> showing;

	public ShowingsMapper(TheatresMapper tm, AvailabilityMapper am) throws SQLException {
		super("MOVIESHOWINGS");
		this.showing = new HashMap<>();
		this.am=am;
		this.tm=tm;
		setUp();
	}

	@Override
	protected Object getObjectFromTable(String OID) {
		return null;
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
	public void delete(String OID) throws SQLException, SearchException {
		if(!isUsed(OID)) {
			PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + "WHERE id!='' and id= ?");
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
			MovieShowing tmp = new MovieShowing(rs.getString(1), rs.getString(2), new Timestamp(rs.getDate(3).getTime()).toLocalDateTime(),
					(server.domain.cinema.theatre.Theatre)tm.get(rs.getString(4)),Double.parseDouble(rs.getString(5)));

			this.showing.put(rs.getString(1),tmp);
		}

		OIDCreator.getInstance().setShowingCode(getLastObjectCode("id"));
	}

	protected List<MovieShowing> getMovieShowingList(String OID_movie) throws SQLException {
		List<MovieShowing> showings = new ArrayList<>();
		PreparedStatement stm = conn.prepareStatement("select * from "+super.tableName + " where movieTitle = ?");
		stm.setString(1, OID_movie);
		ResultSet rs = stm.executeQuery();
		while (rs.next()){
			MovieShowing ms =  new MovieShowing(rs.getString(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(),
					(server.domain.cinema.theatre.Theatre)tm.get(rs.getString(4)),Double.parseDouble(rs.getString(5)));
			updateCache(ms.getId(),ms);
			showings.add(ms);
		}
		return showings;
	}

	protected List<MovieShowing> getMovieShowingList(String OID_theatre, LocalDateTime date) throws SQLException{
		List<MovieShowing> showings = new ArrayList<>();
		PreparedStatement stm = conn.prepareStatement("select * from "+super.tableName + " where theatre=? and (dateShow<? and dateShow>=?)");
		stm.setString(1, OID_theatre);
		stm.setObject(2, new java.sql.Timestamp(date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()+1000 * 60 * 60 * 24));
		stm.setObject(3, new java.sql.Timestamp(date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
		ResultSet rs = stm.executeQuery();
		while (rs.next()){
			MovieShowing ms =  new MovieShowing(rs.getString(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(),
					(server.domain.cinema.theatre.Theatre)tm.get(rs.getString(4)),Double.parseDouble(rs.getString(5)));
			updateCache(ms.getId(),ms);
			showings.add(ms);
		}
		return showings;
	}

	private boolean isUsed(String OID) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement("SELECT COUNT(*) FROM AVAILABLE WHERE SHOWINGID=? AND AVAILABLE=0");
		pstm.setString(1,OID);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		if(rs.getInt(1)!=0)
			return false;
		else
			return true;
	}

	protected void deleteExpiredShowing(long millis) throws SQLException {
		PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + " WHERE id!='' and dateShow< ?");
		stm.setObject(1, new java.sql.Timestamp(millis));
		stm.execute();
	}

	protected synchronized List<MovieShowing> getShowings() {
		return new ArrayList<MovieShowing>(showing.values());
	}
}
