package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.CustomerDoesNotExistException;
import javax.swing.*;

/**
 * Represents a form/panel that handles user requests to delete an existing customer
 */
public class DeleteCustomerPanel extends InputPanel {
    private static final String SUCCESS_MESSAGE = "Specified Customer was deleted";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is invalid";
    private JPanel deleteCustomerPanel;
    private JTextField customerNameInput;
    private JPasswordField passwordInput;
    private JButton cancelButton;
    private JButton enterButton;

    public DeleteCustomerPanel() {
        // NOTE: cannot call addActionListener in supertype InputPanel as $$$SetupUI$$$ gets called after call to super
        //       type constructor
        addActionListeners();
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

        deleteCustomer(inputtedPassword, customerName);
    }

    /**
     * Deletes an existing customer in the warehouse, if the user submission is valid
     *      A submission is valid if:
     *          - the indicated customer currently exists in the warehouse
     *
     * On success, a success message would be displayed to the user, otherwise an error message will be displayed
     * @param pwd The password to compare equivalency
     * @param cName The name of the customer to be removed
     */
    private void deleteCustomer(char[] pwd, String cName) {
        // if the password is invalid update warehouse gui to display error message:
        if (!passwordEquivalent(pwd)) {
            super.warehouseApplication.update(INVALID_PASSWORD_MESSAGE, false);
            return;
        }

        try {
            Warehouse warehouse = super.warehouseApplication.getWarehouse();
            warehouse.deleteCustomer(cName);
            super.warehouseApplication.update(SUCCESS_MESSAGE, true);
        } catch (CustomerDoesNotExistException e) {
            super.warehouseApplication.update(e.getMessage(), false);
        } finally {
            clearUserInputs();
        }
    }
}
