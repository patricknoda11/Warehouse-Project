package ui.components.inputpanel;

import model.exceptions.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

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

    private void deleteOrder(char[] password, String cName, String invNum, String successMsg) {
        String errorMsg = "Password is invalid";

        // if the password is invalid update warehouse gui to display error message:
        if (!passwordEquivalent(password)) {
            super.warehouseApplication.update(errorMsg, false);
            return;
        }

        try {
            super.warehouse.deleteCustomerOrder(cName, invNum);
            super.warehouseApplication.update(successMsg, true);
        } catch (CustomerDoesNotExistException | QuantityNegativeException | QuantityZeroException
                | InvalidImportDateException | OrderDoesNotExistException e) {
            super.warehouseApplication.update(e.getMessage(), false);
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
