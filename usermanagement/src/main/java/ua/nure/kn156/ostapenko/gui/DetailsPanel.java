package ua.nure.kn156.ostapenko.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn156.ostapenko.User;
import ua.nure.kn156.ostapenko.db.DatabaseException;
import ua.nure.kn156.ostapenko.util.Messages;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

/**
 * Details about a particular user in DB
 * 
 * @author Maksym
 *
 */
public class DetailsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 8717452554303988933L;
	private MainFrame parentFrame;
	private JPanel buttonsPanel;
	private JPanel fieldsPanel;
	private JButton backButton;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField ageField;
	private User user;

	public DetailsPanel(MainFrame mainFrame) {
		this.parentFrame = mainFrame;
		initialize();
	}

	public void setUser(User user) {
		// DateFormat format = DateFormat.getDateInstance();
		this.user = user;
		getFirstNameField().setText(user.getFirstName());
		getLastNameField().setText(user.getLastName());
		getAgeField().setText(String.valueOf(user.getAge()));
	}

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
	}

	private void initialize() {
		this.setName("detailsPanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(getFieldsPanel(), BorderLayout.NORTH);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.add(getBackButton());
		}
		return buttonsPanel;
	}

	private JPanel getFieldsPanel() {
		if (fieldsPanel == null) {
			fieldsPanel = new JPanel();
			fieldsPanel.setLayout(new GridLayout(3, 2));
			addLabeledField(fieldsPanel, Messages.getString("AddPanel.first_name"), getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldsPanel, Messages.getString("AddPanel.last_name"), getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldsPanel, Messages.getString("DetailsPanel.age"), getAgeField()); //$NON-NLS-1$
		}
		return fieldsPanel;
	}

	private JTextField getFirstNameField() {
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setEditable(false);
			firstNameField.setName("firstNameField"); //$NON-NLS-1$
		}
		return firstNameField;
	}

	private JTextField getLastNameField() {
		if (lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setEditable(false);
			lastNameField.setName("lastNameField"); //$NON-NLS-1$
		}
		return lastNameField;
	}

	public JTextField getAgeField() {
		if (ageField == null) {
			ageField = new JTextField();
			ageField.setEditable(false);
			ageField.setName("dateOfBirthField"); //$NON-NLS-1$
		}
		return ageField;
	}

	private JButton getBackButton() {
		if (backButton == null) {
			backButton = new JButton();
			backButton.setText(Messages.getString("DetailsPanel.back")); //$NON-NLS-1$
			backButton.setName("backButton"); //$NON-NLS-1$
			backButton.setActionCommand("back"); //$NON-NLS-1$
			backButton.addActionListener(this);
		}
		return backButton;
	}

	/**
	 * This method implements action listener for buttons on DetailsPanel
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("back".equalsIgnoreCase(e.getActionCommand())) { //$NON-NLS-1$
			setVisible(false);
			clearFields();
			parentFrame.showBrowsePanel();
		}
	}

	private void clearFields() {
		getFirstNameField().setText(""); //$NON-NLS-1$
		getLastNameField().setText(""); //$NON-NLS-1$
	}

	public void showUser(Long id) {
		try {
			User user = parentFrame.getDao().find(id);
			getFirstNameField().setText(user.getFirstName());
			getLastNameField().setText(user.getLastName());
			getAgeField().setText(DateFormat.getDateInstance().format(user.getDateOfBirthd()));
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			this.setVisible(false);
			parentFrame.showBrowsePanel();
		}
	}
}
