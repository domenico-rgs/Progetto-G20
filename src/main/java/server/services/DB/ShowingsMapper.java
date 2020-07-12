package server.services.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.domain.cinema.MovieShowing;

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


	protected void deleteExpiredShowing(long millis) throws SQLException {
		PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + "WHERE dateShow< ?");
		stm.setObject(1, new java.sql.Timestamp(millis));
		stm.execute();
	}

	protected synchronized Map<String, MovieShowing> getShowings() {
		return showing;
	}
}
