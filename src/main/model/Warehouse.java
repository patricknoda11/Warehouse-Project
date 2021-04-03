package model;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private final ExportEvent exportEvent;
    private final ImportEvent importEvent;
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
        exportEvent = new ExportEvent(this);
        importEvent = new ImportEvent(this);
        allPackagesAvailableInInventory = new ArrayList<>();
        numberPackagesInInventory = 0;
        warehouseName = name;
        inventory.add(largeSizedPackages);
        inventory.add(mediumSizedPackages);
        inventory.add(smallSizedPackages);
    }

    // REQUIRES: goods must not be null
    // EFFECTS: directs import operation to ImportEvent class
    public void importPackage(Package goods) {
        importEvent.importPackage(goods);
    }

    // REQUIRES: goods must not be null
    // EFFECTS: redirects export operation to ExportEvent class
    public void exportPackage(Package goods, String exportAddress) {
        exportEvent.exportPackage(goods, exportAddress);
    }

    // MODIFIES: this
    // EFFECTS: sets name of warehouse
    public void setWarehouseName(String name) {
        this.warehouseName = name;
    }

    // MODIFIES: this
    // EFFECTS: sets the number of packages in inventory
    public void setNumberPackagesInInventory(int value) {
        this.numberPackagesInInventory = value;
    }

    // EFFECTS: returns warehouse represented as a JSON object
    public JSONObject convertToJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("warehouseName", this.warehouseName);
        jsonObject.put("largeSizedPackages", addListToJsonObject(this.largeSizedPackages));
        jsonObject.put("mediumSizedPackages", addListToJsonObject(this.mediumSizedPackages));
        jsonObject.put("smallSizedPackages", addListToJsonObject(this.smallSizedPackages));
        jsonObject.put("importHistory", addListToJsonObject(this.importEvent.getImportHistory()));
        jsonObject.put("exportHistory", addListToJsonObject(this.exportEvent.getExportHistory()));
        jsonObject.put("numberPackagesInInventory", this.allPackagesAvailableInInventory.size());
        return jsonObject;
    }

    // EFFECTS: adds list representation of inventory, import history, export history to JSON object
    private JSONArray addListToJsonObject(List<Package> packageList) {
        JSONArray jsonArray = new JSONArray();

        for (Package p : packageList) {
            jsonArray.put(p.convertToJsonObject());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: adds package to all packages available in inventory
    public void addToAllPackagesAvailableInInventory(Package p) {
        this.allPackagesAvailableInInventory.add(p);
    }

    // MODIFIES: this
    // EFFECTS: adds package to large section
    public void addPackageToLargeSizedPackages(Package p) {
        this.largeSizedPackages.add(p);
    }

    // MODIFIES: this
    // EFFECTS: adds package to medium section
    public void addPackageToMediumSizedPackages(Package p) {
        this.mediumSizedPackages.add(p);
    }

    // MODIFIES: this
    // EFFECTS: adds package to small section
    public void addPackageToSmallSizedPackages(Package p) {
        this.smallSizedPackages.add(p);
    }

    // getters

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

    public ImportEvent getImportEvent() {
        return this.importEvent;
    }

    public ExportEvent getExportEvent() {
        return this.exportEvent;
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

