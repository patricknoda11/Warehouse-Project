package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Warehouse class and the operations that occur within it
 * The Warehouse class can be used to store Packages
 */
public class Warehouse {

    private List<List<Package>> inventory;
    private List<Package> largeSizedPackages;
    private List<Package> mediumSizedPackages;
    private List<Package> smallSizedPackages;
    private List<Package> exportHistory;
    private List<Package> importHistory;


    // MODIFIES: this
    // EFFECTS: instantiates Warehouse
    //          creates inventory with 3 sections: large sized packages, medium sized packages, small sized packages
    //          creates export history
    //          creates import history
    public Warehouse() {
        inventory = new ArrayList<>();
        largeSizedPackages = new ArrayList<>();
        mediumSizedPackages = new ArrayList<>();
        smallSizedPackages = new ArrayList<>();
        exportHistory = new ArrayList<>();
        importHistory = new ArrayList<>();

        inventory.add(largeSizedPackages);
        inventory.add(mediumSizedPackages);
        inventory.add(smallSizedPackages);
    }

    // REQUIRES: goods must not be null
    // MODIFIES: this
    // EFFECTS: adds package into inventory under correct size section
    //          and logs import into import history
    public void importPackage(Package goods) {
        // stub
    }

    // REQUIRES: goods must not be null
    // MODIFIES: this
    // EFFECTS: removes package from inventory and logs export into export history
    public void exportPackage(Package goods) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds package into correct section based off size
    private void addPackageToCorrectSizeSection(Package goods) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: removes Package stored in owner section
    private void removePackageFromInventory(Package goods) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds package to import history
    private void addPackageToImportHistory(Package goods) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds package to export history
    private void addPackageToExportHistory(Package goods) {
        // stub
    }

    // getters
    public int getTotalNumberOfPackagesInInventory() {
        return 0; // stub
    }

    public int getExportHistorySize() {
        return 0; // stub
    }

    public int getImportHistorySize() {
        return 0; // stub
    }

    public List<List<Package>> getInventory() {
        return null; // stub
    }

    public List<Package> getLargeSizedPackages() {
        return null; // stub
    }

    public List<Package> getMediumSizedPackages() {
        return null; // stub
    }

    public List<Package> getSmallSizedPackages() {
        return null; // stub
    }

    public List<Package> getExportHistory() {
        return null; // stub
    }

    public List<Package> getImportHistory() {
        return null; // stub
    }

    public Package getPackageDetailsFromInventory(Package goods) {
        return null; // stub
    }

    public Package getPackageFromExportHistory(Package goods) {
        return null; // stub
    }

    public Package getPackageFromImportHistory(Package goods) {
        return null; // stub
    }

}
