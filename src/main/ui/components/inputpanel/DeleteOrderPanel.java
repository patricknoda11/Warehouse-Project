package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.*;
import javax.swing.*;

/**
 * Represents a form/panel that handles user requests to delete an existing order
 */
public class DeleteOrderPanel extends InputPanel {
    private JPanel deleteOrderPanel;
    private JTextField customerNameInput;
    private JTextField invoiceNumberInput;
    private JPasswordField adminPasswordInput;
    private JButton cancelButton;
    private JButton enterButton;

    public DeleteOrderPanel() {
        // NOTE: cannot call addActionListener in supertype InputPanel as $$$SetupUI$$$ gets called after call to super
        //       type constructor
        addActionListeners();
    }

    @Override
    public void clearUserInputs() {
        this.customerNameInput.setText("");
        this.invoiceNumberInput.setText("");
        this.adminPasswordInput.setText("");
    }

    @Override
    public void addActionListeners() {
        this.enterButton.addActionListener(this);
        this.cancelButton.addActionListener(this);
    }

    @Override
    public void submitInput() {
        // get user inputs and refine:
        char[] inputtedPassword = this.adminPasswordInput.getPassword();
        String customerName = refineText(this.customerNameInput.getText());
        String invoiceNumber = refineText(this.invoiceNumberInput.getText());
        String successMessage = "Deleted " + customerName + "\'s order --- Invoice Number " + invoiceNumber;

        deleteOrder(inputtedPassword, customerName, invoiceNumber, successMessage);
    }

    /**
     * Deletes an existing order in the warehouse, if the user submission is valid
     *      A submission is valid if:
     *          - the indicated customer exists
     *          - the order with the specified invoice number exists
     *
     * On success, a success message would be displayed to the user, otherwise an error message will be displayed
     * @param pwd The password to compare equivalency
     * @param cName The name of the customer to be removed
     * @param invNum The invoice number of the order to delete
     * @param successMsg The success message that would be displayed to the user
     */
    private void deleteOrder(char[] pwd, String cName, String invNum, String successMsg) {
        String errorMsg = "Password is invalid";

        // if the password is invalid update warehouse gui to display error message:
        if (!passwordEquivalent(pwd)) {
            super.warehouseApplication.update(errorMsg, false);
            return;
        }

        try {
            Warehouse warehouse = super.warehouseApplication.getWarehouse();
            warehouse.deleteCustomerOrder(cName, invNum);
            super.warehouseApplication.update(successMsg, true);
        } catch (CustomerDoesNotExistException | QuantityNegativeException | QuantityZeroException
                | InvalidImportDateException | OrderDoesNotExistException e) {
            super.warehouseApplication.update(e.getMessage(), false);
        }
    }
}
