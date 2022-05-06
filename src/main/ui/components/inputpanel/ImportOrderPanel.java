package ui.components.inputpanel;

import model.Warehouse;
import model.exceptions.*;
import org.jdesktop.swingx.JXDatePicker;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class ImportOrderPanel extends InputPanel {
    private static final String ERROR_MESSAGE ="";
    private JPanel importPanel;
    private JTextField customerNameInput;
    private JTextField invoiceNumberInput;
    private JSpinner quantityInput;
    private JXDatePicker dateInput;
    private JTextField storageLocationInput;
    private JButton cancelButton;
    private JButton enterButton;
    private JTextField descriptionInput;

    public ImportOrderPanel(Warehouse warehouse, WarehouseApplication warehouseApplication) {
        super(warehouse, warehouseApplication);
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


    private void importProduct(String customerName, String description, LocalDate date, String invNum,
                               int quantity, String location, String successMessage) {
        try {
            super.warehouse.importProduct(customerName, description, date, invNum, quantity, location);
            warehouseApplication.update(successMessage, true);
        } catch (CustomerDoesNotExistException | OrderAlreadyExistsException | QuantityNegativeException
                | QuantityZeroException | InvalidImportDateException e) {
            warehouseApplication.update(e.getMessage(), false);
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
