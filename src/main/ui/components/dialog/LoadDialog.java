package ui.components.dialog;

import org.json.JSONObject;
import persistence.JsonReader;
import ui.WarehouseApplication;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 *  Dialog that handles the load functionality of the warehouse application
 */
public class LoadDialog extends Dialog {
    public static final String ERROR_LOAD_UNSUCCESSFUL = "ERROR--- Load unsuccessful..";
    public static final String SUCCESS_TEXT = "Warehouse has been loaded";
    private static final String TITLE = "Select Load File";

    public LoadDialog(WarehouseApplication app) {
        super(app);
        super.fileChooser.setDialogTitle(TITLE);
    }

    /**
     * Displays load dialog and responds to user input
     * If the user selects a file to retrieve data from, the file data is parsed into JSONObject
     * @return JSONObject
     * @throws IOException If the JSON in the chosen file cannot be parsed
     */
    public JSONObject run() throws IOException {
        int retValue = super.fileChooser.showOpenDialog(this.warehouseApplication);

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = super.fileChooser.getSelectedFile();
            JsonReader reader = new JsonReader();
            return reader.getJsonRepresentation(file);
        }
        return null;
    }
}