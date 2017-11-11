package ua.nure.kn156.ostapenko.db;

import java.io.IOException;
import java.util.Properties;

/**
 * Singleton class that is used for creating Data Access Objects (DAOs)
 * 
 * @author Maksym
 *
 */
public class DaoFactory {

	private static final String USER_DAO = "dao.ua.nure.kn156.ostapenko.db.UserDao";
	private final Properties properties;

	private final static DaoFactory INSTANCE = new DaoFactory();

	public static DaoFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 */
	public DaoFactory() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method that generates that is based on properties: driver, url, user,
	 * password
	 * 
	 * @return generated connection factory
	 */
	private ConnectionFactory getConnectionFactory() {
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String url = properties.getProperty("connection.url");
		String driver = properties.getProperty("connection.driver");
		return new ConnectionFactoryImpl(driver, url, user, password);
	}
/**
 * This method creates a new user DAO
 * @return new user DAO
 */
	public UserDAO getUserDao() {
		UserDAO result = null;
		try {
			Class clazz = Class.forName(properties.getProperty(USER_DAO));
			result = (UserDAO) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
