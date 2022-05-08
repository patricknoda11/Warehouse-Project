package ui.components.inputpanel;


import model.Warehouse;
import model.exceptions.*;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.time.LocalDate;

/**
 * Represents a form/panel that handles user requests to add a monthly charge record for an order
 */
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

    public MonthlyChargePanel () {
        // NOTE: cannot call addActionListener in supertype InputPanel as $$$SetupUI$$$ gets called after call to super
        //       type constructor
        addActionListeners();
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

    /**
     * Creates a monthly charge record for an order currently in inventory, if the user submission is valid
     *      A submission is valid if:
     *          - the indicated customer currently exists in the warehouse
     *          - an order currently exists in the current inventory with the specified import invoice number
     *          - the monthly charge quantity is not <= 0
     *          - the monthly charge quantity does not exceed the quantity currently stored in inventory
     *          - the monthly charge start date occurs on the same day or after the import date
     *          - the monthly charge end date is not a future date (must be a date before current date)
     *          - the monthly charge date range is between 28-31 days
     *
     * On success, a success message would be displayed to the user, otherwise an error message will be displayed
     * @param cName The name of the customer
     * @param impInvNum The import invoice number of the order
     * @param qty The quantity of the monthly charge
     * @param startDate The monthly charge start date
     * @param endDate The monthly charge end date
     * @param monInvNum The monthly charge invoice number
     * @param successMsg The success message that would be displayed to the user
     */
    private void addMonthlyCharge(String cName, String impInvNum, int qty, LocalDate startDate,
                                  LocalDate endDate, String monInvNum, String successMsg) {
        try {
            Warehouse warehouse = super.warehouseApplication.getWarehouse();
            warehouse.recordMonthlyCharge(cName, impInvNum,
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
}
