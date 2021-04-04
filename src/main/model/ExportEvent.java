package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// represents the export functionality/process of a warehouse
public class ExportEvent {
    private final Warehouse warehouse;
    private final List<Package> exportHistory;

    // MODIFIES: this
    // EFFECTS: creates new ExportEvent by setting warehouse field and creating a new list for export history
    public ExportEvent(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.exportHistory = new ArrayList<>();
    }

    // REQUIRES: goods must not be null
    // MODIFIES: this, package
    // EFFECTS: sets package's import date and time
    //          sets the package's address it is being exported to
    //          sets the package's is in warehouse status to false
    //          sets the package's has been exported from warehouse status to true
    //          removes package from this warehouse's inventory
    //          records package in this warehouse's export history
    public void exportPackage(Package goods, String exportAddress) {
        DateTimeFormatter dateStructure = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm:ss");
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        goods.setDateAndTimeExportedFromWarehouse(dateStructure.format(currentDateAndTime));
        goods.setAddressExportedTo(exportAddress);
        goods.setIsInWarehouse(false);
        goods.setHasBeenExportedFromWarehouse(true);
        removePackageFromInventory(goods);
        this.exportHistory.add(goods);
        this.warehouse.getAllPackagesAvailableInInventory().remove(goods);
        this.warehouse.setNumberPackagesInInventory(this.warehouse.getNumberPackagesInInventory() - 1);
    }

    // MODIFIES: this
    // EFFECTS: if package is stored in Small section then remove it from there, if not found
    //          then look for package in medium section, otherwise remove it from large
    //          package section (this is where miscellaneous sized packages will be stored)
    private void removePackageFromInventory(Package goods) {
        String goodsSize = goods.getSize();
        String goodsSizeLowerCase = goodsSize.toLowerCase();
        if (goodsSizeLowerCase.equals("small")) {
            this.warehouse.getSmallSizedPackages().remove(goods);
        } else if (goodsSizeLowerCase.equals("medium")) {
            this.warehouse.getMediumSizedPackages().remove(goods);
        } else {
            this.warehouse.getLargeSizedPackages().remove(goods);
        }
    }

    // getters
    public List<Package> getExportHistory() {
        return this.exportHistory;
    }

}
