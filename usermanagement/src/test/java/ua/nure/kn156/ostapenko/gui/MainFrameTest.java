package ua.nure.kn156.ostapenko.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn156.ostapenko.User;
import ua.nure.kn156.ostapenko.db.DaoFactory;
import ua.nure.kn156.ostapenko.db.MockDaoFactory;
import ua.nure.kn156.ostapenko.util.Messages;

/**
 * @author Maksym
 *
 */
public class MainFrameTest extends JFCTestCase {
	List<User> users;
	private Mock mockUserDao;
	private MainFrame mainFrame;
	private User expectedUser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		try {
			Properties properties = new Properties();
			properties.setProperty("dao.factory", MockDaoFactory.class.getName());
			DaoFactory.init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
			expectedUser = new User(new Long(1), "Maksym", "Ostapenko", new Date());
			users = Collections.singletonList(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();

		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	@Override
	protected void tearDown() throws Exception {
		try {
			// mockUserDao.verify();
			mainFrame.setVisible(false);
			getHelper().cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Component find(Class componentClass, String name) {
		Component component;
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component" + name + "'", component);
		return component;
	}

	public void testBrowsePanel() {
		find(JPanel.class, "browsePanel");
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals(Messages.getString("UserTableModel.id"), table.getColumnName(0)); // UserTableModel
																						// -
																						// for
																						// localization
		assertEquals(Messages.getString("UserTableModel.first_name"), table.getColumnName(1)); // UserTableModel
																								// -
																								// for
																								// localization
		assertEquals(Messages.getString("UserTableModel.last_name"), table.getColumnName(2)); // UserTableModel
																								// -
																								// for
																								// localization
		assertEquals(1, table.getRowCount());

	}

	public void testAddUser() {
		try {
			String firstName = "John";
			String lastName = "Doe";
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			calendar.set(year, month, day, 0, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date now = calendar.getTime();

			User user = new User(firstName, lastName, now);

			User expectedUser = new User(new Long(1), firstName, lastName, now);
			mockUserDao.expectAndReturn("create", user, expectedUser);

			ArrayList<User> users = new ArrayList(this.users);
			users.add(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());

			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

			find(JPanel.class, "addPanel");

			fillFields(firstName, lastName, now);

			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(2, table.getRowCount());

			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testCancelAddUser() {
		try {
			String firstName = "John";
			String lastName = "Doe";
			Date now = new Date();

			ArrayList users = new ArrayList(this.users);
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());

			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

			find(JPanel.class, "addPanel");

			fillFields(firstName, lastName, now);

			JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");

			assertEquals(1, table.getRowCount());
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testEditUser() {
		try {
			String firstName = "John";
			String lastName = "Doe";
			Date dateOfBirth = new Date();
			User user = new User(new Long(1), firstName, lastName, dateOfBirth);
			mockUserDao.expectAndReturn("update", user, expectedUser);
			mockUserDao.expectAndReturn("findAll", users);
			mockUserDao.expectAndReturn("find", new Long(1), expectedUser);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton editButton = (JButton) find(JButton.class, "editButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
			find(JPanel.class, "editPanel");
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
			JButton okButton = (JButton) find(JButton.class, "okButton");
			find(JButton.class, "cancelButton");

			firstNameField.setText("");
			lastNameField.setText("");
			dateOfBirthField.setText("");
			getHelper().sendString(new StringEventData(this, firstNameField, firstName));
			getHelper().sendString(new StringEventData(this, lastNameField, lastName));
			DateFormat formatter = DateFormat.getDateInstance();
			String date = formatter.format(dateOfBirth);
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			assertEquals(user.getId(), table.getValueAt(0, 0));
			assertEquals(user.getFirstName(), table.getValueAt(0, 1));
			assertEquals(user.getLastName(), table.getValueAt(0, 2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testCancelEditUser() {
		try {
			ArrayList<User> users = new ArrayList<User>(this.users);
			mockUserDao.expectAndReturn("findAll", users);
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton editButton = (JButton) find(JButton.class, "editButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
			find(JPanel.class, "editPanel");
			Date date = new Date();
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
			getHelper().sendString(new StringEventData(this, firstNameField, "1"));
			getHelper().sendString(new StringEventData(this, lastNameField, "1"));
			getHelper().sendString(new StringEventData(this, dateOfBirthField, ""));
			JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
			// find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	private void findDialog(String title) {
		JDialog dialog;
		DialogFinder dFinder = new DialogFinder(title);
		dialog = (JDialog) dFinder.find();
		assertNotNull("Could not find dialog '" + title + "'", dialog);
		getHelper().disposeWindow(dialog, this);
	}

	public void testDetailsUser() {
		try {
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());

			JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

			String title = "Edit user";
			findDialog(title);

			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

			find(JPanel.class, "detailsPanel");

			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField ageField = (JTextField) find(JTextField.class, "dateOfBirthField");

			assertEquals("Maksym", firstNameField.getText());
			assertEquals("Ostapenko", lastNameField.getText());

			JButton backButton = (JButton) find(JButton.class, "backButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, backButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();

		} catch (Exception e) {
			fail(e.toString());
		}

	}

	public void testDeleteUser() {
		try {
			User expectedUser = new User(new Long(1), "Maksym", "Ostapenko", new Date());
			mockUserDao.expect("delete", expectedUser);

			List users = new ArrayList();
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(0, table.getRowCount());
			mockUserDao.verify();

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	private void fillFields(String firstName, String lastName, Date now) {
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");

		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));

	}
}
