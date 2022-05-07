package ui.components.inputpanel;

import model.exceptions.*;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.text.ParseException;
import java.time.LocalDate;

public class ExportOrderPanel extends InputPanel {
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
        String successMessage = "Successfully removed "
                + quantity + " units --- Invoice Number " + importInvoiceNumber;

        exportProduct(customerName, importInvoiceNumber, quantity, exportDate, exportInvoiceNumber, successMessage);
    }

    private void exportProduct(String cName, String impInvNum, int qty, LocalDate exDate, String exInvNum, String msg) {
        try {
            super.warehouse.exportOrder(cName, impInvNum, qty, exDate, exInvNum);
            super.warehouseApplication.update(msg, true);
        } catch (CustomerDoesNotExistException | OrderDoesNotExistException | QuantityNegativeException
                | QuantityZeroException | QuantityExceedsMaxQuantityException
                | RemovalQuantityExceedsAvailabilityException | InvalidExportDateException | ParseException e) {
            super.warehouseApplication.update(e.getMessage(), false);
        } finally {
            clearUserInputs();
        }
    }
}
