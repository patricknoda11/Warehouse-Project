package ui.operations;

import ui.exceptions.PackageNotFoundInInventoryException;
import model.Package;
import model.Warehouse;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// This class handles the export portion of the warehouse application gui
public class ExportEvent implements ActionListener {
    private WarehouseApplication warehouseApplication;
    private Warehouse myWarehouse;
    private JDialog exportDialog;
    private JLabel communicatorText;
    private Package packageToExport;
    private JLabel packageID;
    private JTextField packageIDField;
    private JLabel packageDestination;
    private JTextField packageDestinationField;
    private JButton cancelButton;
    private JButton enterButton;

    // MODIFIES: this
    // EFFECTS: ExportEvent constructor
    public ExportEvent(WarehouseApplication app, Warehouse warehouse, JLabel communicatorText) {
        packageID = new JLabel("ID of Package to be Exported: ");
        packageIDField = new JTextField();
        packageDestination = new JLabel("Package Destination: ");
        packageDestinationField = new JTextField();
        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");

        this.warehouseApplication = app;
        this.myWarehouse = warehouse;
        this.communicatorText = communicatorText;
    }

    // MODIFIES: this
    // EFFECTS: if the number of packages currently in inventory is == to 0, it indicates to the user that inventory
    //          does not have any packages available to export.
    //          otherwise, creates an export package dialog
    public void generateExportPackageDialog() {
        int packagesInInventory = this.myWarehouse.getNumberPackagesInInventory();
        if (packagesInInventory == 0) {
            communicatorText.setText("Warehouse inventory has no packages to export");
        } else {
            this.exportDialog = new JDialog(this.warehouseApplication, "Export Package");
            exportDialog.setLayout(new GridLayout(3,2));
            organizeExportPackageDialogContent();
            exportDialog.setSize(750, 150);
            exportDialog.setLocationRelativeTo(null);
            exportDialog.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: organizes the content found on the export package dialog
    private void organizeExportPackageDialogContent() {
        exportDialog.add(packageID);
        exportDialog.add(packageIDField);
        exportDialog.add(packageDestination);
        exportDialog.add(packageDestinationField);
        exportDialog.add(cancelButton);
        exportDialog.add(enterButton);
        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);
    }


    // MODIFIES: this
    // EFFECTS: directs user to correct operation given button clicked
    //          disposes dialog once finished and updates current inventory display on main JFrame window
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        Toolkit.getDefaultToolkit().beep();
        if (actionCommand.equals("Cancel")) {
            exportDialog.dispose();
        }

        if (actionCommand.equals("Enter")) {
            try {
                choosePackageToExport();
                myWarehouse.exportPackage(packageToExport, packageDestinationField.getText());
                communicatorText.setText("The package has been successfully shipped to: "
                        + packageToExport.getAddressExportedTo()
                        + " The warehouse inventory now has: " + myWarehouse.getNumberPackagesInInventory() + " items");
            } catch (PackageNotFoundInInventoryException ex) {
                communicatorText.setText("Package ID indicated is not available to ship."
                        + " Please try again. (Ex: 1, 2, 3)");
            } finally {
                warehouseApplication.updateCurrentInventoryDisplay();
                exportDialog.dispose();
            }
        }
    }

    // EFFECTS: searches the inventory for the package with given id
    //          if package with given id not found, throw PackageNotFoundInInventoryException
    private void choosePackageToExport() throws PackageNotFoundInInventoryException {
        List<Package> availablePackagesToExport = this.myWarehouse.getAllPackagesAvailableInInventory();
        for (Package p : availablePackagesToExport) {
            String packageID = p.getPackageID();
            if (packageID.equals(packageIDField.getText())) {
                this.packageToExport = p;
                return;
            }
        }
        throw new PackageNotFoundInInventoryException("The Indicated Package ID is Not Available to be Shipped");
    }
}

