package server.services.DB;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import server.domain.exception.SeatException;
import server.domain.theatre.Theatre;


public class TheatreMapper extends AbstractPersistenceMapper {

	private Map<String, Theatre> theatre;

	public TheatreMapper() throws SQLException, IOException, SeatException {
		super("THEATRE");
		this.theatre = new HashMap<>();
		setUp();
	}

	@Override
	protected Object getObjectFromTable(String OID) {
		return null;
	}

	@Override
	protected Object getObjectFromCache(String OID) {
		if(this.theatre.containsKey(OID))
			return this.theatre.get(OID);
		return null;
	}

	@Override
	protected void updateCache(String OID,Object obj) {
		this.theatre.remove(OID);
		this.theatre.put(OID,(Theatre)obj);
	}


	@Override
	public synchronized void put(String OID, Object obj) throws SQLException{
		Theatre t = (Theatre)obj;
		updateCache(OID,t);

		PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?)");

		pstm.setString(1,OID);
		pstm.setString(2,t.getFilePath());

		pstm.execute();

	}

	@Override
	public synchronized void updateTable(String OID, Object obj)throws SQLException {
	}

	protected void setUp() throws SQLException, IOException, SeatException {
		Statement stm = super.conn.createStatement();
		ResultSet rs = stm.executeQuery("select * from "+super.tableName);
		while (rs.next()){
			Theatre tmp = new Theatre(rs.getString(1),rs.getString(2));

			this.theatre.put(rs.getString(1),tmp);
		}
	}


	public synchronized Map<String, Theatre> getTheatres() {
		return theatre;
	}
}
