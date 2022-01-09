package persistence;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Represents a writer that writes JSON to destination file
 */
public class JsonWriter {
    private final File destination;
    private final JSONObject source;

    public JsonWriter(File destination, JSONObject source) {
        this.destination = destination;
        this.source = source;
    }

    // MODIFIES: this
    // EFFECTS: writes to destination file JSON Object
    public void saveToFile() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(this.destination);
        printWriter.print(this.source);
        printWriter.close();
    }

    // getters
    public File getSaveLocation() {
        return this.destination;
    }

    public JSONObject getSource() {
        return this.source;
    }
}
