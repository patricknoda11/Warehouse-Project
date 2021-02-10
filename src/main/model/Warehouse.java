package model;

/**
 * This class represents the Warehouse class and the operations that occur within it
 * The Warehouse class can be used to store Packages
 */
public class Warehouse {

    // MODIFIES: this
    // EFFECTS: instantiates Warehouse by creating inventory, import history log, and export history log
    public Warehouse() {
        // stub
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public void organizePackageUnderCorrectName(Package pack) {
        // stub
    }

    // REQUIRES: package must not be null
    // MODIFIES: this
    // EFFECTS: adds package into inventory under correct owner name
    //          and logs import into import history
    public void importPackage(Package pack) {
        // stub
    }

    // REQUIRES: package must not be null
    // MODIFIES: this
    // EFFECTS: removes package from inventory and logs export into export history
    public void exportPackage(Package pack) {
        // stub
    }


    // MODIFIES: this
    // EFFECTS: adds package to import history
    private void addPackageToImportHistory(Package pack) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds package to export history
    public void addPackageToExportHistory(Package pack) {
        // stub
    }

    // getters
    public int getInventorySize() {
        return 0; // stub
    }

    public Package getPackageFromInventory(Package pack) {
        return null; // stub
    }

    public Package getPackageFromExportHistory(Package pack) {
        return null; // stub
    }

    public Package getPackageFromImportHistory(Package pack) {
        return null; // stub
    }

}
