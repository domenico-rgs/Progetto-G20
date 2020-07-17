package server.services.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import server.domain.cinema.Movie;
import server.domain.cinema.TypeCategory;
import server.exception.SearchException;

/**this class has the task of interacting with the database,
 * retrieving the requested object from the table and updating
 * the cache, precisely the movies table*/

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
	public void delete(String OID) throws SQLException, SearchException {
		if(!isUsed(OID)) {
			PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + " WHERE title!='' and title= ?");
			stm.setObject(1, OID);
			stm.execute();

			this.movie.remove(OID);
		}else
			throw new SearchException("Movie is used!");
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

		PreparedStatement pstm = conn.prepareStatement("UPDATE " + tableName+" SET plot=?, pathCover=?, category=?" +
				"WHERE title=? ");
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

	private boolean isUsed(String OID) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement("SELECT COUNT(*) FROM MOVIESHOWINGS WHERE MOVIETITLE=?");
		pstm.setString(1,OID);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		if(rs.getInt(1)==0)
			return false;
		else
			return true;
	}

	protected synchronized Map<String, Movie> getMovies() {
		return movie;
	}
}
