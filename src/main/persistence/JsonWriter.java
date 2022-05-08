package persistence;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Represents a writer that writes JSON to a chosen destination file
 */
public class JsonWriter {

    /**
     * Writes/saves JSON to a chosen destination
     * @param destination the location to write to
     * @param source the JSON data to save
     * @throws FileNotFoundException throws FileNotFoundException if indicated file does not exist
     */
    public void saveToFile(File destination, JSONObject source) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(destination);
        printWriter.print(source);
        printWriter.close();
    }
}
