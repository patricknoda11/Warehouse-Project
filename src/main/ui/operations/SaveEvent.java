package ui.operations;

import model.Warehouse;
import persistence.JsonWriter;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// This class handles the save portion of the warehouse application gui
public class SaveEvent implements ActionListener {
    private static final String SOURCE_FILE_1 = "./data/warehouseInventoryFile1.json";
    private static final String SOURCE_FILE_2 = "./data/warehouseInventoryFile2.json";
    private static final String SOURCE_FILE_3 = "./data/warehouseInventoryFile3.json";

    private WarehouseApplication warehouseApplication;
    private Warehouse myWarehouse;
    private JsonWriter jsonWriter;
    private JDialog saveDialog;
    private JLabel communicatorText;
    private ButtonGroup buttonGroup;
    private JRadioButton selectFileOneOption;
    private JRadioButton selectFileTwoOption;
    private JRadioButton selectFileThreeOption;
    private JButton cancelButton;
    private JButton enterButton;

    public SaveEvent(WarehouseApplication app, Warehouse warehouse, JLabel communicatorText) {
        buttonGroup = new ButtonGroup();
        selectFileOneOption = new JRadioButton("Save Changes to File 1");
        selectFileTwoOption = new JRadioButton("Save Changes to File 2");
        selectFileThreeOption = new JRadioButton("Save Changes to File 3");
        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");

        this.warehouseApplication = app;
        this.myWarehouse = warehouse;
        this.communicatorText = communicatorText;

        buttonGroup.add(selectFileOneOption);
        buttonGroup.add(selectFileTwoOption);
        buttonGroup.add(selectFileThreeOption);
    }

    public void generateSaveInventoryDialog() {
        this.saveDialog = new JDialog(this.warehouseApplication, "Save Inventory");
        saveDialog.setLayout(new BorderLayout());
        implementFunctionality();
        saveDialog.setSize(400, 200);
        saveDialog.setLocationRelativeTo(null);
        saveDialog.setVisible(true);
    }

    private void implementFunctionality() {
        JPanel topPanel = new JPanel(new GridLayout(3,1));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        saveDialog.add(topPanel, BorderLayout.CENTER);
        saveDialog.add(bottomPanel, BorderLayout.SOUTH);
        selectFileOneOption.setSelected(true);
        selectFileOneOption.setActionCommand("1");
        selectFileTwoOption.setActionCommand("2");
        selectFileThreeOption.setActionCommand("3");

        topPanel.add(selectFileOneOption);
        topPanel.add(selectFileTwoOption);
        topPanel.add(selectFileThreeOption);

        bottomPanel.add(cancelButton);
        bottomPanel.add(enterButton);

        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String saveLocation = buttonGroup.getSelection().getActionCommand();
        Toolkit.getDefaultToolkit().beep();

        if (e.getActionCommand().equals("Cancel")) {
            saveDialog.dispose();
            return;
        }

        if (e.getActionCommand().equals("Enter")) {
            chooseSaveLocation(saveLocation);
        }
    }

    private void chooseSaveLocation(String saveLocation) {
        try {
            if (saveLocation.equals("1")) {
                jsonWriter = new JsonWriter(SOURCE_FILE_1);
                saveData(SOURCE_FILE_1);
            } else if (saveLocation.equals("2")) {
                jsonWriter = new JsonWriter(SOURCE_FILE_2);
                saveData(SOURCE_FILE_2);
            } else {
                jsonWriter = new JsonWriter(SOURCE_FILE_3);
                saveData(SOURCE_FILE_3);
            }
        } catch (FileNotFoundException ex) {
            communicatorText.setText("Cannot save to specified source file... File not found.");
        } finally {
            warehouseApplication.updateCurrentInventoryDisplay();
            saveDialog.dispose();
        }
    }

    private void saveData(String sourceFile) throws FileNotFoundException {
        jsonWriter.saveToFile(myWarehouse);
        communicatorText.setText("Warehouse inventory has been saved to " + sourceFile);
    }
}
