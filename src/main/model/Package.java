package model;

import org.json.JSONObject;

/**
 * Represents a package having id, owner name, content, size, owner phone number, owner address
 * date imported into warehouse, date exported from warehouse, address exported to
 * info regarding whether package has been exported, and if the package is still in warehouse
 *
 * INVARIANT: NO PACKAGES MUST HAVE THE SAME ID
 */
public class Package {
    private final String packageID;
    private final String ownerName;
    private final String content;
    private final String size;
    private String ownerPhoneNumber;
    private String ownerAddress;
    private String dateImportedIntoWarehouse;
    private String dateExportedFromWarehouse;
    private String addressExportedTo;
    private boolean hasBeenExportedFromWarehouse;
    private boolean isInWarehouse;

    // REQUIRES: size must be either large, medium, small
    // MODIFIES: this
    // EFFECTS: instantiates new package
    //          sets this owner name to ownerName
    //          sets this owner address to ownerAddress
    //          sets this owner phone number to ownerPhoneNumber
    //          sets this content to packageContent
    //          sets this size to size
    //          sets date Imported into warehouse to "has not been imported yet"
    //          sets date exported into warehouse to "has not been exported yet"
    //          sets address exported to to "has not been exported yet"
    //          sets hasBeenExportedFromWarehouse to false
    //          sets isInWarehouse to false
    //          sets this package's identification to id
    public Package(String ownerName,
                   String ownerAddress,
                   String ownerPhoneNumber,
                   String packageContent,
                   String size,
                   String id) {
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.content = packageContent;
        this.size = size;
        this.dateImportedIntoWarehouse = "has not been imported yet";
        this.dateExportedFromWarehouse = "has not been exported yet";
        this.addressExportedTo = "has not been exported yet";
        this.hasBeenExportedFromWarehouse = false;
        this.isInWarehouse = false;
        this.packageID = id;
    }

    // REQUIRES: Date is written in the format: "d MMM yyyy, HH:mm:ss"
    // MODIFIES: this
    // EFFECTS: sets this package's date imported into warehouse to the parameter value, date
    public void setDateAndTimeImportedIntoWarehouse(String date) {
        this.dateImportedIntoWarehouse = date;
    }

    // REQUIRES: Date is written in the format: "d MMM yyyy, HH:mm:ss"
    // MODIFIES: this
    // EFFECTS: sets this package's date exported from warehouse to the parameter value, date
    public void setDateAndTimeExportedFromWarehouse(String date) {
        this.dateExportedFromWarehouse = date;
    }

    // REQUIRES: Address is written in the format: Street Address, City, Country, Postal Code
    //           Ex. 12345 67 ave, Vancouver, Canada, ABC 123
    // MODIFIES: this
    // EFFECTS: sets this package's address has been exported to as the parameter value, address
    public void setAddressExportedTo(String address) {
        this.addressExportedTo = address;
    }

    // REQUIRES: phone number must be String of digits
    // MODIFIES: this
    // EFFECTS: updates this package's owner phone number to parameter value, newPhoneNumber
    public void updateOwnerPhoneNumber(String newPhoneNumber) {
        this.ownerPhoneNumber = newPhoneNumber;
    }

    // REQUIRES: Address is written in the format: Street Address, City, Country, Postal Code
    //           Ex. 12345 67 ave, Vancouver, Canada, ABC 123
    // MODIFIES: this
    // EFFECTS: updates this package's owner address to parameter value, newAddress
    public void updateOwnerAddress(String newAddress) {
        this.ownerAddress = newAddress;
    }

    // MODIFIES: this
    // EFFECTS: sets this package's hasBeenExportedFromWarehouse to parameter value, status
    public void setHasBeenExportedFromWarehouse(boolean status) {
        this.hasBeenExportedFromWarehouse = status;
    }

    // MODIFIES: this
    // EFFECTS: sets this package's isInWarehouse to parameter value, status
    public void setIsInWarehouse(boolean status) {
        this.isInWarehouse = status;
    }

    // EFFECTS: returns JSONObject representation of this package by adding details
    public JSONObject convertToJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("packageID", this.packageID);
        jsonObject.put("ownerName", this.ownerName);
        jsonObject.put("content", this.content);
        jsonObject.put("size", this.size);
        jsonObject.put("ownerPhoneNumber", this.ownerPhoneNumber);
        jsonObject.put("ownerAddress", this.ownerAddress);
        jsonObject.put("dateImportedIntoWarehouse", this.dateImportedIntoWarehouse);
        jsonObject.put("dateExportedFromWarehouse", this.dateExportedFromWarehouse);
        jsonObject.put("addressExportedTo", this.addressExportedTo);
        jsonObject.put("hasBeenExportedFromWarehouse", this.hasBeenExportedFromWarehouse);
        jsonObject.put("IsInWarehouse", this.isInWarehouse);
        return jsonObject;
    }

    @Override
    // EFFECTS: returns string representation of object
    public String toString() {
        return "Package Owner: " + this.ownerName
                + "\n Package ID: " + this.packageID
                + "\n Owner Phone Number: " + this.ownerPhoneNumber
                + "\n Owner Address: " + this.ownerAddress
                + "\n Package Content: " + this.content
                + "\n Package Size: " + this.size
                + "\n Date Imported: " + this.dateImportedIntoWarehouse
                + "\n Date Exported: " + this.dateExportedFromWarehouse
                + "\n Address Exported To: " + this.addressExportedTo;
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

    public String getContent() {
        return this.content;
    }

    public String getSize() {
        return this.size;
    }

    public String getPackageID() {
        return this.packageID;
    }

}
