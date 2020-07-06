package server.services.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import server.domain.showing.MovieShowing;
import server.domain.theatre.Seat;

public class AvailabilityMapper extends AbstractPersistenceMapper {
	public AvailabilityMapper() throws SQLException {
		super("AVAILABILITY");
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
		MovieShowing ms = (MovieShowing)obj;

		for (Map.Entry<Seat, Boolean> temp:ms.getAvailability().getAvailability().entrySet()) {
			PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?)");
			pstm.setString(1,ms.getId());
			pstm.setString(2,temp.getKey().getPosition());
			pstm.setBoolean(3,temp.getValue());
			pstm.execute();
		}
	}

	@Override
	public synchronized  void updateTable(String OID, Object obj){
	}

	protected HashMap<Seat,Boolean> getAvailabilityList(String OID_movieShowing) throws SQLException{
		HashMap<Seat, Boolean> availabilityList = new HashMap<>();
		

		PreparedStatement pstm = conn.prepareStatement("SELECT * FROM "+tableName+" WHERE BINARY showingID = ?" );
		pstm.setString(1, OID_movieShowing);
		ResultSet rs = pstm.executeQuery();
		while (rs.next())
			availabilityList.put(new Seat(rs.getString(2)), rs.getBoolean(3));

		return availabilityList;
	}
}
