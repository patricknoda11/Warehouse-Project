package ui.components;

import model.Warehouse;
import persistence.JsonReader;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// This class handles the load portion of the warehouse application gui
public class LoadDialog implements ActionListener {
    private static final String SOURCE_FILE_1 = "./data/warehouseInventoryFile1.json";
    private static final String SOURCE_FILE_2 = "./data/warehouseInventoryFile2.json";
    private static final String SOURCE_FILE_3 = "./data/warehouseInventoryFile3.json";

    private final WarehouseApplication warehouseApplication;
    private final JLabel communicatorText;
    private final ButtonGroup buttonGroup;
    private final JRadioButton selectFileOneOption;
    private final JRadioButton selectFileTwoOption;
    private final JRadioButton selectFileThreeOption;
    private final JButton cancelButton;
    private final JButton enterButton;
    private JsonReader jsonReader;
    private JDialog loadDialog;

    // MODIFIES: this
    // EFFECTS: LoadEvent constructor
    public LoadDialog(WarehouseApplication app, JLabel communicatorText) {
        buttonGroup = new ButtonGroup();
        selectFileOneOption = new JRadioButton("Load Warehouse from File 1");
        selectFileTwoOption = new JRadioButton("Load Warehouse from File 2");
        selectFileThreeOption = new JRadioButton("Load Warehouse from File 3");
        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");

        this.warehouseApplication = app;
        this.communicatorText = communicatorText;

        buttonGroup.add(selectFileOneOption);
        buttonGroup.add(selectFileTwoOption);
        buttonGroup.add(selectFileThreeOption);
    }

    // MODIFIES: this
    // EFFECTS: creates load inventory dialog
    public void generateLoadInventoryDialog() {
        this.loadDialog = new JDialog(this.warehouseApplication, "Load Inventory");
        loadDialog.setLayout(new BorderLayout());
        organizeLoadInventoryDialogContent();
        loadDialog.setSize(400, 250);
        loadDialog.setLocationRelativeTo(null);
        loadDialog.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: organizes/structures the load inventory dialog content
    private void organizeLoadInventoryDialogContent() {
        JPanel topPanel = new JPanel(new GridLayout(3,1));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        loadDialog.add(topPanel, BorderLayout.CENTER);
        loadDialog.add(bottomPanel, BorderLayout.SOUTH);
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

    // MODIFIES: this
    // EFFECTS: directs user to correct operation given button clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String loadLocation = buttonGroup.getSelection().getActionCommand();
        Toolkit.getDefaultToolkit().beep();

        if (e.getActionCommand().equals("Cancel")) {
            loadDialog.dispose();
            return;
        }

        if (e.getActionCommand().equals("Enter")) {
            chooseLoadLocation(loadLocation);
        }
    }

    // MODIFIES: this
    // EFFECTS: chooses load location based off user input then loads data from file
    //          disposes dialog once finished and updates current inventory display on main JFrame window
    private void chooseLoadLocation(String loadLocation) {
        try {
            if (loadLocation.equals("1")) {
                jsonReader = new JsonReader(SOURCE_FILE_1);
                loadData(SOURCE_FILE_1);
            } else if (loadLocation.equals("2")) {
                jsonReader = new JsonReader(SOURCE_FILE_2);
                loadData(SOURCE_FILE_2);
            } else {
                jsonReader = new JsonReader(SOURCE_FILE_3);
                loadData(SOURCE_FILE_3);
            }
        } catch (IOException ex) {
            communicatorText.setText("Inventory Information could not be loaded from specified file...");
        } finally {
            warehouseApplication.updateCurrentInventoryDisplay();
            loadDialog.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads data from given file source
    //          if file not readable, throws IOException
    private void loadData(String sourceFile) throws IOException {
        Warehouse myWarehouse = jsonReader.retrieveSavedWarehouseData();
        warehouseApplication.reloadMyWarehouseFromFile(myWarehouse);
        communicatorText.setText("Warehouse inventory previously saved in " + sourceFile + " has been loaded");
    }

}
