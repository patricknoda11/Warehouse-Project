package ui.components.inputpanel;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Represents a form which contains multiple components for user input, and an enter/cancel button to handle submission
 */
public abstract class InputPanel extends JComponent implements ActionListener {
    private static final char[] ADMIN_PASSWORD = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
    protected WarehouseApplication warehouseApplication;

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand == "Enter") {
            submitInput();
        }

        if (actionCommand == "Cancel") {
            clearUserInputs();
            this.warehouseApplication.update();
        }
    }

    /** Setter */
    public void setWarehouseApplication(WarehouseApplication warehouseApplication) {
        this.warehouseApplication = warehouseApplication;
    }

    /**
     *  Clears all user inputs on input panel
     */
    protected abstract void clearUserInputs();

    /**
     * Links action listeners to components on input panel
     */
    protected abstract void addActionListeners();

    /**
     * Submits the inputs from the user to perform a warehouse operation
     */
    protected abstract void submitInput();

    /**
     * Refines a text by trimming leading/trailing whitespace and converting all characters to lowercase
     * @param text The text to refine
     * @return String
     */
    protected String refineText(String text) {
        return text.trim().toLowerCase();
    }

    /**
     * Converts a Date to an equivalent LocalDate
     * If the date received is null, the current date will be returned as a LocalDate
     * @param date - the date to convert
     * @return LocalDate
     */
    protected LocalDate getLocalDate(Date date) {
        if (date == null) {
            return LocalDate.now();
        }

        Instant instant = date.toInstant();
        ZonedDateTime zoneDataTime = instant.atZone(ZoneId.systemDefault());
        return zoneDataTime.toLocalDate();
    }

    /**
     * Checks password equivalency
     * @param input the password to compare equivalency with
     * @return Boolean - returns true if passwords are equivalent, false otherwise
     */
    protected boolean passwordEquivalent(char[] input) {
        for (int i = 0; i < input.length; i++) {
            if (input[i] != ADMIN_PASSWORD[i]) {
                return false;
            }
        }
        return true;
    }
}
