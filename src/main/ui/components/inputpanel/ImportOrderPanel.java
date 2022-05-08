package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.*;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.time.LocalDate;

/**
 * Represents a form/panel that handles user requests to import an order
 */
public class ImportOrderPanel extends InputPanel {
    private JPanel importPanel;
    private JTextField customerNameInput;
    private JTextField invoiceNumberInput;
    private JSpinner quantityInput;
    private JXDatePicker dateInput;
    private JTextField storageLocationInput;
    private JButton cancelButton;
    private JButton enterButton;
    private JTextField descriptionInput;

    public ImportOrderPanel() {
        // NOTE: cannot call addActionListener in supertype InputPanel as $$$SetupUI$$$ gets called after call to super
        //       type constructor
        addActionListeners();
    }

    @Override
    public void addActionListeners() {
        this.enterButton.addActionListener(this);
        this.cancelButton.addActionListener(this);
    }

    @Override
    public void clearUserInputs() {
        this.customerNameInput.setText("");
        this.invoiceNumberInput.setText("");
        this.descriptionInput.setText("");
        this.quantityInput.setValue(0);
        this.dateInput.setDate(null);
        this.storageLocationInput.setText("");
    }

    @Override
    public void submitInput() {
        // refine and get user input:
        String customerName = super.refineText(this.customerNameInput.getText());
        String invoiceNumber = super.refineText(this.invoiceNumberInput.getText());
        String productDescription = super.refineText(this.descriptionInput.getText());
        int quantity = (int) this.quantityInput.getValue();
        LocalDate importDate = super.getLocalDate(this.dateInput.getDate());
        String storageLocation = super.refineText(this.storageLocationInput.getText());
        String successMessage = "Successfully imported " + quantity + " units of "
                + productDescription + " --- Invoice Number " + invoiceNumber;

        importProduct(customerName, productDescription, importDate, invoiceNumber,
                quantity, storageLocation, successMessage);
    }


    /**
     * Creates an import event for an existing customer, if the user submission is valid
     *      A submission is valid if:
     *          - the indicated customer currently exists in the warehouse
     *          - an order with the specified invoice number has never been received by the warehouse
     *          - the export quantity is not <= 0
     *          - the import date is not a future date (must be a date before current date)
     *
     * On success, a success message would be displayed to the user, otherwise an error message will be displayed
     * @param cName The name of the customer that is importing
     * @param description The product description
     * @param date The import date
     * @param invNum The import invoice number associated with import event
     * @param qty The quantity of the order
     * @param location The storage location
     * @param successMsg The success message that would be displayed to the user
     */
    private void importProduct(String cName, String description, LocalDate date, String invNum,
                               int qty, String location, String successMsg) {
        try {
            Warehouse warehouse = super.warehouseApplication.getWarehouse();
            warehouse.importProduct(cName, description, date, invNum, qty, location);
            super.warehouseApplication.update(successMsg, true);
        } catch (CustomerDoesNotExistException | OrderAlreadyExistsException | QuantityNegativeException
                | QuantityZeroException | InvalidImportDateException e) {
            super.warehouseApplication.update(e.getMessage(), false);
        } finally {
            clearUserInputs();
        }
    }
}
