package ua.nure.kn156.ostapenko.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


import javax.swing.JOptionPane;


import ua.nure.kn156.ostapenko.User;
import ua.nure.kn156.ostapenko.db.DatabaseException;


public class EditPanel extends AddPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7857674629803589954L;
	private User user;

    public EditPanel(MainFrame parent) {
        super(parent);
        setName("editPanel");
    }

/**
 * Implements buttons' functionality of EditPanel
 */
    public void actionPerformed(ActionEvent e)  {
      
        if ("ok".equalsIgnoreCase(e.getActionCommand())) {
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance();
            try {
                Date date = format.parse(getDateOfBirthField().getText());
                user.setDateOfBirthd(date);
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                
            }
            try {
                parent.getDao().update(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if ("cancel".equalsIgnoreCase(e.getActionCommand())) //$NON-NLS-1$
			this.setVisible(false);
		this.setVisible(false);
		parent.showBrowsePanel();
    }


    public void setUser(User user) {
        DateFormat format = DateFormat.getDateInstance();
        this.user = user;
        getFirstNameField().setText(user.getFirstName());
        getLastNameField().setText(user.getLastName());
        getDateOfBirthField().setText(format.format(user.getDateOfBirthd()));
    }
    
}