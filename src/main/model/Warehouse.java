package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter dateStructure = DateTimeFormatter.ofPattern("d MMM, yyyy HH:mm:ss");
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        goods.setDateAndTimeImportedIntoWarehouse(dateStructure.format(currentDateAndTime));
        goods.setIsInWarehouse(true);
        goods.setHasBeenExportedFromWarehouse(false);
        addPackageToCorrectSizeSection(goods);
        addPackageToImportHistory(goods);
    }

    // REQUIRES: goods must not be null
    // MODIFIES: this
    // EFFECTS: removes package from inventory and logs export into export history
    public void exportPackage(Package goods) {
        DateTimeFormatter dateStructure = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm:ss");
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        goods.setDateAndTimeExportedFromWarehouse(dateStructure.format(currentDateAndTime));
        goods.setIsInWarehouse(false);
        goods.setHasBeenExportedFromWarehouse(true);
        removePackageFromInventory(goods);
        addPackageToExportHistory(goods);

    }

    // REQUIRES: goods must have valid size (Large, Medium, Small)
    // MODIFIES: this
    // EFFECTS: adds package into correct section based off size
    private void addPackageToCorrectSizeSection(Package goods) {
        String goodSize = goods.getSize();

        switch (goodSize) {
            case "Large":
                this.largeSizedPackages.add(goods);
                break;
            case "Medium":
                this.mediumSizedPackages.add(goods);
                break;
            case "Small":
                this.smallSizedPackages.add(goods);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes Package stored in owner section
    private void removePackageFromInventory(Package goods) {
        String goodSize = goods.getSize();

        switch (goodSize) {
            case "Large":
                int largeSizedGoodsLocation = this.largeSizedPackages.indexOf(goods);
                this.largeSizedPackages.remove(largeSizedGoodsLocation);
                break;
            case "Medium":
                int mediumSizedGoodsLocation = this.mediumSizedPackages.indexOf(goods);
                this.mediumSizedPackages.remove(mediumSizedGoodsLocation);
                break;
            case "Small":
                int smallSizedGoodsLocation = this.smallSizedPackages.indexOf(goods);
                this.smallSizedPackages.remove(smallSizedGoodsLocation);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds package to import history
    private void addPackageToImportHistory(Package goods) {
        this.importHistory.add(goods);
    }

    // MODIFIES: this
    // EFFECTS: adds package to export history
    private void addPackageToExportHistory(Package goods) {
        this.exportHistory.add(goods);
    }

    // getters
    public int getTotalNumberOfPackagesInInventory() {
        int largePackages = this.largeSizedPackages.size();
        int mediumPackages = this.mediumSizedPackages.size();
        int smallPackages = this.smallSizedPackages.size();
        return largePackages + mediumPackages + smallPackages;
    }

    public int getExportHistorySize() {
        return this.exportHistory.size();
    }

    public int getImportHistorySize() {
        return this.importHistory.size();
    }

    public List<List<Package>> getInventory() {
        return this.inventory;
    }

    public List<Package> getLargeSizedPackages() {
        return this.largeSizedPackages;
    }

    public List<Package> getMediumSizedPackages() {
        return this.mediumSizedPackages;
    }

    public List<Package> getSmallSizedPackages() {
        return this.smallSizedPackages;
    }

    public List<Package> getExportHistory() {
        return this.exportHistory;
    }

    public List<Package> getImportHistory() {
        return this.importHistory;
    }
}
