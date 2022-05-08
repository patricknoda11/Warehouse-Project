package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.CustomerDoesNotExistException;
import model.exceptions.OrderDoesNotExistException;
import javax.swing.*;

/**
 * Represents a form/panel that handles user requests to edit an existing order
 */
public class EditOrderPanel extends InputPanel {
    private static final String SUCCESS_MESSAGE = "The specified order was edited";
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

        editOrder(customerName, invoiceNumber, description, storageLocation);
    }

    /**
     * Edits an existing order in the warehouse, if the user submission is valid
     *      A submission is valid if:
     *          - the indicated customer exists
     *          - the order with the specified invoice number exists
     *
     * On success, a success message would be displayed to the user, otherwise an error message will be displayed
     * @param cName The name of the customer
     * @param invNum The invoice number of the order to edit
     * @param description The updated/new description of the order
     * @param location The updated/new location of the order
     */
    private void editOrder(String cName, String invNum, String description, String location) {
        try {
            Warehouse warehouse = super.warehouseApplication.getWarehouse();
            warehouse.editExistingActiveCustomerOrder(cName, invNum, description, location);
            super.warehouseApplication.update(SUCCESS_MESSAGE, true);
        } catch (CustomerDoesNotExistException | OrderDoesNotExistException e) {
            super.warehouseApplication.update(e.getMessage(), false);
        } finally {
            clearUserInputs();
        }
    }
}
