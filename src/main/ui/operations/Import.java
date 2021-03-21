package ui.operations;

import model.Package;
import model.Warehouse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Import implements ActionListener {
    Warehouse myWarehouse;
    JDialog importDialog;
    JLabel communicatorText;
    Package newPackageToImport;
    JLabel nameField;
    JTextField ownerName;
    JLabel addressField;
    JTextField ownerAddress;
    JLabel phoneNumberField;
    JTextField ownerPhoneNumber;
    JLabel contentField;
    JTextField packageContent;
    JLabel sizeField;
    JTextField packageSize;
    JButton cancelButton;
    JButton enterButton;

    public Import(Warehouse myWarehouse, JDialog importDialog, JLabel communicatorText) {
        this.myWarehouse = myWarehouse;
        this.importDialog = importDialog;
        this.communicatorText = communicatorText;
        nameField = new JLabel("Owner Name: ");
        ownerName = new JTextField();
        addressField = new JLabel("Owner Address: ");
        ownerAddress = new JTextField();
        phoneNumberField = new JLabel("Owner Phone Number: ");
        ownerPhoneNumber = new JTextField();
        contentField = new JLabel("Package Content: ");
        packageContent = new JTextField();
        sizeField = new JLabel("Package Size: \n Please enter one of large, medium or small");
        packageSize = new JTextField();
        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");
    }

    public void implementFunctionality() {
        importDialog.add(nameField);
        importDialog.add(ownerName);
        importDialog.add(addressField);
        importDialog.add(ownerAddress);
        importDialog.add(phoneNumberField);
        importDialog.add(ownerPhoneNumber);
        importDialog.add(contentField);
        importDialog.add(packageContent);
        importDialog.add(sizeField);
        importDialog.add(packageSize);
        importDialog.add(cancelButton);
        importDialog.add(enterButton);
        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Cancel")) {
            System.out.println();
        }
        if (actionCommand.equals("Enter")) {
            int packageID = myWarehouse.getNumberPackagesInInventory() + 1;
            newPackageToImport = new Package(ownerName.getText(),
                    ownerAddress.getText(),
                    ownerPhoneNumber.getText(),
                    packageContent.getText(),
                    packageSize.getText(),
                    String.valueOf(packageID));

            myWarehouse.importPackage(newPackageToImport);
            communicatorText.setText("Package " + newPackageToImport.getPackageID()
                    + " has been stored in the inventory."
                    + " The warehouse inventory now has: " + myWarehouse.getNumberPackagesInInventory() + " items");
            importDialog.dispose();
        }
    }
}
