package ui.operations;

import exceptions.PackageNotFoundException;
import model.Package;
import model.Warehouse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Export implements ActionListener {
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


    public Export(Warehouse warehouse, JDialog exportDialog, JLabel communicatorText) {
        this.myWarehouse = warehouse;
        this.exportDialog = exportDialog;
        this.communicatorText = communicatorText;
        packageID = new JLabel("ID of Package to be Exported: ");
        packageIDField = new JTextField();
        packageDestination = new JLabel("Package Destination: ");
        packageDestinationField = new JTextField();
        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");
    }


    public void implementFunctionality() {
        exportDialog.add(packageID);
        exportDialog.add(packageIDField);
        exportDialog.add(packageDestination);
        exportDialog.add(packageDestinationField);
        exportDialog.add(cancelButton);
        exportDialog.add(enterButton);
        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Cancel")) {
            exportDialog.dispose();
        }

        if (actionCommand.equals("Enter")) {
            try {
                choosePackageToExport();
                myWarehouse.exportPackage(packageToExport, packageDestinationField.getText());
                communicatorText.setText("The package has been successfully shipped to: "
                        + packageToExport.getAddressExportedTo()
                        + "The warehouse inventory now has: " + myWarehouse.getNumberPackagesInInventory() + " items");
            } catch (PackageNotFoundException ex) {
                communicatorText.setText("Package ID indicated is Not Available to Ship."
                        + " Please try again. (Ex: 1, 2, 3)");
            } finally {
                exportDialog.dispose();
            }
        }
    }

    private void choosePackageToExport() throws PackageNotFoundException {
        List<Package> availablePackagesToExport = this.myWarehouse.getAllPackagesAvailableInInventory();
        for (Package p : availablePackagesToExport) {
            String packageID = p.getPackageID();
            if (packageID.equals(packageDestinationField.getText())) {
                this.packageToExport = p;
                return;
            }
        }
        throw new PackageNotFoundException("The Indicated Package ID is Not Available to be Shipped");
    }
}

