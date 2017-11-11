package ua.nure.kn156.ostapenko.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * Implementation of the connection factory interface
 * 
 * @author Maksym
 *
 */
public class ConnectionFactoryImpl implements ConnectionFactory {

	private String driver;
	private String url;
	private String user;
	private String password;

	public ConnectionFactoryImpl(String driver, String url, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	/**
	 * @return created connection based on parameters: url, user, password
	 * @throws RuntimeException
	 *             if the driver is not found
	 * @throws DatabaseException
	 *             if url, user or password is incorrect
	 */
	public Connection createConnection() throws DatabaseException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
