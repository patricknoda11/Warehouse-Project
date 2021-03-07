package persistence;


import model.Warehouse;

// represents writer that saves Warehouse as JSON object onto file
public class JsonWriter {

    // MODIFIES: this
    // EFFECTS: instantiates JsonWriter object, sets save location to source
    public JsonWriter(String source) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS:
    public void saveToFile(Warehouse wh) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: instantiates writer
    private void initiateWriter() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: converts Warehouse inventory to JSON object and writes it to file
    private void writeToFile() {
        // stub
    }


    // MODIFIES: this
    // EFFECTS: closes the writer
    private void terminateWriter() {
        // stub
    }

    // getters
    public String getSaveLocation() {
        return null;
    }
}
