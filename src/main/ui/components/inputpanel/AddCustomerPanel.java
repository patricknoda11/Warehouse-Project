package ui.components.inputpanel;

import model.exceptions.CustomerAlreadyExistsException;
import model.exceptions.InvalidCustomerNameException;
import javax.swing.*;

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

    private void registerNewCustomer(String cName, String successMsg) {
        try {
            super.warehouse.addCustomer(cName);
            super.warehouseApplication.update(successMsg, true);
        } catch (CustomerAlreadyExistsException | InvalidCustomerNameException e) {
            super.warehouseApplication.update(e.getMessage(), false);
        } finally {
            clearUserInputs();
        }
    }
}
