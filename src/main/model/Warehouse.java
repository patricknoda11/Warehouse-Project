package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a warehouse having an inventory organized into 3 sections:
 *  - large packages
 *  - medium packages
 *  - small packages
 * A warehouse keeps record of all its imports and exports
 */
public class Warehouse {
    public static final int MAX_WAREHOUSE_CAPACITY = 100;

    private final List<List<Package>> inventory;
    private final List<Package> largeSizedPackages;
    private final List<Package> mediumSizedPackages;
    private final List<Package> smallSizedPackages;
    private final List<Package> exportHistory;
    private final List<Package> importHistory;
    private final List<Package> allPackagesAvailableInInventory;
    private int numberPackagesInInventory;
    private String warehouseName;

    // MODIFIES: this
    // EFFECTS: instantiates Warehouse
    //          creates inventory with 3 sections: large sized packages, medium sized packages, small sized packages
    //          creates export history
    //          creates import history
    //          creates list with all available packages currently in inventory (from all sections)
    //          sets number of packages in inventory to 0
    public Warehouse(String name) {
        inventory = new ArrayList<>();
        largeSizedPackages = new ArrayList<>();
        mediumSizedPackages = new ArrayList<>();
        smallSizedPackages = new ArrayList<>();
        exportHistory = new ArrayList<>();
        importHistory = new ArrayList<>();
        allPackagesAvailableInInventory = new ArrayList<>();
        numberPackagesInInventory = 0;
        warehouseName = name;
        inventory.add(largeSizedPackages);
        inventory.add(mediumSizedPackages);
        inventory.add(smallSizedPackages);
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
        this.allPackagesAvailableInInventory.add(goods);
        this.numberPackagesInInventory++;
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
        this.allPackagesAvailableInInventory.remove(goods);
        this.numberPackagesInInventory--;
    }

    // MODIFIES: this
    // EFFECTS: if package is "Small" add package into small section, if not then check if package is "Medium",
    //          otherwise store it in the large sized package section
    private void addPackageToCorrectSizeSection(Package goods) {
        String goodsSize = goods.getSize();
        String goodsSizeLowerCase = goodsSize.toLowerCase();
        if (goodsSizeLowerCase.equals("small")) {
            this.smallSizedPackages.add(goods);
        } else if (goodsSizeLowerCase.equals("medium")) {
            this.mediumSizedPackages.add(goods);
        } else {
            this.largeSizedPackages.add(goods);
        }
    }

    // MODIFIES: this
    // EFFECTS: if package is stored in Small section then remove it from there, if not found
    //          then look for package in medium section, otherwise remove it from large
    //          package section (this is where miscellaneous sized packages will be stored)
    private void removePackageFromInventory(Package goods) {
        String goodsSize = goods.getSize();
        String goodsSizeLowerCase = goodsSize.toLowerCase();
        if (goodsSizeLowerCase.equals("small")) {
            this.smallSizedPackages.remove(goods);
        } else if (goodsSizeLowerCase.equals("medium")) {
            this.mediumSizedPackages.remove(goods);
        } else {
            this.largeSizedPackages.remove(goods);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets name of warehouse
    public void setWarehouseName(String name) {
        this.warehouseName = name;
    }

    // EFFECTS: returns warehouse represented as a JSON object
    public JSONObject convertToJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("warehouseName", this.warehouseName);
        jsonObject.put("largeSizedPackages", addListToJsonObject(this.largeSizedPackages));
        jsonObject.put("mediumSizedPackages", addListToJsonObject(this.mediumSizedPackages));
        jsonObject.put("smallSizedPackages", addListToJsonObject(this.smallSizedPackages));
        jsonObject.put("importHistory", addListToJsonObject(this.importHistory));
        jsonObject.put("exportHistory", addListToJsonObject(this.exportHistory));
        jsonObject.put("numberPackagesInInventory", this.allPackagesAvailableInInventory.size());
        return jsonObject;
    }

    private JSONArray addListToJsonObject(List<Package> packageList) {
        JSONArray jsonArray = new JSONArray();

        for (Package p : packageList) {
            jsonArray.put(p.convertToJsonObject());
        }
        return jsonArray;
    }



    // getters
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

    public int getNumberPackagesInInventory() {
        return this.numberPackagesInInventory;
    }

    public List<Package> getAllPackagesAvailableInInventory() {
        return this.allPackagesAvailableInInventory;
    }

    public String getWarehouseName() {
        return this.warehouseName;
    }

}

