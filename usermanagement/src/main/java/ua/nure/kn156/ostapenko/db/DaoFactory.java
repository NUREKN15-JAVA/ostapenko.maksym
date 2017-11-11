package ua.nure.kn156.ostapenko.db;

import java.io.IOException;
import java.util.Properties;

/**
 * Singleton class that is used for creating Data Access Objects (DAOs)
 * 
 * @author Maksym
 *
 */
public abstract class DaoFactory {

	protected static final String USER_DAO = "dao.ua.nure.kn156.ostapenko.db.UserDao";
	private static final String DAO_FACTORY = "dao.factory";
	protected static Properties properties;

	private static DaoFactory instance;

	static {
		properties = new Properties();
		try {
			properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized DaoFactory getInstance() {
		if (instance == null) {
			Class factoryClass;
			try {
				factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
				instance = (DaoFactory) factoryClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	protected DaoFactory() {
	}

	public static void init(Properties prop) {
		properties = prop;
		instance = null;
	}

	/**
	 * This method creates a new user DAO
	 * 
	 * @return new user DAO
	 */
	protected ConnectionFactory getConnectionFactory() {
		return new ConnectionFactoryImpl(properties);
	}

	public abstract UserDao getUserDao();
}