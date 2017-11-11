package ua.nure.kn156.ostapenko;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirthd;

	public User(User user) {
		id = user.getId();
		firstName = user.getFirstName();
		lastName = user.getLastName();
		dateOfBirthd = user.getDateOfBirthd();
	}

	public User() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirthd() {
		return dateOfBirthd;
	}

	public void setDateOfBirthd(Date dateOfBirthd) {
		this.dateOfBirthd = dateOfBirthd;
	}

	public Object getFullName() throws IllegalArgumentException {
		if ((getFirstName() == null) || (getLastName() == null))
			throw new IllegalArgumentException();
		return getLastName() + ", " + getFirstName();
	}

	public int getAge() {
		Calendar dateOfBirth = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		dateOfBirth.setTime(new Date());
		dateOfBirth.setTime(getDateOfBirthd());
		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}
		return age;
	}
}
