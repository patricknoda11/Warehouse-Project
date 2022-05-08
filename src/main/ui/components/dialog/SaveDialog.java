package ui.components.dialog;

import persistence.JsonWriter;
import ui.WarehouseApplication;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Dialog that handles the save functionality of the warehouse application
 */
public class SaveDialog extends Dialog {
    public static final String ERROR_SAVE_FILE_NOT_FOUND = "ERROR--- The save file could not be found";
    public static final String SUCCESS_SAVE_FILE_FOUND = "Warehouse has been saved to file";
    private static final String TITLE = "Select a Save Location";

    public SaveDialog(WarehouseApplication app) {
        super(app);
        super.fileChooser.setDialogTitle(TITLE);
    }

    /**
     * Displays save dialog and responds to user input
     * If the user selects a file, the JSON object representation of the warehouse is saved in the chosen file location
     * @throws FileNotFoundException Throws FileNotFoundException, if the file indicated by the user does not exist
     */
    public void run() throws FileNotFoundException {
        int retValue = super.fileChooser.showSaveDialog(this.warehouseApplication);

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = super.fileChooser.getSelectedFile();
            JsonWriter writer = new JsonWriter();
            writer.saveToFile(file, super.warehouseApplication.getWarehouseJsonObjectRepresentation());
        }
    }
}