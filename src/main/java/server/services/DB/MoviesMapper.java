package server.services.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import server.domain.cinema.Movie;
import server.domain.cinema.TypeCategory;

public class MoviesMapper extends AbstractPersistenceMapper {

	private Map<String, Movie> movie;

	public MoviesMapper() throws SQLException {
		super("MOVIES");
		this.movie = new HashMap<>();
		setUp();
	}

	@Override
	protected Object getObjectFromTable(String OID) {
		return null;
	}

	@Override
	protected Object getObjectFromCache(String OID) {
		if(this.movie.containsKey(OID))
			return this.movie.get(OID);
		return null;
	}

	@Override
	protected void updateCache(String OID,Object obj) {
		this.movie.remove(OID);
		this.movie.put(OID,(Movie)obj);
	}


	@Override
	public synchronized void put(String OID, Object obj) throws SQLException{
		Movie m = (Movie)obj;
		updateCache(OID,m);

		PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?)");

		pstm.setString(1,OID);
		pstm.setInt(2,m.getDuration());
		pstm.setString(3,m.getPlot());
		pstm.setString(4,m.getPathCover());
		pstm.setString(5,m.getCategory().toString().toLowerCase());

		pstm.execute();

	}

	@Override
	public synchronized void updateTable(String OID, Object obj)throws SQLException {
		Movie m = (Movie)obj;
		updateCache(OID,m);

		PreparedStatement pstm = conn.prepareStatement("UPDATE " + tableName+" SET plot=?, cover=?, category=?," +
				"WHERE BINARY title=? ");
		pstm.setString(1,m.getPlot());
		pstm.setString(2,m.getPathCover());
		pstm.setString(3,m.getCategory().toString().toLowerCase());
		pstm.setString(4,OID);
		pstm.execute();


	}

	protected void setUp() throws SQLException {
		Statement stm = super.conn.createStatement();
		ResultSet rs = stm.executeQuery("select * from "+super.tableName);
		while (rs.next()){
			Movie tmp = new Movie(rs.getString(1),rs.getInt(2),
					rs.getString(3),rs.getString(4), TypeCategory.valueOf(rs.getString(5).toUpperCase()));

			this.movie.put(rs.getString(1),tmp);
		}
	}


	public synchronized Map<String, Movie> getMovies() {
		return movie;
	}
}
