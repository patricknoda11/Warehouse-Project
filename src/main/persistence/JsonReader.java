package persistence;

import model.Warehouse;

//represents reader that reads JSON object of a Warehouse from file
public class JsonReader {

    // EFFECTS: instantiates JsonReader object
    public JsonReader() {
        // stub
    }

    // EFFECTS: returns the Warehouse that was saved on file
    public Warehouse retrieveSavedWarehouseData() {
        return null; // stub
    }


    // EFFECTS: returns the string representation of the source file
    private String readFile() {
        return null; // stub
    }

    // EFFECTS: Returns Warehouse data from JSON object
    private Warehouse loadFile() {
        return null; // stub
    }

    // EFFECTS: finds Warehouse details that was saved
    private void findWarehouseDetails() {

    }

    // EFFECTS: finds the packages that was saved
    private void findPackageStoredInWarehouse() {
        // stub
    }

    // EFFECTS: finds the package detail that was saved
    private void findPackageDetails() {
        // stub
    }


}
