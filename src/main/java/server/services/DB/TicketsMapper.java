package server.services.DB;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import server.domain.cinema.Ticket;
import server.domain.exception.SeatException;

public class TicketsMapper extends AbstractPersistenceMapper {
	private Map<String, Ticket> tickets;

	public TicketsMapper() throws SQLException, IOException, SeatException {
		super("TICKETS");
		this.tickets = new HashMap<>();
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
	public synchronized void put(String OID, Object obj) throws SQLException{
		Ticket t = (Ticket)obj;
		updateCache(OID,t);

		PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?)");

		pstm.setString(1,OID);
		pstm.setString(2, t.getMovie());
		pstm.setObject(3, new java.sql.Timestamp(t.getDate().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
		pstm.setString(4,t.getSeat());
		pstm.setDouble(5,t.getTotalPrice());

		pstm.execute();
	}

	@Override
	public synchronized void updateTable(String OID, Object obj)throws SQLException {
	}

	protected void setUp() throws SQLException, IOException, SeatException {
		Statement stm = super.conn.createStatement();
		ResultSet rs = stm.executeQuery("select * from "+super.tableName);
		while (rs.next()){
			Ticket tmp = new Ticket(rs.getNString(1),rs.getString(2),rs.getString(4),
					new Timestamp(rs.getDate(3).getTime()).toLocalDateTime(),rs.getDouble(5));

			this.tickets.put(rs.getString(1),tmp);
		}
	}

	protected void deleteTicket(String OID) throws SQLException {
		PreparedStatement stm = conn.prepareStatement("SET SQL_SAFE_UPDATES = 0; DELETE FROM " + super.tableName + " WHERE BINARY ticketCode=?");
		stm.setString(1, OID);
		stm.execute();
	}


	public synchronized Map<String, Ticket> getTickets() {
		return tickets;
	}
}
