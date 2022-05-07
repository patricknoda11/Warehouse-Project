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
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Warehouse Application Graphical User Interface
 * functionalities/views:
 *      - adding customer
 *      - importing orders
 *      - exporting orders
 *      - add monthly charge to orders
 *      - deleting existing orders
 *      - deleting existing customers
 *      - editing previously made orders
 *      - current inventory view
 *      - complete transactions view
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
    private JButton toolBarPrintButton;
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
     * Adds components to this frame
     */
    private void setupComponents() {
        setupInputPanels();
        setupDisplayPanels();
    }

    /**
     * Adds all the user-input panels
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
     * Manually inject dependency
     *  Simplifies process of GUI designer palette instantiation
     * @param comp The InputPanel to inject dependency
     */
    private void setUpInputPanel(InputPanel comp) {
        comp.setWarehouse(this.warehouse);
        comp.setWarehouseApplication(this);
    }

    /**
     * Adds all display panels
     */
    private void setupDisplayPanels() {
        setupDisplayPanel(this.currentInventoryPanel);
        setupDisplayPanel(this.transactionHistoryPanel);
    }

    /**
     * Manually inject dependency
     *  Simplifies process of GUI designer palette instantiation
     * @param comp The DisplayPanel to inject dependency
     */
    private void setupDisplayPanel(DisplayPanel comp) {
        comp.setWarehouse(this.warehouse);
        comp.setWarehouseApplication(this);
    }

    // MODIFIES: this
    // EFFECTS: adds action listeners to components associated with this
    private void addActionListenersComponents() {
        this.toolBarSaveButton.addActionListener(this);
        this.toolBarLoadButton.addActionListener(this);
        this.toolBarPrintButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: initialize general frame settings
    private void initializeFrameSettings() {
        setContentPane(this.mainPanel);
        setBackground(GUI_BACKGROUND_COLOR);
        setSize(GUI_WIDTH, GUI_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
    }

    // EFFECTS: sets up icon if it is possible
    private void setUpFrameIcon() {
        BufferedImage icon;
        try {
            icon = ImageIO.read(new File("./data/warehouse.png"));
            setIconImage(icon);
        } catch (IOException e) {
            // do nothing
        }
    }

    @Override
    // EFFECTS: directs user to correct operation given button clicks
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == this.toolBarSaveButton) {
            saveOperation();
        }
        if (source == this.toolBarLoadButton) {
            loadOperation();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Save Warehouse
     */

    // MODIFIES: this
    // EFFECTS: creates new save dialog which allows user to choose save destination
    private void saveOperation() {
        try {
            SaveDialog saveDialog = new SaveDialog(this);
            saveDialog.runSaveDialog();
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText(SaveDialog.SUCCESS_SAVE_FILE_FOUND);
        } catch (FileNotFoundException e) {
            this.commentLabel.setForeground(ERROR_TEXT_COLOR);
            this.commentLabel.setText(SaveDialog.ERROR_SAVE_FILE_NOT_FOUND);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Load Warehouse
     */

    // MODIFIES: this
    // EFFECTS: creates new load dialog which allows user to choose load source
    private void loadOperation() {
        try {
            LoadDialog loadDialog = new LoadDialog(this);
            loadDialog.runLoadDialog();
            JSONObject jsonWarehouseRepresentation = loadDialog.getJsonWarehouseRepresentation();
            this.warehouse = new Warehouse();
            this.warehouse.convertJsonObjectToWarehouse(jsonWarehouseRepresentation);
            this.currentInventoryPanel.renderDisplay();
            this.transactionHistoryPanel.renderDisplay();
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText(LoadDialog.SUCCESS_TEXT);
        } catch (IOException | CorruptFileException e) {
            this.commentLabel.setForeground(ERROR_TEXT_COLOR);
            this.commentLabel.setText(LoadDialog.ERROR_LOAD_UNSUCCESSFUL);
        }

    }

    public JSONObject getWarehouseJsonObjectRepresentation() {
        return this.warehouse.convertToJsonObject();
    }

    public void update() {
        clearDisplayMessage();
        this.currentInventoryPanel.renderDisplay();
        this.transactionHistoryPanel.renderDisplay();
    }

    public void update(String msg, boolean isSuccess) {
        displayMessage(msg, isSuccess);
        this.currentInventoryPanel.renderDisplay();
        this.transactionHistoryPanel.renderDisplay();
    }

    private void clearDisplayMessage() {
        this.commentLabel.setText("");
    }

    private void displayMessage(String msg, boolean isSuccess) {
        this.commentLabel.setForeground(isSuccess ? SUCCESS_TEXT_COLOR : ERROR_TEXT_COLOR);
        this.commentLabel.setText(msg);
    }
}