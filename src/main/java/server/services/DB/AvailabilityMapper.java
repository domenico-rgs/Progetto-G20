package server.services.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import server.domain.cinema.MovieShowing;
import server.domain.cinema.theatre.Seat;

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

		for (Map.Entry<String, Seat> temp:ms.getTheatre().getSeatsList().entrySet()) {
			PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?)");
			pstm.setString(1,ms.getId());
			pstm.setString(2,temp.getValue().getPosition());
			pstm.setBoolean(3,true);
			pstm.execute();
		}
	}

	@Override
	public synchronized  void updateTable(String OID, Object obj) throws SQLException{
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
	
	protected synchronized void changeAvailability(String OID_showing, String OID_seat, boolean availability) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement("UPDATE " + tableName+ " SET available=? WHERE BINARY showingID=? AND pos=?");
		pstm.setBoolean(1,availability);
		pstm.setString(2,OID_showing);
		pstm.setString(3,OID_seat);
		pstm.execute();
	}

	public HashMap<Seat, Boolean> getAvailableSeatsList(String OID_movieShowing) throws SQLException {
		HashMap<Seat, Boolean> availabilityList = new HashMap<>();

		PreparedStatement pstm = conn.prepareStatement("SELECT * FROM "+tableName+" WHERE BINARY showingID = ? and available= 1" );
		pstm.setString(1, OID_movieShowing);
		ResultSet rs = pstm.executeQuery();
		while (rs.next())
			availabilityList.put(new Seat(rs.getString(2)), rs.getBoolean(3));

		return availabilityList;
	}
}
