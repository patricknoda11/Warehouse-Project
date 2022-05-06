package ui.components.dialog;

import persistence.JsonWriter;
import ui.WarehouseApplication;
import ui.components.dialog.Dialog;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents the save functionality of the warehouse application GUI
 */
public class SaveDialog extends Dialog {
    private static final String TITLE = "Select a Save Location";
    public static final String ERROR_SAVE_FILE_NOT_FOUND = "ERROR--- The save file could not be found";
    public static final String SUCCESS_SAVE_FILE_FOUND = "Warehouse has been saved to file";

    public SaveDialog(WarehouseApplication app) {
        super(app);

        // set save dialog title
        super.fileChooser.setDialogTitle(TITLE);
    }

    // MODIFIES: this
    // EFFECTS: displays save dialog and responds to user input/interaction
    public void runSaveDialog() throws FileNotFoundException {
        // makes save dialog visible at center of screen
        int retValue = super.fileChooser.showSaveDialog(this.warehouseApplication);

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = super.fileChooser.getSelectedFile();
            JsonWriter writer = new JsonWriter(file, super.warehouseApplication.getWarehouseJsonObjectRepresentation());
            writer.saveToFile();
        }
    }

}