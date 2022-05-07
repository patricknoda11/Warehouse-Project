package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.CustomerDoesNotExistException;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteCustomerPanel extends InputPanel {
    private JPanel deleteCustomerPanel;
    private JTextField customerNameInput;
    private JPasswordField passwordInput;
    private JButton cancelButton;
    private JButton enterButton;

    public DeleteCustomerPanel(Warehouse warehouse, WarehouseApplication warehouseApplication) {
        super(warehouse, warehouseApplication);
    }


    @Override
    public void clearUserInputs() {
        this.customerNameInput.setText("");
        this.passwordInput.setText("");
    }

    @Override
    public void addActionListeners() {
        this.cancelButton.addActionListener(this);
        this.enterButton.addActionListener(this);
    }

    @Override
    public void submitInput() {
        // get user input and refine:
        char[] inputtedPassword = this.passwordInput.getPassword();
        String customerName = refineText(this.customerNameInput.getText());
        String successMessage = "Deleted customer named " + customerName;

        deleteCustomer(inputtedPassword, customerName, successMessage);
    }

    private void deleteCustomer(char[] pwd, String cName, String successMsg) {
        String errorMessage = "Password is incorrect";

        // if the password is invalid update warehouse gui to display error message:
        if (!passwordEquivalent(pwd)) {
            super.warehouseApplication.update(errorMessage, false);
            return;
        }

        try {
            super.warehouse.deleteCustomer(cName);
            super.warehouseApplication.update(successMsg, true);
        } catch (CustomerDoesNotExistException e) {
            super.warehouseApplication.update(e.getMessage(), false);
        } finally {
            clearUserInputs();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.enterButton) {
            submitInput();
        }

        if (e.getSource() == this.cancelButton) {
            clearUserInputs();
        }
    }
}
