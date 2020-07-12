package server.services.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import server.domain.cinema.theatre.Seat;
import server.domain.cinema.theatre.Theatre;


public class SeatsMapper extends AbstractPersistenceMapper {
	public SeatsMapper() throws SQLException {
		super("SEATS");
	}

	@Override
	protected Object getObjectFromTable(String OID){
		return null;
	}

	@Override
	protected Object getObjectFromCache(String OID) {
		return null;
	}

	@Override
	protected void updateCache(String OID, Object obj) {
	}

	@Override
	public synchronized void  put(String OID, Object obj)throws SQLException {
		Theatre t = (Theatre)obj;

		for (Map.Entry<String,Seat> temp:t.getSeatsList().entrySet()) {
			PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?)");
			pstm.setString(1,temp.getKey());
			pstm.setString(2,t.getTheatreName());
			pstm.setString(3,temp.getValue().getType().toString());
			pstm.setDouble(4,temp.getValue().getAddition());
			pstm.execute();
		}
	}

	@Override
	public synchronized  void updateTable(String OID, Object obj){
	}

	protected HashMap<String,Seat> getSeatsList(String theatreName) throws SQLException{
		HashMap<String, Seat> seatsList = new HashMap<>();

		PreparedStatement pstm = conn.prepareStatement("SELECT * FROM "+tableName+" WHERE BINARY theatre = ?" );
		pstm.setString(1, theatreName);
		ResultSet rs = pstm.executeQuery();
		while (rs.next())
			seatsList.put(rs.getString(2), new Seat(rs.getString(3)));

		return seatsList;
	}
}