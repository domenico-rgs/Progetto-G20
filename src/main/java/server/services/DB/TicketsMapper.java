package server.services.DB;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import server.domain.cinema.Ticket;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;

public class TicketsMapper extends AbstractPersistenceMapper {
	private Map<String, Ticket> tickets;
	private ShowingsMapper sm;

	public TicketsMapper(ShowingsMapper sm) throws SQLException, IOException, SeatException {
		super("TICKETS");
		this.tickets = new HashMap<>();
		this.sm=sm;
		setUp();
	}

	@Override
	protected Object getObjectFromTable(String OID) {
		return null;
	}

	@Override
	protected Object getObjectFromCache(String OID) {
		if(this.tickets.containsKey(OID))
			return this.tickets.get(OID);
		return null;
	}

	@Override
	protected void updateCache(String OID,Object obj) {
		this.tickets.remove(OID);
		this.tickets.put(OID,(Ticket)obj);
	}

	@Override
	public void delete(String OID) throws SQLException, SearchException {
		PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + " WHERE ticketCode!='' and ticketCode=?");
		stm.setString(1, OID);
		stm.execute();
	}


	@Override
	public synchronized void put(String OID, Object obj) throws SQLException{
		Ticket t = (Ticket)obj;
		updateCache(OID,t);

		PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?)");

		pstm.setString(1,OID);
		pstm.setString(2, t.getShowing().getTheatreName());
		pstm.setObject(3, t.getShowing().getId());
		pstm.setString(4,t.getSeat());
		pstm.setDouble(5,t.getTotalPrice());

		pstm.execute();
	}

	@Override
	public synchronized void updateTable(String OID, Object obj)throws SQLException {
	}

	protected void setUp() throws SQLException, IOException, SeatException {
		Statement stm = super.conn.createStatement();

		ResultSet rs = stm.executeQuery("select ticketCode, movieTitle, occupiedSeat, showingID, totalPrice from TICKETS join MOVIESHOWINGS on TICKETS.showingID = MOVIESHOWINGS.id");

		while (rs.next()){
			Ticket tmp = new Ticket(rs.getString(1),rs.getString(2),rs.getString(3),
					(server.domain.cinema.MovieShowing)sm.get(rs.getString(4)), rs.getDouble(5));

			this.tickets.put(rs.getString(1),tmp);
		}
	}

	protected void deleteTicket(String OID) throws SQLException {
		PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + " WHERE ticketCode!='' and ticketCode=?");
		stm.setString(1, OID);
		stm.execute();
	}


	protected synchronized Map<String, Ticket> getTickets() {
		return tickets;
	}
}
