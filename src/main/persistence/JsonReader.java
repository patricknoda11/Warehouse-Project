package persistence;

import model.Package;
import model.Warehouse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//represents reader that reads JSON object of a Warehouse from file
public class JsonReader {
    private String saveLocation;

    // MODIFIES: this
    // EFFECTS: instantiates JsonReader object, sets saved location to source
    public JsonReader(String source) {
        this.saveLocation = source;
    }

    // EFFECTS: returns the Warehouse that was saved on file
    public Warehouse retrieveSavedWarehouseData() throws IOException {
        String stringRepresentation = retrieveFileDataInStringRepresentation();
        JSONObject jsonObject = convertStringRepresentationToJsonObject(stringRepresentation);
        return convertJsonObjectToWarehouse(jsonObject);
    }

    /**
     *  The Code in this method was borrowed from JsonSerializationDemo - JsonReader.Java
     */
    // EFFECTS: returns the string representation of the source file
    private String retrieveFileDataInStringRepresentation() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(saveLocation), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: returns JSONObject created from String representation
    private JSONObject convertStringRepresentationToJsonObject(String info) {
        return new JSONObject(info);
    }

    // EFFECTS: Returns Warehouse data from JSON object
    private Warehouse convertJsonObjectToWarehouse(JSONObject jsonObject) {
        String warehouseName = jsonObject.getString("warehouseName");
        int inventoryCount = jsonObject.getInt("numberPackagesInInventory");
        Warehouse warehouse = new Warehouse(warehouseName);
        warehouse.setWarehouseName(warehouseName);
        warehouse.setNumberPackagesInInventory(inventoryCount);
        addPackagesToLargeSizedPackages(jsonObject, warehouse);
        addPackagesToMediumSizedPackages(jsonObject, warehouse);
        addPackagesToSmallSizedPackages(jsonObject, warehouse);
        addPackagesToImportHistory(jsonObject, warehouse);
        addPackagesToExportHistory(jsonObject, warehouse);

        return warehouse;
    }

    private void addPackagesToLargeSizedPackages(JSONObject jsonObject, Warehouse warehouse) {
        JSONArray largeSizedPackages = jsonObject.getJSONArray("largeSizedPackages");
        for (Object o : largeSizedPackages) {
            JSONObject jo = (JSONObject) o;
            String ownerName = jo.getString("ownerName");
            String ownerAddress = jo.getString("ownerAddress");
            String ownerPhoneNumber = jo.getString("ownerPhoneNumber");
            String content = jo.getString("content");
            String size = jo.getString("size");
            String id = jo.getString("packageID");
            Package p = new Package(ownerName, ownerAddress, ownerPhoneNumber, content, size, id);
            p.setDateAndTimeImportedIntoWarehouse(jo.getString("dateImportedIntoWarehouse"));
            p.setDateAndTimeExportedFromWarehouse(jo.getString("dateExportedFromWarehouse"));
            p.setAddressExportedTo(jo.getString("addressExportedTo"));
            p.setHasBeenExportedFromWarehouse(jo.getBoolean("hasBeenExportedFromWarehouse"));
            p.setIsInWarehouse(jo.getBoolean("IsInWarehouse"));
            warehouse.addPackageToLargeSizedPackages(p);
            warehouse.addToAllPackagesAvailableInInventory(p);
        }
    }

    private void addPackagesToMediumSizedPackages(JSONObject jsonObject, Warehouse warehouse) {
        JSONArray mediumSizedPackages = jsonObject.getJSONArray("mediumSizedPackages");
        for (Object o : mediumSizedPackages) {
            JSONObject jo = (JSONObject) o;
            String ownerName = jo.getString("ownerName");
            String ownerAddress = jo.getString("ownerAddress");
            String ownerPhoneNumber = jo.getString("ownerPhoneNumber");
            String content = jo.getString("content");
            String size = jo.getString("size");
            String id = jo.getString("packageID");
            Package p = new Package(ownerName, ownerAddress, ownerPhoneNumber, content, size, id);
            p.setDateAndTimeImportedIntoWarehouse(jo.getString("dateImportedIntoWarehouse"));
            p.setDateAndTimeExportedFromWarehouse(jo.getString("dateExportedFromWarehouse"));
            p.setAddressExportedTo(jo.getString("addressExportedTo"));
            p.setHasBeenExportedFromWarehouse(jo.getBoolean("hasBeenExportedFromWarehouse"));
            p.setIsInWarehouse(jo.getBoolean("IsInWarehouse"));
            warehouse.addPackageToMediumSizedPackages(p);
            warehouse.addToAllPackagesAvailableInInventory(p);
        }
    }

    private void addPackagesToSmallSizedPackages(JSONObject jsonObject, Warehouse warehouse) {
        JSONArray smallSizedPackages = jsonObject.getJSONArray("smallSizedPackages");
        for (Object o : smallSizedPackages) {
            JSONObject jo = (JSONObject) o;
            String ownerName = jo.getString("ownerName");
            String ownerAddress = jo.getString("ownerAddress");
            String ownerPhoneNumber = jo.getString("ownerPhoneNumber");
            String content = jo.getString("content");
            String size = jo.getString("size");
            String id = jo.getString("packageID");
            Package p = new Package(ownerName, ownerAddress, ownerPhoneNumber, content, size, id);
            p.setDateAndTimeImportedIntoWarehouse(jo.getString("dateImportedIntoWarehouse"));
            p.setDateAndTimeExportedFromWarehouse(jo.getString("dateExportedFromWarehouse"));
            p.setAddressExportedTo(jo.getString("addressExportedTo"));
            p.setHasBeenExportedFromWarehouse(jo.getBoolean("hasBeenExportedFromWarehouse"));
            p.setIsInWarehouse(jo.getBoolean("IsInWarehouse"));
            warehouse.addPackageToSmallSizedPackages(p);
            warehouse.addToAllPackagesAvailableInInventory(p);
        }
    }

    private void addPackagesToImportHistory(JSONObject jsonObject, Warehouse warehouse) {
        JSONArray importHistory = jsonObject.getJSONArray("importHistory");
        for (Object o : importHistory) {
            JSONObject jo = (JSONObject) o;
            String ownerName = jo.getString("ownerName");
            String ownerAddress = jo.getString("ownerAddress");
            String ownerPhoneNumber = jo.getString("ownerPhoneNumber");
            String content = jo.getString("content");
            String size = jo.getString("size");
            String id = jo.getString("packageID");
            Package p = new Package(ownerName, ownerAddress, ownerPhoneNumber, content, size, id);
            p.setDateAndTimeImportedIntoWarehouse(jo.getString("dateImportedIntoWarehouse"));
            p.setDateAndTimeExportedFromWarehouse(jo.getString("dateExportedFromWarehouse"));
            p.setAddressExportedTo(jo.getString("addressExportedTo"));
            p.setHasBeenExportedFromWarehouse(jo.getBoolean("hasBeenExportedFromWarehouse"));
            p.setIsInWarehouse(jo.getBoolean("IsInWarehouse"));
            warehouse.addToImportHistory(p);
        }
    }

    private void addPackagesToExportHistory(JSONObject jsonObject, Warehouse warehouse) {
        JSONArray exportHistory = jsonObject.getJSONArray("exportHistory");
        for (Object o : exportHistory) {
            JSONObject jo = (JSONObject) o;
            String ownerName = jo.getString("ownerName");
            String ownerAddress = jo.getString("ownerAddress");
            String ownerPhoneNumber = jo.getString("ownerPhoneNumber");
            String content = jo.getString("content");
            String size = jo.getString("size");
            String id = jo.getString("packageID");
            Package p = new Package(ownerName, ownerAddress, ownerPhoneNumber, content, size, id);
            p.setDateAndTimeImportedIntoWarehouse(jo.getString("dateImportedIntoWarehouse"));
            p.setDateAndTimeExportedFromWarehouse(jo.getString("dateExportedFromWarehouse"));
            p.setAddressExportedTo(jo.getString("addressExportedTo"));
            p.setHasBeenExportedFromWarehouse(jo.getBoolean("hasBeenExportedFromWarehouse"));
            p.setIsInWarehouse(jo.getBoolean("IsInWarehouse"));
            warehouse.addToExportHistory(p);
        }
    }

    // getters
    public String getSaveLocation() {
        return this.saveLocation;
    }

}
