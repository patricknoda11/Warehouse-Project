package model;

/**
 * This is the Package class
 * Packages can be stored in the Warehouse
 */
public class Package {


    // MODIFIES: this
    // EFFECTS: instantiates new package
    //          sets owner name to ownerName
    //          sets owner address to ownerAddress
    //          sets owner phone number to ownerPhoneNumber
    //          sets product contained to packageContent
    //          sets hasBeenExportedFromWarehouse to false
    //          sets isInWarehouse to false
    public Package(String ownerName, String ownerAddress, String ownerPhoneNumber, String packageContent) {
        // stub
    }

    // REQUIRES: Date must be written in the format Month/Day/Year
    //           Ex. February/14/2021
    // MODIFIES: this
    // EFFECTS: sets date this package is imported into warehouse to the parameter, date
    public void setDateImportedIntoWarehouse(String date) {
        // stub
    }

    // REQUIRES: Date must be written in the format: Month/Day/Year
    //           Ex. February/14/2021
    // MODIFIES: this
    // EFFECTS:sets date this package is exported from warehouse to the parameter value, date
    public void setDateExportedFromWarehouse(String date) {
        // stub
    }

    // REQUIRES: Address must be written in the format: Street Address, City, Country, Postal Code
    //           Ex. 12345 67 ave, Vancouver, Canada, ABC 123
    // MODIFIES: this
    // EFFECTS: sets address the this package has been exported to, to the parameter value address
    public void setAddressExportedTo(String address) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: updates the owner's phone number associated with this package to new number
    public void updateOwnerPhoneNumber(String newPhoneNumber) {
        // stub
    }

    // REQUIRES: Address must be written in the format: Street Address, City, Country, Postal Code
    //           Ex. 12345 67 ave, Vancouver, Canada, ABC 123
    // MODIFIES: this
    // EFFECTS: updates the owner's address with this package to new address
    public void updateOwnerAddress(String newAddress) {
        // stub
    }

    // getters
    public String getOwnerAddress() {
        return ""; // stub
    }

    public String getOwnerPhoneNumber() {
        return ""; // stub
    }

    public String getOwnerName() {
        return ""; // stub
    }

    public boolean getHasBeenExportedFromWarehouse() {
        return false; // stub
    }

    public boolean getIsInWarehouse() {
        return false; // stub
    }

    public String getDateImportedIntoWarehouse() {
        return ""; // stub
    }

    public String getDateExportedFromWarehouse() {
        return ""; // stub
    }

    public String getAddressExportedTo() {
        return ""; // stub
    }

    public String getProductContained() {
        return ""; // stub
    }

}
