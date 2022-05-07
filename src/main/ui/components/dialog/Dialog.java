package ui.components.dialog;

import ui.WarehouseApplication;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionListener;

/**
 * Represents abstract dialog
 */
public abstract class Dialog {
    protected final WarehouseApplication warehouseApplication;
    protected JFileChooser fileChooser;

    protected Dialog(WarehouseApplication app) {
        this.warehouseApplication = app;
        this.fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    }

    // MODIFIES: this
    // EFFECTS: adds a file filter to only be able to see json files
    private void addFileFilter() {
        this.fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter(".json files", "json");
        this.fileChooser.addChoosableFileFilter(restrict);
    }
}
