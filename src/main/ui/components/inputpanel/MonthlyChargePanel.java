package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.*;
import org.jdesktop.swingx.JXDatePicker;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class MonthlyChargePanel extends InputPanel {
    private JPanel monthlyChargePanel;
    private JTextField customerNameInput;
    private JTextField importInvoiceNumberInput;
    private JXDatePicker startDateInput;
    private JXDatePicker endDateInput;
    private JSpinner quantityInput;
    private JTextField monthlyInvoiceNumberInput;
    private JButton cancelButton;
    private JButton enterButton;

    public MonthlyChargePanel (Warehouse warehouse, WarehouseApplication warehouseApplication) {
        super(warehouse, warehouseApplication);
    }

    @Override
    public void clearUserInputs() {
        this.customerNameInput.setText("");
        this.importInvoiceNumberInput.setText("");
        this.startDateInput.setDate(null);
        this.endDateInput.setDate(null);
        this.quantityInput.setValue(0);
        this.monthlyInvoiceNumberInput.setText("");
    }

    @Override
    public void addActionListeners() {
        this.enterButton.addActionListener(this);
        this.cancelButton.addActionListener(this);
    }

    @Override
    public void submitInput() {
        String customerName = refineText(this.customerNameInput.getText());
        String importInvNum = refineText(this.importInvoiceNumberInput.getText());
        int quantity = (int) this.quantityInput.getValue();
        LocalDate startDate = getLocalDate(this.startDateInput.getDate());
        LocalDate endDate = getLocalDate(this.endDateInput.getDate());
        String monthlyInvNum = refineText(this.monthlyInvoiceNumberInput.getText());
        String successMessage = "Successfully charged monthly fee for " + customerName + " from " + startDate +
                " --- " + endDate + " for " + quantity + " items";

        addMonthlyCharge(customerName, importInvNum, quantity, startDate, endDate, monthlyInvNum, successMessage);
    }

    private void addMonthlyCharge(String cName, String impInvNum, int qty, LocalDate startDate,
                                  LocalDate endDate, String monInvNum, String successMsg) {
        try {
            super.warehouse.recordMonthlyCharge(cName, impInvNum,
                    startDate, endDate, qty, monInvNum);
            super.warehouseApplication.update(successMsg, true);
        } catch (CustomerDoesNotExistException | OrderDoesNotExistException
                | QuantityZeroException | QuantityNegativeException | QuantityExceedsMaxQuantityException
                | InvalidStartDateException | InvalidEndDateException | InvalidMonthRangeException e) {
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
