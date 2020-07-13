package server.services.DB;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import server.domain.cinema.theatre.Theatre;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;


public class TheatresMapper extends AbstractPersistenceMapper {
	private Map<String, Theatre> theatres;
	private SeatsMapper sm ;

	public TheatresMapper(SeatsMapper sm) throws SQLException, IOException, SeatException {
		super("THEATRES");
		this.sm=sm;
		this.theatres = new HashMap<>();
		setUp();
	}

	@Override
	protected Object getObjectFromTable(String OID) {
		return null;
	}

	@Override
	protected Object getObjectFromCache(String OID) {
		if(this.theatres.containsKey(OID))
			return this.theatres.get(OID);
		return null;
	}

	@Override
	protected void updateCache(String OID,Object obj) {
		this.theatres.remove(OID);
		this.theatres.put(OID,(Theatre)obj);
	}


	@Override
	public synchronized void put(String OID, Object obj) throws SQLException{
		Theatre t = (Theatre)obj;
		updateCache(OID,t);

		PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?)");

		pstm.setString(1,OID);
		pstm.setString(2,t.getFilePath());

		pstm.execute();

		sm.put(t.getTheatreName(), t);

	}

	@Override
	public synchronized void updateTable(String OID, Object obj)throws SQLException {
	}

	protected void setUp() throws SQLException, IOException, SeatException {
		Statement stm = super.conn.createStatement();
		ResultSet rs = stm.executeQuery("select * from "+super.tableName);
		while (rs.next()){
			Theatre tmp = new Theatre(rs.getString(1));
			tmp.setSeatsList(sm.getSeatsList(tmp.getTheatreName()));

			this.theatres.put(rs.getString(1),tmp);
		}
	}
	
	private boolean isUsed(String OID) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement("SELECT COUNT(*) FROM MOVIESHOWINGS WHERE theatre=?");
		pstm.setString(1,OID);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		if(rs.getInt(1)!=0)
			return false;
		else
			return true;
	}
	
	protected void deleteTheatre(String OID) throws SQLException, SearchException {
		if(!isUsed(OID)) {
			PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + "WHERE theatreName!='' and theatreName= ?");
			stm.setObject(1, OID);
			stm.execute();
		}else
			throw new SearchException("Theatre is used!");
	}


	public synchronized Map<String, Theatre> getTheatres() {
		return theatres;
	}
}
