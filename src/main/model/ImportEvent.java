package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class ImportEvent {
    private final Warehouse warehouse;
    private final List<Package> importHistory;

    public ImportEvent(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.importHistory = new ArrayList<>();
    }

    // REQUIRES: goods must not be null
    // MODIFIES: this, package
    // EFFECTS: sets package's import date and time
    //          sets the package's is in warehouse status to true
    //          adds package into inventory under correct size section
    //          records package in this warehouse's import history
    //          adds 1 to the this warehouse's number of packages in inventory
    public void importPackage(Package goods) {
        DateTimeFormatter dateStructure = DateTimeFormatter.ofPattern("d MMM, yyyy HH:mm:ss");
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        goods.setDateAndTimeImportedIntoWarehouse(dateStructure.format(currentDateAndTime));
        goods.setIsInWarehouse(true);
        addPackageToCorrectSizeSection(goods);
        this.importHistory.add(goods);
        this.warehouse.getAllPackagesAvailableInInventory().add(goods);
        this.warehouse.setNumberPackagesInInventory(this.warehouse.getNumberPackagesInInventory() + 1);
    }

    // REQUIRES: warehouse's number of packages stored in inventory < MAX_WAREHOUSE_CAPACITY
    // MODIFIES: this
    // EFFECTS: if package is "Small" add package into small section, if not then check if package is "Medium",
    //          otherwise store it in the large sized package section
    private void addPackageToCorrectSizeSection(Package goods) {
        String goodsSize = goods.getSize();
        String goodsSizeLowerCase = goodsSize.toLowerCase();
        if (goodsSizeLowerCase.equals("small")) {
            this.warehouse.getSmallSizedPackages().add(goods);
        } else if (goodsSizeLowerCase.equals("medium")) {
            this.warehouse.getMediumSizedPackages().add(goods);
        } else {
            this.warehouse.getLargeSizedPackages().add(goods);
        }
    }

    // getters
    public List<Package> getImportHistory() {
        return this.importHistory;
    }

}
