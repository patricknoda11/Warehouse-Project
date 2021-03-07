package persistence;


import model.Warehouse;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// represents writer that saves Warehouse as JSON object onto file
public class JsonWriter {
    private PrintWriter printWriter;
    private String saveLocation;

    // MODIFIES: this
    // EFFECTS: instantiates JsonWriter object, sets save location to source
    public JsonWriter(String source) {
        this.saveLocation = source;
    }

    // MODIFIES: this
    // EFFECTS: instantiates printWriter linked to save location
    //          converts Warehouse to JSON object and writes it to file
    //          closes printWriter after finished
    public void saveToFile(Warehouse wh) throws FileNotFoundException {
        File file = new File(this.saveLocation);
        printWriter = new PrintWriter(file);
        JSONObject jsonObject = wh.convertToJsonObject();
        printWriter.print(jsonObject);
        printWriter.close();
    }

    // getters
    public String getSaveLocation() {
        return this.saveLocation;
    }
}
