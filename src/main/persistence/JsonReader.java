package persistence;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Represents a reader that reads text from a file and returns a JSON Object from it
 */
public class JsonReader {
    private final File source;

    public JsonReader(File source) {
        this.source = source;
    }

    // EFFECTS: returns JSON object representation of file data
    public JSONObject getJsonRepresentation() throws IOException {
        String stringRepresentation = retrieveFileDataInStringRepresentation();
        return new JSONObject(stringRepresentation);
    }

    // EFFECTS: returns the string representation of the source file
    private String retrieveFileDataInStringRepresentation() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(this.source.getAbsolutePath()), StandardCharsets.UTF_8)) {
            stream.forEach(stringBuilder::append);
        }
        return stringBuilder.toString();
    }

    // getters
    public File getSource() {
        return this.source;
    }
}