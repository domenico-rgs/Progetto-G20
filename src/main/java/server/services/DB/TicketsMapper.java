package server.services.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import server.domain.cinema.Ticket;
import server.domain.exception.SearchException;

/**this class has the task of interacting with the database,
 * retrieving the requested object from the table and updating
 * the cache, precisely the tickets table*/

public class TicketsMapper extends AbstractPersistenceMapper {
	private Map<String, Ticket> tickets;
	private ShowingsMapper sm;

	public TicketsMapper(ShowingsMapper sm) throws SQLException {
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
	public synchronized void updateTable(String OID, Object obj)throws SQLException {
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
		
		this.tickets.remove(OID);
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
		pstm.setDouble(5,Math.round(t.getTotalPrice()*100)/100);

		pstm.execute();
	}

	protected void setUp() throws SQLException {
		Statement stm = super.conn.createStatement();
		ResultSet rs = stm.executeQuery("select ticketCode, movieTitle, occupiedSeat, showingID, totalPrice from TICKETS join MOVIESHOWINGS on TICKETS.showingID = MOVIESHOWINGS.id");

		while (rs.next()){
			Ticket tmp = new Ticket(rs.getString(1),rs.getString(2),rs.getString(3),
					(server.domain.cinema.MovieShowing)sm.get(rs.getString(4)), rs.getDouble(5));

			this.tickets.put(rs.getString(1),tmp);
		}
	}

	protected synchronized Map<String, Ticket> getTickets() {
		return tickets;
	}
}
