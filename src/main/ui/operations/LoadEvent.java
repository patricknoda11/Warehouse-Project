package ui.operations;

import model.Warehouse;
import persistence.JsonReader;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// This class handles the load portion of the warehouse application gui
public class LoadEvent implements ActionListener {
    private static final String SOURCE_FILE_1 = "./data/warehouseInventoryFile1.json";
    private static final String SOURCE_FILE_2 = "./data/warehouseInventoryFile2.json";
    private static final String SOURCE_FILE_3 = "./data/warehouseInventoryFile3.json";

    private WarehouseApplication warehouseApplication;
    private Warehouse myWarehouse;
    private JsonReader jsonReader;
    private JDialog loadDialog;
    private JLabel communicatorText;
    private ButtonGroup buttonGroup;
    private JRadioButton selectFileOneOption;
    private JRadioButton selectFileTwoOption;
    private JRadioButton selectFileThreeOption;
    private JButton cancelButton;
    private JButton enterButton;

    public LoadEvent(WarehouseApplication app, Warehouse warehouse, JLabel communicatorText) {
        buttonGroup = new ButtonGroup();
        selectFileOneOption = new JRadioButton("Load Warehouse from File 1");
        selectFileTwoOption = new JRadioButton("Load Warehouse from File 2");
        selectFileThreeOption = new JRadioButton("Load Warehouse from File 3");
        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");

        this.warehouseApplication = app;
        this.myWarehouse = warehouse;
        this.communicatorText = communicatorText;

        buttonGroup.add(selectFileOneOption);
        buttonGroup.add(selectFileTwoOption);
        buttonGroup.add(selectFileThreeOption);
    }

    public void generateLoadInventoryDialog() {
        this.loadDialog = new JDialog(this.warehouseApplication, "Load Inventory");
        loadDialog.setLayout(new BorderLayout());
        implementFunctionality();
        loadDialog.setSize(400, 250);
        loadDialog.setLocationRelativeTo(null);
        loadDialog.setVisible(true);
    }

    private void implementFunctionality() {
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

    private void loadData(String sourceFile) throws IOException {
        this.myWarehouse = jsonReader.retrieveSavedWarehouseData();
        warehouseApplication.reloadMyWarehouseFromFile(this.myWarehouse);
        communicatorText.setText("Warehouse inventory previously saved in " + sourceFile + " has been loaded");
    }

}
