package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.CustomerAlreadyExistsException;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddCustomerPanel extends InputPanel {
    private JPanel addCustomerPanel;
    private JTextField customerNameInput;
    private JButton cancelButton;
    private JButton enterButton;

    public AddCustomerPanel(Warehouse warehouse, WarehouseApplication warehouseApplication) {
        super(warehouse, warehouseApplication);
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
        } catch (CustomerAlreadyExistsException e) {
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
