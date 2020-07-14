package server.services.DB;

import java.sql.SQLException;

import server.domain.exception.SearchException;

/**
 * Interface for Mapper Class
 */
public interface IMapper {

	String DB_URL = "jdbc:mysql://web-lnx243.ergonet.host:3306/progG20?serverTimezone=Europe/Rome";
	String USER = "cinemaG20";
	String PASSWORD = "progettoG20";

	/**
	 * Method which returns an Object for the table belonging to the Mapper
	 * @param OID is the code of the object
	 * @return the object requested
	 * @throws SQLException
	 */
	Object  get(String OID) throws SQLException;

	/**
	 * Method which insert a new Object in the table belonging to he Mapper
	 * @param OID is the code of the Object
	 * @param obj is the new Object which has to be inserted
	 * @throws SQLException
	 */
	void put(String OID, Object obj) throws SQLException;

	void delete(String OID) throws SQLException, SearchException;

	/**
	 * Method which modifies the information of the object in the table belonging to the Mapper
	 * @param OID is the code of the Object
	 * @param obj is the Object whose information has to be modified
	 * @throws SQLException
	 */
	void updateTable(String OID,Object obj) throws SQLException;
}