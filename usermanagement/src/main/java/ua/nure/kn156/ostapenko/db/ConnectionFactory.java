package ua.nure.kn156.ostapenko.db;

import java.sql.Connection;

/**
 * Interface that defines a method for creating a connection factory
 * 
 * @author Maksym
 *
 */
public interface ConnectionFactory {
	/**
	 * Method that creates a connection
	 * 
	 * @see settings.properties file, which contains all parameters which are
	 *      nessesary for generating connection
	 * @return generated connection object
	 * @throws DatabaseException
	 */
	Connection createConnection() throws DatabaseException;

}
