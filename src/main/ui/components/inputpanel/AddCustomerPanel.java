package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.CustomerAlreadyExistsException;
import model.exceptions.InvalidCustomerNameException;
import javax.swing.*;

/**
 * Represents a form/panel that handles user requests to add a new customer to the warehouse
 */
public class AddCustomerPanel extends InputPanel {
    private JPanel addCustomerPanel;
    private JTextField customerNameInput;
    private JButton cancelButton;
    private JButton enterButton;

    public AddCustomerPanel() {
        // NOTE: cannot call addActionListener in supertype InputPanel as $$$SetupUI$$$ gets called after call to super
        //       type constructor
        addActionListeners();
    }

    @Override
    public void clearUserInputs() {
        this.customerNameInput.setText("");
    }

    @Override
    public void addActionListeners() {
        this.cancelButton.addActionListener(this);
        this.enterButton.addActionListener(this);
    }

    @Override
    public void submitInput() {
        // get user input and refine:
        String customerName = refineText(this.customerNameInput.getText());
        String successMessage = "New customer named " + customerName + " has been registered";

        registerNewCustomer(customerName, successMessage);
    }

    /**
     * Registers new customer to warehouse, if the user submission is valid
     *      A submission is valid if:
     *          - a customer with the indicated name does not already exist in the warehouse
     *          - the customer name is not an empty string ("")
     *
     * On success, a success message would be displayed to the user, otherwise an error message will be displayed
     * @param cName The name of the customer to be registered
     * @param successMsg The success message to be displayed to the user
     */
    private void registerNewCustomer(String cName, String successMsg) {
        try {
            Warehouse warehouse = super.warehouseApplication.getWarehouse();
            warehouse.addCustomer(cName);
            super.warehouseApplication.update(successMsg, true);
        } catch (CustomerAlreadyExistsException | InvalidCustomerNameException e) {
            super.warehouseApplication.update(e.getMessage(), false);
        } finally {
            clearUserInputs();
        }
    }
}
