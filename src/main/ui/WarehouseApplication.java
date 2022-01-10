package ui;

import model.Warehouse;
import model.exceptions.*;
import org.jdesktop.swingx.JXDatePicker;
import org.json.JSONObject;
import ui.components.LoadDialog;
import ui.components.SaveDialog;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

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
 */
public class WarehouseApplication extends JFrame implements ActionListener {
    public static final Color SUCCESS_TEXT_COLOR = new Color(58, 163, 64);
    public static final Color ERROR_TEXT_COLOR = new Color(187, 14, 8);
    private static final Color GUI_BACKGROUND_COLOR = new Color(250, 230, 190);
    private static final int GUI_HEIGHT = 1000;
    private static final int GUI_WIDTH = 1430;
    // INVARIANT: the size of COLUMN_NAMES must match TABLE_COLUMN_WIDTHS
    private static final String[] COLUMN_NAMES = {"Name", "Invoice #", "Qty", "Product-Description",
            "Import Date", "Location", "Export Info", "Monthly Charge"};
    private static final int[] COLUMN_WIDTHS = {55, 60, 15, 180, 60, 60, 220, 280};
    private static final char[] ADMIN_PASSWORD = {'a', 'l', 'a', 'n', 'a'};

    private JPanel mainPanel;
    private JTable historyTable;
    private JButton importEnter;
    private JButton importCancel;
    private JTextField importInvoiceNumberUserInput;
    private JTextField importProductUserInput;
    private JSpinner importQuantityUserInput;
    private JXDatePicker importDateUserInput;
    private JTextField importCustomerUserInput;
    private JSpinner exportQuantityUserInput;
    private JTextField exportImportInvoiceNumberUserInput;
    private JButton exportEnter;
    private JButton exportCancel;
    private JXDatePicker exportDateUserInput;
    private JTextField importStorageLocationUserInput;
    private JTextField exportCustomerUserInput;
    private JTextField addCustomerNameUserInput;
    private JButton addCustomerCancel;
    private JButton addCustomerEnter;
    private JTable currentInventoryTable;
    private JLabel commentLabel;
    private JTextField currentInventoryFilterText;
    private JTextField historyFilterText;
    private JButton historyFilterButton;
    private JButton currentInventoryFilterButton;
    private JButton historyFilterClearButton;
    private JButton currentInventoryFilterClearButton;
    private JTextField deleteOrderCustomerUserInput;
    private JButton deleteOrderCancel;
    private JButton deleteOrderEnter;
    private JTextField deleteOrderInvoiceNumberUserInput;
    private JButton removeCustomerCancel;
    private JButton removeCustomerEnter;
    private JPasswordField removeCustomerAdminPasswordUserInput;
    private JPasswordField deleteOrderAdminPasswordUserInput;
    private JTextField removeCustomerNameUserInput;
    private JTextField exportExportInvoiceNumberUserInput;
    private JButton addMonthlyChargeCancel;
    private JButton addMonthlyChargeEnter;
    private JSpinner addMonthlyChargeQuantityUserInput;
    private JTextField addMonthlyChargeMonthlyInvoiceNumUserInput;
    private JXDatePicker addMonthlyChargeStartDateUserInput;
    private JXDatePicker addMonthlyChargeEndDateUserInput;
    private JTextField addMonthlyChargeCustomerUserInput;
    private JTextField addMonthlyChargeImportInvoiceNumUserInput;
    private JButton toolBarSaveButton;
    private JButton toolBarLoadButton;
    private JButton toolBarPrintButton;
    private JTextField editCustomerNameUserInput;
    private JTextField editInvoiceNumberUserInput;
    private JTextField editDescriptionUserInput;
    private JButton editEnterButton;
    private JButton editCancelButton;
    private JTextField editStorageLocationUserInput;
    private TableRowSorter<MyTableModel> currentInventorySorter;
    private TableRowSorter<MyTableModel> historyInventorySorter;
    private Warehouse warehouse = new Warehouse();

    public WarehouseApplication() {
        // set Frame title

        // frame setup
        initializeFrameSettings();

        // setup frame icon if possible
        setUpFrameIcon();

        // add action listeners to import and export buttons (utilizes event propagation -- bubbling)
        addActionListenersComponents();

        // initialize current/history display
        renderCurrentInventory();
        renderHistory();

        // set as visible
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds action listeners to components associated with this
    private void addActionListenersComponents() {
        this.importCancel.addActionListener(this);
        this.importEnter.addActionListener(this);
        this.exportCancel.addActionListener(this);
        this.exportEnter.addActionListener(this);
        this.addCustomerCancel.addActionListener(this);
        this.addCustomerEnter.addActionListener(this);
        this.currentInventoryFilterButton.addActionListener(this);
        this.currentInventoryFilterClearButton.addActionListener(this);
        this.historyFilterButton.addActionListener(this);
        this.historyFilterClearButton.addActionListener(this);
        this.deleteOrderCancel.addActionListener(this);
        this.deleteOrderEnter.addActionListener(this);
        this.removeCustomerCancel.addActionListener(this);
        this.removeCustomerEnter.addActionListener(this);
        this.addMonthlyChargeCancel.addActionListener(this);
        this.addMonthlyChargeEnter.addActionListener(this);
        this.toolBarSaveButton.addActionListener(this);
        this.toolBarLoadButton.addActionListener(this);
        this.toolBarPrintButton.addActionListener(this);
        this.editCancelButton.addActionListener(this);
        this.editEnterButton.addActionListener(this);
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
        if (source == this.importCancel) {
            clearImportUserInputs();
        }
        if (source == this.importEnter) {
            importProduct();
        }
        if (source == this.exportCancel) {
            clearExportUserInputs();
        }
        if (source == this.exportEnter) {
            exportProduct();
        }
        if (source == this.addCustomerCancel) {
            clearAddCustomerUserInputs();
        }
        if (source == this.addCustomerEnter) {
            registerNewCustomer();
        }
        if (source == this.currentInventoryFilterButton) {
            filterCurrentInventoryDisplay();
        }
        if (source == this.currentInventoryFilterClearButton) {
            clearCurrentInventoryFilter();
        }
        if (source == this.historyFilterButton) {
            filterHistoryDisplay();
        }
        if (source == this.historyFilterClearButton) {
            clearHistoryInventoryFilter();
        }
        if (source == this.removeCustomerEnter) {
            deleteCustomer();
        }
        if (source == this.removeCustomerCancel) {
            clearRemoveCustomerUserInputs();
        }
        if (source == this.deleteOrderEnter) {
            deleteOrder();
        }
        if (source == this.deleteOrderCancel) {
            clearDeleteOrderUserInputs();
        }
        if (source == this.addMonthlyChargeCancel) {
            clearAddMonthlyChargeUserInputs();
        }
        if (source == this.addMonthlyChargeEnter) {
            addMonthlyCharge();
        }
        if (source == this.toolBarSaveButton) {
            saveOperation();
        }
        if (source == this.toolBarLoadButton) {
            loadOperation();
        }
        if (source == this.editCancelButton) {
            clearEditUserInputs();
        }
        if (source == this.editEnterButton) {
            editOrder();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * In/Import Panel
     */

    // EFFECTS: sends import instructions to warehouse
    private void importProduct() {
        // retrieve user inputs
        String name = this.importCustomerUserInput.getText().trim().toLowerCase();
        String invoiceNumber = this.importInvoiceNumberUserInput.getText().trim().toLowerCase();
        String content = this.importProductUserInput.getText().trim().toLowerCase();
        int quantity = (Integer) this.importQuantityUserInput.getValue();
        LocalDate importDate = getLocalDate(this.importDateUserInput.getDate());
        String location = this.importStorageLocationUserInput.getText().trim().toLowerCase();

        try {
            this.warehouse.importProduct(name, content, importDate, invoiceNumber, quantity, location);
            renderCurrentInventory();
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText("Successfully imported " + quantity + " pallets of "
                    + content + " --- Invoice Number " + invoiceNumber);
        } catch (CustomerDoesNotExistException | OrderAlreadyExistsException | QuantityNegativeException
                | QuantityZeroException | InvalidImportDateException e) {
            displayErrorMessage(e);
        } finally {
            clearImportUserInputs();
        }
    }

    // MODIFIES: this
    // EFFECTS: clears user inputs on import screen
    private void clearImportUserInputs() {
        this.importCustomerUserInput.setText("");
        this.importInvoiceNumberUserInput.setText("");
        this.importProductUserInput.setText("");
        this.importQuantityUserInput.setValue(0);
        this.importDateUserInput.setDate(null);
        this.importStorageLocationUserInput.setText("");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Out/Export Panel
     */

    // EFFECTS: a helper that sends export instructions to warehouse
    private void exportProduct() {
        // retrieve user inputs
        String customerName = this.exportCustomerUserInput.getText().trim().toLowerCase();
        String importInvoiceNumber = this.exportImportInvoiceNumberUserInput.getText().trim().toLowerCase();
        int quantity = (Integer) this.exportQuantityUserInput.getValue();
        LocalDate exportDate = getLocalDate(this.exportDateUserInput.getDate());
        String exportInvoiceNumber = this.exportExportInvoiceNumberUserInput.getText().trim().toLowerCase();

        try {
            this.warehouse.exportOrder(customerName, importInvoiceNumber,
                    quantity, exportDate, exportInvoiceNumber);
            renderCurrentInventory();
            renderHistory();
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText("Successfully removed "
                    + quantity + " pallets --- Invoice Number " + importInvoiceNumber);
        } catch (CustomerDoesNotExistException | OrderDoesNotExistException | QuantityNegativeException
                | QuantityZeroException | QuantityExceedsMaxQuantityException
                | RemovalQuantityExceedsAvailabilityException | InvalidExportDateException | ParseException e) {
            displayErrorMessage(e);
        } finally {
            clearExportUserInputs();
        }
    }

    // MODIFIES: this
    // EFFECTS: that clears user inputs on export screen
    private void clearExportUserInputs() {
        this.exportCustomerUserInput.setText("");
        this.exportImportInvoiceNumberUserInput.setText("");
        this.exportQuantityUserInput.setValue(0);
        this.exportDateUserInput.setDate(null);
        this.exportExportInvoiceNumberUserInput.setText("");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Add Customer Panel
     */

    // EFFECTS: sends customer registration instructions to warehouse
    private void registerNewCustomer() {
        // retrieve user input
        String name = this.addCustomerNameUserInput.getText().trim().toLowerCase();

        try {
            this.warehouse.addCustomer(name);
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText("New Customer named \""
                    + name + "\" has been registered");
        } catch (CustomerAlreadyExistsException e) {
            displayErrorMessage(e);
        } finally {
            clearAddCustomerUserInputs();
        }
    }

    // MODIFIES: this
    // EFFECTS: clears user inputs on Add Customer screen
    private void clearAddCustomerUserInputs() {
        this.addCustomerNameUserInput.setText("");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Add Monthly Charge Panel
     */

    // MODIFIES: this
    // EFFECTS: adds monthly charge with specified details to Order stored in Warehouse
    private void addMonthlyCharge() {
        String customerName = this.addMonthlyChargeCustomerUserInput.getText().trim().toLowerCase();
        String importInvoiceNumber = this.addMonthlyChargeImportInvoiceNumUserInput.getText().trim().toLowerCase();
        int quantity = (Integer) this.addMonthlyChargeQuantityUserInput.getValue();
        LocalDate startDate = getLocalDate(this.addMonthlyChargeStartDateUserInput.getDate());
        LocalDate endDate = getLocalDate(this.addMonthlyChargeEndDateUserInput.getDate());
        String monthlyInvoiceNumber = this.addMonthlyChargeMonthlyInvoiceNumUserInput.getText().trim().toLowerCase();

        try {
            this.warehouse.recordMonthlyCharge(customerName, importInvoiceNumber,
                    startDate, endDate, quantity, monthlyInvoiceNumber);
            renderCurrentInventory();

        } catch (CustomerDoesNotExistException | OrderDoesNotExistException
                | QuantityZeroException | QuantityNegativeException | QuantityExceedsMaxQuantityException
                | InvalidStartDateException | InvalidEndDateException | InvalidMonthRangeException e) {
            displayErrorMessage(e);
        } finally {
            clearAddMonthlyChargeUserInputs();
        }
    }

    // MODIFIES: this
    // EFFECTS: clears user inputs on Add Monthly Charge screen
    private void clearAddMonthlyChargeUserInputs() {
        this.addMonthlyChargeCustomerUserInput.setText("");
        this.addMonthlyChargeImportInvoiceNumUserInput.setText("");
        this.addMonthlyChargeStartDateUserInput.setDate(null);
        this.addMonthlyChargeEndDateUserInput.setDate(null);
        this.addMonthlyChargeQuantityUserInput.setValue(0);
        this.addMonthlyChargeMonthlyInvoiceNumUserInput.setText("");
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Edit Panel
     */

    // EFFECTS: edits an existing order's properties
    private void editOrder() {
        String name = this.editCustomerNameUserInput.getText();
        String invoiceNumber = this.editInvoiceNumberUserInput.getText();
        String description = this.editDescriptionUserInput.getText();
        String storageLocation = this.editStorageLocationUserInput.getText();

        try {
            this.warehouse.editExistingActiveCustomerOrder(name, invoiceNumber, description, storageLocation);
            renderCurrentInventory();
            renderHistory();
        } catch (CustomerDoesNotExistException | OrderDoesNotExistException e) {
            displayErrorMessage(e);
        } finally {
            clearEditUserInputs();
        }
    }

    // MODIFIES: this
    // EFFECTS: clears user inputs on edit screen
    private void clearEditUserInputs() {
        this.editCustomerNameUserInput.setText("");
        this.editInvoiceNumberUserInput.setText("");
        this.editDescriptionUserInput.setText("");
        this.editStorageLocationUserInput.setText("");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Delete Order Panel
     */

    // EFFECTS: deletes an existing order from warehouse inventory
    private void deleteOrder() {
        // if the password inputted does not match the ADMIN_PASSWORD return (guard clause)
        char[] inputtedPassword = this.deleteOrderAdminPasswordUserInput.getPassword();
        if (!checkPasswordEquivalence(inputtedPassword)) {
            this.commentLabel.setForeground(ERROR_TEXT_COLOR);
            this.commentLabel.setText("ERROR--- The password inputted is incorrect, please try again..");
            clearDeleteOrderUserInputs();
            return;
        }

        // retrieve user inputs
        String name = this.deleteOrderCustomerUserInput.getText().trim().toLowerCase();
        String invoiceNumber = this.deleteOrderInvoiceNumberUserInput.getText().trim().toLowerCase();

        try {
            this.warehouse.deleteCustomerOrder(name, invoiceNumber);
            renderCurrentInventory();           // updates current inventory display
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText("Successfully deleted " + name + "'s order --- Invoice Number " + invoiceNumber);
            renderCurrentInventory();
            renderHistory();
        } catch (CustomerDoesNotExistException | QuantityNegativeException | QuantityZeroException
                | InvalidImportDateException | OrderDoesNotExistException e) {
            displayErrorMessage(e);
        } finally {
            clearDeleteOrderUserInputs();
        }
    }

    // MODIFIES: this
    // EFFECTS: clears user inputs on delete order screen
    private void clearDeleteOrderUserInputs() {
        this.deleteOrderCustomerUserInput.setText("");
        this.deleteOrderInvoiceNumberUserInput.setText("");
        this.deleteOrderAdminPasswordUserInput.setText("");
    }




    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Delete Customer Panel
     */

    // EFFECTS: if the password inputted is correct, deletes the customer specified
    private void deleteCustomer() {
        // if the password inputted does not match the ADMIN_PASSWORD return (guard clause)
        char[] inputtedPassword = this.removeCustomerAdminPasswordUserInput.getPassword();
        if (!checkPasswordEquivalence(inputtedPassword)) {
            this.commentLabel.setForeground(ERROR_TEXT_COLOR);
            this.commentLabel.setText("ERROR--- The password inputted is incorrect, please try again..");
            clearRemoveCustomerUserInputs();
            return;
        }

        // retrieve user input
        String name = this.removeCustomerNameUserInput.getText().trim().toLowerCase();

        try {
            this.warehouse.deleteCustomer(name);
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText("Customer named \""
                    + name + "\" has been deleted");
            renderCurrentInventory();
            renderHistory();
        } catch (CustomerDoesNotExistException e) {
            displayErrorMessage(e);
        } finally {
            clearRemoveCustomerUserInputs();
        }
    }

    // MODIFIES: this
    // EFFECTS: clears user inputs on Remove Customer Panel
    private void clearRemoveCustomerUserInputs() {
        this.removeCustomerNameUserInput.setText("");
        this.removeCustomerAdminPasswordUserInput.setText("");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Current Inventory Display
     */

    // EFFECTS: updates current inventory display
    private void renderCurrentInventory() {
        // create TableModel with updated inventory
        MyTableModel model = new MyTableModel(this.warehouse.getOrders(true), COLUMN_NAMES);

        // create a TableRowSorter for inventory
        this.currentInventorySorter = new TableRowSorter<>(model);

        // set/update current inventory JTable with TableModel
        this.currentInventoryTable.setModel(model);

        // set TableRowSorter to current inventory JTable
        this.currentInventoryTable.setRowSorter(this.currentInventorySorter);

        // adjust column characteristics in current inventory JTable
        setColumns(this.currentInventoryTable);
        setRowProperties(this.currentInventoryTable);
    }

    // MODIFIES: this
    // EFFECTS: clears current inventory filter text field and removes filter on current inventory display
    private void clearCurrentInventoryFilter() {
        this.currentInventoryFilterText.setText("");
        filterRow(this.currentInventoryFilterText, this.currentInventorySorter);
    }

    // EFFECTS: filters the current inventory display based off selections
    private void filterCurrentInventoryDisplay() {
        filterRow(this.currentInventoryFilterText, this.currentInventorySorter);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * History Display
     */

    // EFFECTS: updates complete orders display
    private void renderHistory() {
        // create TableModel with updated history
        MyTableModel model = new MyTableModel(this.warehouse.getOrders(false), COLUMN_NAMES);

        // create a TableRowSorter for history
        this.historyInventorySorter = new TableRowSorter<>(model);

        // set/update history JTable with TableModel
        this.historyTable.setModel(model);

        // set TableRowSorter to history JTable
        this.historyTable.setRowSorter(this.historyInventorySorter);

        // adjust column/row characteristics in current history JTable
        setColumns(this.historyTable);
        setRowProperties(this.historyTable);
    }


    // MODIFIES: this
    // EFFECTS: clears history inventory filter text field and removes filter on history display
    private void clearHistoryInventoryFilter() {
        this.historyFilterText.setText("");
        filterRow(this.historyFilterText, this.historyInventorySorter);
    }

    // EFFECTS: filters the history display based off selections
    private void filterHistoryDisplay() {
        filterRow(this.historyFilterText, this.historyInventorySorter);
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
    // EFFECTS: displays error message
    private void displayErrorMessage(Exception e) {
        this.commentLabel.setForeground(ERROR_TEXT_COLOR);
        this.commentLabel.setText(e.getMessage());
    }

    // EFFECTS: converts a Date to an equivalent LocalDate and returns it
    private LocalDate getLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zoneDataTime = instant.atZone(ZoneId.systemDefault());
        return zoneDataTime.toLocalDate();
    }

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
        if (model.getColumnCount() != COLUMN_WIDTHS.length) {
            return;
        }

        for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
            model.getColumn(i).setPreferredWidth(COLUMN_WIDTHS[i]);
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

    // EFFECTS: returns true if passwords equivalent, otherwise false
    private boolean checkPasswordEquivalence(char[] input) {
        if (input.length != ADMIN_PASSWORD.length) {
            return false;
        }

        for (int i = 0; i < input.length; i++) {
            if (input[i] != ADMIN_PASSWORD[i]) {
                return false;
            }
        }
        return true;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * public interface
     */

    public JSONObject getWarehouseJsonObjectRepresentation() {
        return this.warehouse.convertToJsonObject();
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

            setPreferredSize(new Dimension(COLUMN_WIDTHS[columnIndex], getRowHeight()));
            setMaximumSize(new Dimension(COLUMN_WIDTHS[columnIndex], 100));
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
