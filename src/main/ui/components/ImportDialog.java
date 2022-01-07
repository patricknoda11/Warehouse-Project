package ui.components;

import model.Warehouse;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class handles the import portion of the warehouse application gui
public class ImportDialog implements ActionListener {
    private final JLabel customer = new JLabel("Customer: ");
    private final JTextField customerUserInput = new JTextField();
    private final JLabel content = new JLabel("Content: ");
    private final JTextField contentUserInput = new JTextField();
    private final JLabel quantity = new JLabel("Quantity: ");
    private final JTextField quantityUserInput = new JTextField();
    private final JLabel invoiceNumber = new JLabel("Invoice Number: ");
    private final JTextField invoiceNumberUserInput = new JTextField();
    private final JLabel importDate = new JLabel("Import Date: ");
    private final JTextField importDateUserInput = new JTextField();
    private final JLabel description = new JLabel("Description: ");
    private final JTextField descriptionUserInput = new JTextField();
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton enterButton = new JButton("Enter");

    private final WarehouseApplication warehouseApplication;
    private final JLabel communicatorText;
    private JDialog importDialog;

    // MODIFIES: this
    // EFFECTS: ImportEvent constructor
    public ImportDialog(WarehouseApplication app, JLabel communicatorText) {
        this.warehouseApplication = app;
        this.communicatorText = communicatorText;
        initializeImportDialog();
    }

    // MODIFIES: this
    // EFFECTS: if the number of packages in inventory is < to the max warehouse capacity,
    //          creates dialog for package import.
    //          otherwise, indicates to the user that inventory is full
    private void initializeImportDialog() {
//        int numberOfPackagesInInventoryBeforeImport = this.warehouseApplication.getWarehouse()
//                .getNumberPackagesInInventory(); //
        this.importDialog = new JDialog(this.warehouseApplication, "Import Package");
        this.importDialog.setLayout(new GridLayout(6, 2));
        organizeImportDialogContent();
        this.importDialog.setSize(750, 300);
        this.importDialog.setLocationRelativeTo(null);
        this.importDialog.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: organizes/structures the content found on the import package dialog
    private void organizeImportDialogContent() {
        this.importDialog.add(this.customer);
        this.importDialog.add(this.customerUserInput);
        this.importDialog.add(this.content);
        this.importDialog.add(this.contentUserInput);
        this.importDialog.add(this.quantity);
        this.importDialog.add(this.quantityUserInput);
        this.importDialog.add(this.invoiceNumber);
        this.importDialog.add(this.invoiceNumberUserInput);
        this.importDialog.add(this.importDate);
        this.importDialog.add(this.importDateUserInput);
        this.importDialog.add(this.cancelButton);
        this.importDialog.add(this.enterButton);
        this.cancelButton.addActionListener(this);
        this.enterButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: directs user to correct operation given button clicked
    //          disposes dialog once finished and updates current inventory display on main JFrame window
    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        Warehouse myWarehouse = this.warehouseApplication.getWarehouse();

        // If "Cancel" button clicked close Import Dialog
        if (actionCommand.equals("Cancel")) {
            this.importDialog.dispose();
        }

        // If "Enter" button clicked read inputs and import products
        if (actionCommand.equals("Enter")) {
            // guard clause (if any of the inputs are empty then do not Import
            if (this.customerUserInput.getText().isEmpty() || this.contentUserInput.getText().isEmpty()
                    || this.quantityUserInput.getText().isEmpty() || this.invoiceNumberUserInput.getText().isEmpty()
                    || this.importDateUserInput.getText().isEmpty() || this.descriptionUserInput.getText().isEmpty())
                return;

            // import specified package
//            this.warehouseApplication.getWarehouse().importProduct(this.customerUserInput.getText(), this.contentUserInput.getText(), this.importDateUserInput.getText(), Integer.parseInt(this.invoiceNumberUserInput.getText()), Integer.parseInt(this.quantityUserInput.getText()));


//            // update communicatorText on main window
//            this.communicatorText.setText("Order " + this.invoiceNumber
//                    + " has been stored in the inventory."
//                    + " The warehouse inventory now has: " + myWarehouse.getNumberPackagesInInventory() + " item(s).");

            // update current inventory display on main window
            this.warehouseApplication.updateCurrentInventoryDisplay();

            // dispose ImportDialog
            this.importDialog.dispose();
        }
    }

}
