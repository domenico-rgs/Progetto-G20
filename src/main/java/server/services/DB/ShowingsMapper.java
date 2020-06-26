package server.services.DB;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import server.domain.showing.MovieShowing;
import server.domain.theatre.Theatre;

public class ShowingsMapper extends AbstractPersistenceMapper {

	private Map<String, MovieShowing> showing;

	public ShowingsMapper(TheatreMapper tm) throws SQLException {
		super("MOVIESHOWINGS");
		this.showing = new HashMap<>();
		setUp(tm);
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

		PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?)");
		pstm.setString(1,OID);
		pstm.setObject(2, new java.sql.Timestamp(s.getDate().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
		pstm.setString(3,s.getTheatreName());
		pstm.setDouble(4,s.getPrice());

		pstm.execute();

	}

	@Override
	public synchronized void updateTable(String OID, Object obj)throws SQLException {
		MovieShowing s = (MovieShowing)obj;
		updateCache(OID,s);

		PreparedStatement pstm = conn.prepareStatement("UPDATE " + tableName+" SET theatre=?, price=?" +
				"WHERE BINARY id=? ");
		pstm.setString(1,s.getTheatreName());
		pstm.setDouble(2,s.getPrice());
		pstm.setString(4,OID);
		pstm.execute();


	}

	protected void setUp(TheatreMapper tm) throws SQLException {
		Statement stm = super.conn.createStatement();
		ResultSet rs = stm.executeQuery("select * from "+super.tableName);
		while (rs.next()){
			MovieShowing tmp = new MovieShowing(rs.getString(1), new Timestamp(rs.getDate(2).getTime()).toLocalDateTime(),
					(Theatre) tm.get(rs.getString(3)),Double.parseDouble(rs.getString(4)));

			this.showing.put(rs.getString(1),tmp);
		}
		
		OIDCreator.getInstance().setShowingCode(getLastObjectCode("id"));
	}


	public synchronized Map<String, MovieShowing> getShowings() {
		return showing;
	}
}
