package ui.components.inputpanel;

import model.exceptions.CustomerDoesNotExistException;
import model.exceptions.OrderDoesNotExistException;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class EditOrderPanel extends InputPanel {
    private JPanel editOrderPanel;
    private JTextField customerNameInput;
    private JTextField invoiceNumberInput;
    private JTextField descriptionInput;
    private JTextField storageLocationInput;
    private JButton cancelButton;
    private JButton enterButton;

    public EditOrderPanel() {
        // NOTE: cannot call addActionListener in supertype InputPanel as $$$SetupUI$$$ gets called after call to super
        //       type constructor
        addActionListeners();
    }

    @Override
    public void clearUserInputs() {
        this.customerNameInput.setText("");
        this.invoiceNumberInput.setText("");
        this.descriptionInput.setText("");
        this.storageLocationInput.setText("");
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
        String invoiceNumber = refineText(this.invoiceNumberInput.getText());
        String description = refineText(this.descriptionInput.getText());
        String storageLocation = refineText(this.storageLocationInput.getText());
        String successMessage = "Successfully edited order --- Invoice Number " + invoiceNumber;

        editOrder(customerName, invoiceNumber, description, storageLocation, successMessage);
    }

    private void editOrder(String cName, String invNum, String description, String location, String successMsg) {
        try {
            super.warehouse.editExistingActiveCustomerOrder(cName, invNum, description, location);
            super.warehouseApplication.update(successMsg, true);
        } catch (CustomerDoesNotExistException | OrderDoesNotExistException e) {
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
