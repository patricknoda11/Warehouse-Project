package ui;

import model.Warehouse;
import model.exceptions.*;
import org.json.JSONObject;
import ui.components.dialog.LoadDialog;
import ui.components.dialog.SaveDialog;
import ui.components.displaypanel.CurrentInventoryPanel;
import ui.components.displaypanel.DisplayPanel;
import ui.components.displaypanel.TransactionHistoryPanel;
import ui.components.inputpanel.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Warehouse Application Graphical User Interface
 * functionalities/views:
 *      - adding a customer
 *      - importing orders
 *      - exporting orders
 *      - add monthly charge records
 *      - delete existing orders
 *      - deleting existing customers
 *      - edit previously made orders
 *      - view current inventory
 *      - view all past transactions
 * @author Patrick Noda
 */
public class WarehouseApplication extends JFrame implements ActionListener {
    private static final Color SUCCESS_TEXT_COLOR = new Color(58, 163, 64);
    private static final Color ERROR_TEXT_COLOR = new Color(187, 14, 8);
    private static final Color GUI_BACKGROUND_COLOR = new Color(250, 230, 190);
    private static final int GUI_HEIGHT = 1000;
    private static final int GUI_WIDTH = 1430;

    private Warehouse warehouse = new Warehouse();
    private JLabel commentLabel;
    private JPanel mainPanel;
    private JButton toolBarSaveButton;
    private JButton toolBarLoadButton;
    private ImportOrderPanel importOrderPanel;
    private ExportOrderPanel exportOrderPanel;
    private MonthlyChargePanel monthlyChargePanel;
    private EditOrderPanel editOrderPanel;
    private DeleteOrderPanel deleteOrderPanel;
    private DeleteCustomerPanel deleteCustomerPanel;
    private AddCustomerPanel addCustomerPanel;
    private CurrentInventoryPanel currentInventoryPanel;
    private TransactionHistoryPanel transactionHistoryPanel;

    public WarehouseApplication() {
        initializeFrameSettings();
        setUpFrameIcon();
        setupComponents();
        addActionListenersComponents();
        update();
        setVisible(true);
    }

    /**
     * Updates the GUI state by updating current inventory and history display
     */
    public void update() {
        clearMessage();
        this.currentInventoryPanel.renderDisplay();
        this.transactionHistoryPanel.renderDisplay();
    }

    /**
     * Updates the GUI state by rendering display message and updating current inventory and history display
     * @param msg The message to display to the user
     * @param isSuccess
     */
    public void update(String msg, boolean isSuccess) {
        displayMessage(msg, isSuccess);
        this.currentInventoryPanel.renderDisplay();
        this.transactionHistoryPanel.renderDisplay();
    }


    /**
     * Gets the JSONObject representation of the warehouse
     * @return JSONObject
     */
    public JSONObject getWarehouseJsonObjectRepresentation() {
        return this.warehouse.convertToJsonObject();
    }

    /** Getter */
    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == this.toolBarSaveButton) {
            saveOperation();
        }
        if (source == this.toolBarLoadButton) {
            loadOperation();
        }
    }

    /**
     * Initialize general frame settings
     */
    private void initializeFrameSettings() {
        setContentPane(this.mainPanel);
        setBackground(GUI_BACKGROUND_COLOR);
        setSize(GUI_WIDTH, GUI_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
    }

    /**
     * Setup frame icon if possible, otherwise do nothing
     */
    private void setUpFrameIcon() {
        BufferedImage icon;
        try {
            icon = ImageIO.read(new File("./data/warehouse.png"));
            setIconImage(icon);
        } catch (IOException e) {
            // do nothing
        }
    }

    /**
     * Setup components for this frame
     */
    private void setupComponents() {
        setupInputPanels();
        setupDisplayPanels();
    }

    /**
     * Setup user input panels
     */
    private void setupInputPanels() {
        setUpInputPanel(this.importOrderPanel);
        setUpInputPanel(this.exportOrderPanel);
        setUpInputPanel(this.monthlyChargePanel);
        setUpInputPanel(this.editOrderPanel);
        setUpInputPanel(this.deleteOrderPanel);
        setUpInputPanel(this.deleteCustomerPanel);
        setUpInputPanel(this.addCustomerPanel);
    }

    /**
     * Manually inject WarehouseApplication dependency
     * Simplifies process of GUI designer palette instantiation process
     * @param comp The InputPanel to inject dependency
     */
    private void setUpInputPanel(InputPanel comp) {
        comp.setWarehouseApplication(this);
    }

    /**
     * Setup all display panels
     */
    private void setupDisplayPanels() {
        setupDisplayPanel(this.currentInventoryPanel);
        setupDisplayPanel(this.transactionHistoryPanel);
    }

    /**
     * Manually inject WarehouseApplication dependency
     * Simplifies process of GUI designer palette instantiation process
     * @param comp The DisplayPanel to inject dependency
     */
    private void setupDisplayPanel(DisplayPanel comp) {
        comp.setWarehouseApplication(this);
    }

    /**
     * Add action listeners associated with this component
     */
    private void addActionListenersComponents() {
        this.toolBarSaveButton.addActionListener(this);
        this.toolBarLoadButton.addActionListener(this);
    }

    /**
     * Creates a new save dialog which allows user to choose save destination
     */
    private void saveOperation() {
        try {
            SaveDialog saveDialog = new SaveDialog(this);
            saveDialog.run();
            update(SaveDialog.SUCCESS_SAVE_FILE_FOUND, true);
        } catch (FileNotFoundException e) {
            update(SaveDialog.ERROR_SAVE_FILE_NOT_FOUND, false);
        }
    }

    /**
     * Creates a new load dialog which allows user to choose load source
     */
    private void loadOperation() {
        try {
            LoadDialog loadDialog = new LoadDialog(this);
            JSONObject jsonWarehouseRepresentation = loadDialog.run();
            if (jsonWarehouseRepresentation == null) {
                return;
            }
            this.warehouse = new Warehouse();
            this.warehouse.convertJsonObjectToWarehouse(jsonWarehouseRepresentation);
            update(LoadDialog.SUCCESS_TEXT, true);
        } catch (IOException | CorruptFileException e) {
            update(LoadDialog.ERROR_LOAD_UNSUCCESSFUL, false);
        }
    }

    /**
     * Clears message
     */
    private void clearMessage() {
        this.commentLabel.setText("");
    }

    /**
     * if isSuccess, displays a success message
     * otherwise, displays an error message
     * @param msg The message to display to the user
     * @param isSuccess
     */
    private void displayMessage(String msg, boolean isSuccess) {
        this.commentLabel.setForeground(isSuccess ? SUCCESS_TEXT_COLOR : ERROR_TEXT_COLOR);
        this.commentLabel.setText(msg);
    }
}