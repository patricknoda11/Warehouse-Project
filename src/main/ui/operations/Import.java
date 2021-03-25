package ui.operations;

import model.Package;
import model.Warehouse;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class handles the import portion of the warehouse application gui
public class Import implements ActionListener {
    private WarehouseApplication warehouseApplication;
    private Warehouse myWarehouse;
    private JDialog importDialog;
    private JLabel communicatorText;
    private Package newPackageToImport;
    private JLabel ownerName;
    private JTextField ownerNameField;
    private JLabel ownerAddress;
    private JTextField addressField;
    private JLabel ownerPhoneNumber;
    private JTextField phoneNumberField;
    private JLabel packageContent;
    private JTextField packageContentField;
    private JLabel packageSize;
    private JTextField packageSizeField;
    private JButton cancelButton;
    private JButton enterButton;

    public Import(WarehouseApplication app, Warehouse myWarehouse, JDialog importDialog, JLabel communicatorText) {
        this.warehouseApplication = app;
        this.myWarehouse = myWarehouse;
        this.importDialog = importDialog;
        this.communicatorText = communicatorText;
        ownerName = new JLabel("Owner Name: ");
        ownerNameField = new JTextField();
        ownerAddress = new JLabel("Owner Address: ");
        addressField = new JTextField();
        ownerPhoneNumber = new JLabel("Owner Phone Number: ");
        phoneNumberField = new JTextField();
        packageContent = new JLabel("Package Content: ");
        packageContentField = new JTextField();
        packageSize = new JLabel("Package Size: \n Please enter one of large, medium or small");
        packageSizeField = new JTextField();
        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");
    }

    public void implementFunctionality() {
        importDialog.add(ownerName);
        importDialog.add(ownerNameField);
        importDialog.add(ownerAddress);
        importDialog.add(addressField);
        importDialog.add(ownerPhoneNumber);
        importDialog.add(phoneNumberField);
        importDialog.add(packageContent);
        importDialog.add(packageContentField);
        importDialog.add(packageSize);
        importDialog.add(packageSizeField);
        importDialog.add(cancelButton);
        importDialog.add(enterButton);
        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Cancel")) {
            importDialog.dispose();
        }
        if (actionCommand.equals("Enter")) {
            generateNewPackageToImport();
            myWarehouse.importPackage(newPackageToImport);
            communicatorText.setText("Package " + newPackageToImport.getPackageID()
                    + " has been stored in the inventory."
                    + " The warehouse inventory now has: " + myWarehouse.getNumberPackagesInInventory() + " items");
            warehouseApplication.updateCurrentInventoryDisplay();
            importDialog.dispose();
        }
    }

    private void generateNewPackageToImport() {
        int packageID = myWarehouse.getNumberPackagesInInventory() + 1;
        newPackageToImport = new Package(ownerNameField.getText(),
                addressField.getText(),
                phoneNumberField.getText(),
                packageContentField.getText(),
                packageSizeField.getText(),
                String.valueOf(packageID));
    }
}
