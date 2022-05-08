package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.*;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.text.ParseException;
import java.time.LocalDate;

/**
 * Represents a form/panel that handles user requests to export an existing order
 */
public class ExportOrderPanel extends InputPanel {
    private static final String SUCCESS_MESSAGE = "Specified order was successfully exported";
    private JPanel exportPanel;
    private JTextField customerNameInput;
    private JTextField importInvoiceNumberInput;
    private JSpinner quantityInput;
    private JXDatePicker dateExportInput;
    private JTextField exportInvoiceNumberInput;
    private JButton cancelButton;
    private JButton enterButton;

    public ExportOrderPanel() {
        // NOTE: cannot call addActionListener in supertype InputPanel as $$$SetupUI$$$ gets called after call to super
        //       type constructor
        addActionListeners();
    }

    @Override
    public void clearUserInputs() {
        this.customerNameInput.setText("");
        this.importInvoiceNumberInput.setText("");
        this.quantityInput.setValue(0);
        this.dateExportInput.setDate(null);
        this.exportInvoiceNumberInput.setText("");
    }

    @Override
    public void addActionListeners() {
        this.enterButton.addActionListener(this);
        this.cancelButton.addActionListener(this);
    }

    @Override
    public void submitInput() {
        // refine and get user input:
        String customerName = refineText(this.customerNameInput.getText());
        String importInvoiceNumber = refineText(this.importInvoiceNumberInput.getText());
        int quantity = (int) this.quantityInput.getValue();
        LocalDate exportDate = getLocalDate(this.dateExportInput.getDate());
        String exportInvoiceNumber = refineText(this.exportInvoiceNumberInput.getText());

        exportProduct(customerName, importInvoiceNumber, quantity, exportDate, exportInvoiceNumber);
    }

    /**
     * Creates an export event for an order currently stored in inventory, if the user submission is valid
     *      A submission is valid if:
     *          - the indicated customer currently exists in the warehouse
     *          - an order currently exists in the current inventory with the specified import invoice number
     *          - the export quantity is not <= 0
     *          - the export quantity does not exceed the quantity available to export
     *          - the export date is not a future date (must be before the current date)
     *          - the export date does not occur before the imported date
     *
     * On success, a success message would be displayed to the user, otherwise an error message will be displayed
     * @param cName The name of the customer to be removed
     * @param impInvNum The import invoice number of the order
     * @param qty The quantity to export
     * @param exDate The export date
     * @param exInvNum The export invoice number associated with export
     */
    private void exportProduct(String cName, String impInvNum, int qty, LocalDate exDate, String exInvNum) {
        try {
            Warehouse warehouse = super.warehouseApplication.getWarehouse();
            warehouse.exportOrder(cName, impInvNum, qty, exDate, exInvNum);
            super.warehouseApplication.update(SUCCESS_MESSAGE, true);
        } catch (CustomerDoesNotExistException | OrderDoesNotExistException | QuantityNegativeException
                | QuantityZeroException | QuantityExceedsMaxQuantityException
                | RemovalQuantityExceedsAvailabilityException | InvalidExportDateException | ParseException e) {
            super.warehouseApplication.update(e.getMessage(), false);
        } finally {
            clearUserInputs();
        }
    }
}
