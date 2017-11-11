package ua.nure.kn156.ostapenko;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	private User user;
	private Date dateOfBirthd;
	private final int DAY_OF_BIRTH = 4;
	private final int MONTH_OF_BIRTH = 12;
	private final int YEAR_OF_BIRTH = 1997;

	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
	}

	public void testGetFullName() {
		user.setFirstName("Maksym");
		user.setLastName("Ostapenko");
		assertEquals("Ostapenko, Maksym", user.getFullName());
	}

	public void testGetAge() {
		Calendar dateOfBirth = new GregorianCalendar(YEAR_OF_BIRTH, MONTH_OF_BIRTH, DAY_OF_BIRTH);
		dateOfBirthd = dateOfBirth.getTime();
		user.setDateOfBirthd(dateOfBirthd);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}
		assertEquals(age, user.getAge());
	}

}
