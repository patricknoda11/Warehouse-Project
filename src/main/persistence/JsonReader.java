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

    /**
     * Retrieves JSON object representation of file data
     * @param source the file that contains data that will be parsed into a JSON Object
     * @return JSON Object representation of the source file
     * @throws IOException throws IOException if the file lines are not readable
     */
    public JSONObject getJsonRepresentation(File source) throws IOException {
        String stringRepresentation = retrieveFileDataInStringRepresentation(source);
        return new JSONObject(stringRepresentation);
    }

    /**
     * Retrieves the string representation of the source file
     * @param source the file that is converted into a string
     * @return string representation of file
     * @throws IOException throws IOException if the file lines are not readable
     */
    private String retrieveFileDataInStringRepresentation(File source) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source.getAbsolutePath()), StandardCharsets.UTF_8)) {
            stream.forEach(stringBuilder::append);
        }
        return stringBuilder.toString();
    }
}