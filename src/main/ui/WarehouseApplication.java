package ui;

import model.Warehouse;
import model.exceptions.*;
import org.json.JSONObject;
import ui.components.dialog.LoadDialog;
import ui.components.dialog.SaveDialog;
import ui.components.displaypanel.CurrentInventoryPanel;
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
    // INVARIANT: the size of COLUMN_NAMES must match TABLE_COLUMN_WIDTHS
    private static final String[] DISPLAY_COLUMN_NAMES = {"Name", "Invoice #", "Qty", "Product-Description",
            "Import Date", "Location", "Export Info", "Monthly Charge"};
    private static final int[] DISPLAY_COLUMN_WIDTHS = {55, 60, 15, 180, 60, 60, 220, 280};

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
    private TableRowSorter<MyTableModel> currentInventorySorter;
    private TableRowSorter<MyTableModel> historyInventorySorter;

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
     * Current Inventory Display
     */

    // EFFECTS: updates current inventory display
    private void renderCurrentInventory() {
//        // create TableModel with updated inventory
//        MyTableModel model = new MyTableModel(this.warehouse.getOrders(true), DISPLAY_COLUMN_NAMES);
//
//        // create a TableRowSorter for inventory
//        this.currentInventorySorter = new TableRowSorter<>(model);
//
//        // set/update current inventory JTable with TableModel
//        this.currentInventoryTable.setModel(model);
//
//        // set TableRowSorter to current inventory JTable
//        this.currentInventoryTable.setRowSorter(this.currentInventorySorter);
//
//        // adjust column characteristics in current inventory JTable
//        setColumns(this.currentInventoryTable);
//        setRowProperties(this.currentInventoryTable);
    }

//    // MODIFIES: this
//    // EFFECTS: clears current inventory filter text field and removes filter on current inventory display
//    private void clearCurrentInventoryFilter() {
//        this.currentInventoryFilterText.setText("");
//        filterRow(this.currentInventoryFilterText, this.currentInventorySorter);
//    }
//
//    // EFFECTS: filters the current inventory display based off selections
//    private void filterCurrentInventoryDisplay() {
//        filterRow(this.currentInventoryFilterText, this.currentInventorySorter);
//    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * History Display
     */

    // EFFECTS: updates complete orders display
    private void renderHistory() {
//        // create TableModel with updated history
//        MyTableModel model = new MyTableModel(this.warehouse.getOrders(false), DISPLAY_COLUMN_NAMES);
//
//        // create a TableRowSorter for history
//        this.historyInventorySorter = new TableRowSorter<>(model);
//
//        // set/update history JTable with TableModel
//        this.historyTable.setModel(model);
//
//        // set TableRowSorter to history JTable
//        this.historyTable.setRowSorter(this.historyInventorySorter);
//
//        // adjust column/row characteristics in current history JTable
//        setColumns(this.historyTable);
//        setRowProperties(this.historyTable);
    }


//    // MODIFIES: this
//    // EFFECTS: clears history inventory filter text field and removes filter on history display
//    private void clearHistoryInventoryFilter() {
//        this.historyFilterText.setText("");
//        filterRow(this.historyFilterText, this.historyInventorySorter);
//    }

    // EFFECTS: filters the history display based off selections
//    private void filterHistoryDisplay() {
//        filterRow(this.historyFilterText, this.historyInventorySorter);
//    }
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
            renderCurrentInventory();
            renderHistory();
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText(LoadDialog.SUCCESS_TEXT);
        } catch (IOException | CorruptFileException e) {
            this.commentLabel.setForeground(ERROR_TEXT_COLOR);
            this.commentLabel.setText(LoadDialog.ERROR_LOAD_UNSUCCESSFUL);
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * helpers
     */

    // MODIFIES: this
    // EFFECTS: filters JTable
    private void filterRow(JTextField tf, TableRowSorter<MyTableModel> rs) {
        RowFilter<MyTableModel, Object> rowFilter;

        try {
            rowFilter = RowFilter.regexFilter(tf.getText());
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }

        rs.setRowFilter(rowFilter);
    }

    // MODIFIES: this
    // EFFECTS: if number of column matches the number of elements in COLUMN_WIDTH sets column widths on JTable,
    //          else set default width on each column
    private void setColumns(JTable jt) {
        TableColumnModel model = jt.getColumnModel();

        MultiLineTableCellRenderer rendererThree = new MultiLineTableCellRenderer(3);
        MultiLineTableCellRenderer rendererSix = new MultiLineTableCellRenderer(6);
        MultiLineTableCellRenderer rendererSeven = new MultiLineTableCellRenderer(7);

        model.getColumn(3).setCellRenderer(rendererThree);
        model.getColumn(6).setCellRenderer(rendererSix);
        model.getColumn(7).setCellRenderer(rendererSeven);

        // guard clause
        if (model.getColumnCount() != DISPLAY_COLUMN_WIDTHS.length) {
            return;
        }

        for (int i = 0; i < DISPLAY_COLUMN_WIDTHS.length; i++) {
            model.getColumn(i).setPreferredWidth(DISPLAY_COLUMN_WIDTHS[i]);
        }
    }

    // EFFECTS: dynamically sets row height depending on amount of text in each row
    private void setRowProperties(JTable jt) {
        int numRows = jt.getRowCount();
        double fontHeightPixels = jt.getFontMetrics(jt.getFont()).getHeight() * 1.05;

        for (int i = 0; i < numRows; i++) {
            double heightThree = Math.ceil(((String) jt.getValueAt(i, 3)).length() / 23.0);
            double heightSix = Math.ceil(((String) jt.getValueAt(i, 6)).length() / 28.0);
            double heightSeven = Math.ceil(((String) jt.getValueAt(i, 7)).length() / 36.0);
            double maxNumLines = Math.max(heightSeven, Math.max(heightThree, heightSix));
            jt.setRowHeight(i, (int) Math.ceil((fontHeightPixels * maxNumLines)));
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * public interface
     */

    public JSONObject getWarehouseJsonObjectRepresentation() {
        return this.warehouse.convertToJsonObject();
    }

    public void update() {
        clearDisplayMessage();
        renderCurrentInventory();
        renderHistory();
    }

    public void update(String msg, boolean isSuccess) {
        displayMessage(msg, isSuccess);
        renderCurrentInventory();
        renderHistory();
    }

    private void clearDisplayMessage() {
        this.commentLabel.setText("");
    }

    private void displayMessage(String msg, boolean isSuccess) {
        this.commentLabel.setForeground(isSuccess ? SUCCESS_TEXT_COLOR : ERROR_TEXT_COLOR);
        this.commentLabel.setText(msg);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *  MyTableModel class
     */
    private static class MyTableModel extends DefaultTableModel {

        public MyTableModel(String[][] data, String[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

    }

    /**
     *  MultiLineTableCellRenderer class
     */
    private static class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
        private final int columnIndex;

        public MultiLineTableCellRenderer(int columnIndex) {
            // save argument in class field
            this.columnIndex = columnIndex;

            // initialize cell properties to allow multiple lines
            setGeneralTextAreaProperties();
        }

        // MODIFIES: this
        // EFFECTS: sets general cell renderer properties
        private void setGeneralTextAreaProperties() {
            setWrapStyleWord(true);
            setLineWrap(true);
            setEditable(false);

            setPreferredSize(new Dimension(DISPLAY_COLUMN_WIDTHS[columnIndex], getRowHeight()));
            setMaximumSize(new Dimension(DISPLAY_COLUMN_WIDTHS[columnIndex], 100));
            setIgnoreRepaint(true);
            scrollRectToVisible(new Rectangle());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            return this;
        }
    }
}