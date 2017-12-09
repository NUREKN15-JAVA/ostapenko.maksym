package ua.nure.kn156.ostapenko.db;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.kn156.ostapenko.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {
	private static final long USERS_ID = 1000L;
	private static final int DAY_OF_BIRTH = 4;
	private static final int MONTH_OF_BIRTH = 11;
	private static final int YEAR_OF_BIRTH = 1997;
	private static final String LAST_NAME = "Ostapenko";
	private static final String FIRST_NAME = "Maksym";
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}

	public void testCreate() {
		User user = new User();
		user.setFirstName(FIRST_NAME);
		user.setLastName(LAST_NAME);
		user.setDateOfBirthd(new Date());
		assertNull(user.getId());
		User createdUser;
		try {
			createdUser = dao.create(user);
			assertNotNull(createdUser);
			assertNotNull(createdUser.getId());
			assertEquals(user.getFullName(), createdUser.getFullName());
			assertEquals(user.getAge(), createdUser.getAge());

		} catch (DatabaseException e) {
			fail(e.toString());
		}

	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement",
				"sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

	public void testFindAll() {
		Collection<User> collection = new LinkedList<>();
		try {
			collection = dao.findAll();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		assertNotNull("Collection is null", collection);
		assertEquals("Collection size.", 2, collection.size());
	}

	public void testFind() {
		User foundUser = new User();
		User newUser = new User();
		newUser.setFirstName("Bill");
		newUser.setLastName("Gates");
		Calendar dateOfBirthNew = new GregorianCalendar(1968, 3, 26);
		newUser.setDateOfBirthd(dateOfBirthNew.getTime());
		try {
			foundUser = dao.find(USERS_ID);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		assertNotNull(foundUser.getFirstName());
		assertEquals("Wrong user's first name!", foundUser.getFirstName(), newUser.getFirstName());
		assertEquals("Wrong user's last name!", foundUser.getLastName(), newUser.getLastName());
		assertEquals("Wrong user's date of birth!", foundUser.getDateOfBirthd(), newUser.getDateOfBirthd());
	}

	public void testUpdate() {
		User updatedUser = new User();
		User newUser = new User();
		newUser.setId(USERS_ID);
		newUser.setFirstName(FIRST_NAME);
		newUser.setLastName(LAST_NAME);
		newUser.setDateOfBirthd(new Date());
		Calendar dateOfBirthNew = new GregorianCalendar(YEAR_OF_BIRTH, MONTH_OF_BIRTH, DAY_OF_BIRTH);
		newUser.setDateOfBirthd(dateOfBirthNew.getTime());
		try {
			dao.update(newUser);
			updatedUser = dao.find(USERS_ID);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		assertNotNull(updatedUser.getFirstName());
		assertEquals("Incorrect user's last name!", newUser.getLastName(), updatedUser.getLastName());
		assertTrue(newUser.getFirstName().equals(updatedUser.getFirstName()));
		assertEquals("Wrong user's date of birth!", newUser.getDateOfBirthd(), updatedUser.getDateOfBirthd());
	}

	public void testDelete() {
		Collection<User> collection = new LinkedList<>();
		User user = new User();
		user.setId(USERS_ID);
		user.setFirstName(FIRST_NAME);
		user.setLastName(LAST_NAME);
		Calendar dateOfBirthNew = new GregorianCalendar(YEAR_OF_BIRTH, MONTH_OF_BIRTH, DAY_OF_BIRTH);
		user.setDateOfBirthd(dateOfBirthNew.getTime());
		try {
			dao.delete(user);
			collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size.", 1, collection.size());

		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

}
