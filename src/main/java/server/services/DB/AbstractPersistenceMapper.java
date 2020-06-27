package server.services.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Abstract class which has to be extended by all the Mappers.
 * Each represents a table of the Database and it only can access that table.
 */
public abstract class AbstractPersistenceMapper implements IMapper {

	protected String tableName;
	protected Connection conn;


	/**
	 * Constructor of the class
	 * @param tableName is name of the table of the db
	 * @throws SQLException
	 */
	protected AbstractPersistenceMapper(String tableName) throws SQLException {
		this.tableName = tableName;
		conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
	}

	/**
	 * Method called when an objects is requested .
	 * @param OID is the code (by whom the object is identified in the system)of the object which is requested.
	 * @return the object
	 */
	@Override
	public synchronized Object get(String OID) throws SQLException{
		Object obj = getObjectFromCache(OID);
		if(obj == null){
			obj = getObjectFromTable(OID);
			updateCache(OID, obj);
		}
		return obj;
	}

	/**
	 * Method which get the object, represented by its key, (if it is not in cache) from the table
	 * who is linked to the mapper
	 * @param OID is the key of the object
	 * @return the object requested
	 * @throws SQLException
	 */
	protected abstract Object getObjectFromTable (String OID) throws SQLException;

	/**
	 * Method which get the object , represented by its key, from the cache of the mapper
	 * (only if it has already been instanced from the database)
	 * @param OID the key of the object
	 * @return the object requested
	 */
	protected abstract Object getObjectFromCache(String OID);

	/**
	 * Method called the cache has to be updated.
	 * @param OID the key of the object
	 * @param obj the object itself
	 */
	protected abstract void updateCache(String OID,Object obj);

	/**
	 * Method which select from the table the last code of the Objects of the table belonging
	 * to the Mapper
	 * @param keyName is the name of the key of the table
	 * @return the last code of the table
	 * @throws SQLException
	 */
	protected synchronized String getLastObjectCode(String keyName) throws SQLException{
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("select max("+keyName+") from "+tableName);
		if(rs.next())
			return rs.getString(1);
		return null;
	}
}