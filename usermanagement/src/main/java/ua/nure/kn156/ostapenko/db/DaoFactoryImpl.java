package ua.nure.kn156.ostapenko.db;

public class DaoFactoryImpl extends DaoFactory {
	@Override
	public UserDao getUserDao() {
		UserDao result = null;
		try {
			Class clazz = Class.forName(properties.getProperty(USER_DAO));
			result = (UserDao) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}