package ui.components;

import org.json.JSONObject;
import persistence.JsonReader;
import ui.WarehouseApplication;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 *  Represents the load functionality of the warehouse application GUI
 */
public class LoadDialog extends Dialog {
    public static final String ERROR_LOAD_UNSUCCESSFUL = "ERROR--- Load unsuccessful..";
    public static final String SUCCESS_TEXT = "Warehouse has been loaded";
    private static final String TITLE = "Select Load File";

    private JSONObject jsonWarehouseRepresentation;

    public LoadDialog(WarehouseApplication app) {
        super(app);

        // set save dialog title
        super.fileChooser.setDialogTitle(TITLE);
    }

    // MODIFIES: this
    // EFFECTS: displays load dialog and responds to user input/interaction
    public void runLoadDialog() throws IOException {
        // makes save dialog visible at center of screen
        int retValue = super.fileChooser.showOpenDialog(this.warehouseApplication);

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = super.fileChooser.getSelectedFile();
            JsonReader reader = new JsonReader(file);
            this.jsonWarehouseRepresentation = reader.getJsonRepresentation();
        }
    }

    // getters
    public JSONObject getJsonWarehouseRepresentation() {
        return this.jsonWarehouseRepresentation;
    }
}
