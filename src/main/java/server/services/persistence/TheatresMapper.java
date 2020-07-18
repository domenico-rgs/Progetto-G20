package server.services.persistence;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import server.domain.cinema.theatre.Theatre;
import server.exception.SearchException;
import server.exception.SeatException;

/**
 * It is the mapper of the table "Theatres"
 */
public class TheatresMapper extends AbstractPersistenceMapper {
	private Map<String, Theatre> theatres;
	private SeatsMapper sm ;

	/**
     * Initialize theatres with
     * the theatres which are registered  when the system is set up.
     * @throws SQLException
     */
	public TheatresMapper(SeatsMapper sm) throws SQLException {
		super("THEATRES");
		this.sm=sm;
		this.theatres = new HashMap<>();
		setUp();
	}

	@Override
	protected Object getObjectFromTable(String OID) throws SQLException, ObjectNotFoundException {
		PreparedStatement pstm = conn.prepareStatement("SELECT * FROM "+tableName+" WHERE theatreName = ?");
		pstm.setString(1,OID);
		ResultSet rs = pstm.executeQuery();
		if(!rs.next())
			throw new ObjectNotFoundException();

		try {
			Theatre tmp = new Theatre(rs.getString(1));
			tmp.setSeatsList(sm.getSeatsList(tmp.getTheatreName()));
			return tmp;
		} catch (IOException | SeatException e) {
			e.printStackTrace();
		}
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
	public void delete(String OID) throws SQLException, SearchException {
		if(!isUsed(OID)) {
			PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + " WHERE theatreName!='' and theatreName= ?");
			stm.setObject(1, OID);
			stm.execute();
		}else
			throw new SearchException("Theatre is used!");

		this.theatres.remove(OID);
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

	protected void setUp() throws SQLException {
		Statement stm = super.conn.createStatement();
		ResultSet rs = stm.executeQuery("select * from "+super.tableName);
		while (rs.next()){
			Theatre tmp;
			try {
				tmp = new Theatre(rs.getString(1));
				tmp.setSeatsList(sm.getSeatsList(tmp.getTheatreName()));

				this.theatres.put(rs.getString(1),tmp);
			} catch (IOException | SeatException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isUsed(String OID) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement("SELECT COUNT(*) FROM MOVIESHOWINGS WHERE theatre=?");
		pstm.setString(1,OID);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		if(rs.getInt(1)==0)
			return false;
		else
			return true;
	}

	protected synchronized Map<String, Theatre> getTheatres() {
		return theatres;
	}
}
