package model;

/**
 * This is the Package class
 * Packages can be stored in the Warehouse
 */
public class Package {

    private String ownerName;
    private String ownerPhoneNumber;
    private String ownerAddress;
    private String productContent;
    private String dateImportedIntoWarehouse;
    private String dateExportedFromWarehouse;
    private String addressExportedTo;
    private String size;
    private boolean hasBeenExportedFromWarehouse;
    private boolean isInWarehouse;

    // REQUIRES: size must be either Large, Medium, Small
    // MODIFIES: this
    // EFFECTS: instantiates new package
    //          sets this owner name to ownerName
    //          sets this owner address to ownerAddress
    //          sets this owner phone number to ownerPhoneNumber
    //          sets this product content to packageContent
    //          sets this size to size
    //          sets this hasBeenExportedFromWarehouse to false
    //          sets this isInWarehouse to false
    public Package(String ownerName,
                   String ownerAddress,
                   String ownerPhoneNumber,
                   String packageContent,
                   String size) {
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.productContent = packageContent;
        this.size = size;
        this.hasBeenExportedFromWarehouse = false;
        this.isInWarehouse = false;
    }

    // REQUIRES: Date must be written in the format Month/Day/Year
    //           Ex. February/14/2021
    // MODIFIES: this
    // EFFECTS: sets date this package is imported into warehouse to the parameter, date
    public void setDateImportedIntoWarehouse(String date) {
        this.dateImportedIntoWarehouse = date;
    }

    // REQUIRES: Date must be written in the format: Month/Day/Year
    //           Ex. February/14/2021
    // MODIFIES: this
    // EFFECTS:sets date this package is exported from warehouse to the parameter value, date
    public void setDateExportedFromWarehouse(String date) {
        this.dateExportedFromWarehouse = date;
    }

    // REQUIRES: Address must be written in the format: Street Address, City, Country, Postal Code
    //           Ex. 12345 67 ave, Vancouver, Canada, ABC 123
    // MODIFIES: this
    // EFFECTS: sets address the this package has been exported to, to the parameter value address
    public void setAddressExportedTo(String address) {
        this.addressExportedTo = address;
    }

    // REQUIRES: phone number must be String of digits
    // MODIFIES: this
    // EFFECTS: updates the owner's phone number associated with this package to new number
    public void updateOwnerPhoneNumber(String newPhoneNumber) {
        this.ownerPhoneNumber = newPhoneNumber;
    }

    // REQUIRES: Address must be written in the format: Street Address, City, Country, Postal Code
    //           Ex. 12345 67 ave, Vancouver, Canada, ABC 123
    // MODIFIES: this
    // EFFECTS: updates the owner's address with this package to new address
    public void updateOwnerAddress(String newAddress) {
        this.ownerAddress = newAddress;
    }

    // getters
    public String getOwnerAddress() {
        return this.ownerAddress;
    }

    public String getOwnerPhoneNumber() {
        return this.ownerPhoneNumber;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public boolean getHasBeenExportedFromWarehouse() {
        return this.hasBeenExportedFromWarehouse;
    }

    public boolean getIsInWarehouse() {
        return this.isInWarehouse;
    }

    public String getDateImportedIntoWarehouse() {
        return this.dateImportedIntoWarehouse;
    }

    public String getDateExportedFromWarehouse() {
        return this.dateExportedFromWarehouse;
    }

    public String getAddressExportedTo() {
        return this.addressExportedTo;
    }

    public String getProductContent() {
        return this.productContent;
    }

    public String getSize() {
        return this.size;
    }

}
