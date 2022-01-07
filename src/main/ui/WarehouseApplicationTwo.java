package ui;

import model.Warehouse;
import model.exceptions.*;
import org.jdesktop.swingx.JXDatePicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Warehouse Application User Interface
 * functionalities/views:
 *      - adding customer
 *      - importing products
 *      - exporting products
 *      - current inventory view
 *      - complete transactions view
 */
public class WarehouseApplicationTwo extends JFrame implements ActionListener {
    private static final Color GUI_BACKGROUND_COLOR = new Color(250, 230, 190);
    private static final Color SUCCESS_TEXT_COLOR = new Color(58, 163, 64);
    private static final Color ERROR_TEXT_COLOR = new Color(187, 14, 8);
    private static final int GUI_HEIGHT = 1000;
    private static final int GUI_WIDTH = 1430;
    // INVARIANT: the size of COLUMN_NAMES must match TABLE_COLUMN_WIDTHS
    private static final String[] COLUMN_NAMES = {"Name", "Invoice #", "Qty", "Product-Description",
            "Import Date", "Location", "Export Info", "Monthly Charge"};
    private static final int[] COLUMN_WIDTHS = {55, 60, 15, 180, 60, 60, 220, 280};
    private static final int MAX_TABLE_CELL_HEIGHT = 200;
    private static final char[] ADMIN_PASSWORD = {'a', 'l', 'a', 'n', 'a'};

    private JPanel mainPanel;
    private JTabbedPane importExportPanel;
    private JTabbedPane orderDisplayView;
    private JTable historyTable;
    private JButton importEnter;
    private JButton importCancel;
    private JTextField importInvoiceNumberUserInput;
    private JTextField importProductUserInput;
    private JSpinner importQuantityUserInput;
    private JXDatePicker importDateUserInput;
    private JLabel importDate;
    private JLabel importQuantity;
    private JLabel importProduct;
    private JLabel importCustomer;
    private JPanel exportPanel;
    private JPanel importPanel;
    private JLabel importInvoiceNumber;
    private JTextField importCustomerUserInput;
    private JTextArea importCommentsUserInput;
    private JLabel importStorageLocation;
    private JLabel exportCustomer;
    private JLabel exportImportInvoiceNumber;
    private JLabel exportQuantity;
    private JLabel exportDate;
    private JSpinner exportQuantityUserInput;
    private JTextField exportImportInvoiceNumberUserInput;
    private JButton exportEnter;
    private JButton exportCancel;
    private JXDatePicker exportDateUserInput;
    private JPanel currentInventoryPanel;
    private JPanel historyPanel;
    private JScrollPane historyScrollPane;
    private JTextField importStorageLocationUserInput;
    private JTextField exportCustomerUserInput;
    private JPanel addCustomerPanel;
    private JTextField addCustomerNameUserInput;
    private JButton addCustomerCancel;
    private JLabel addCustomerName;
    private JButton addCustomerEnter;
    private JTable currentInventoryTable;
    private JScrollPane currentInventoryScrollPane;
    private JLabel commentLabel;
    private JTextField currentInventoryFilterText;
    private JTextField historyFilterText;
    private JComboBox historyFilterSelector;
    private JComboBox currentInventoryFilterSelector;
    private JPanel currentInventoryFilterPanel;
    private JPanel historyFilterPanel;
    private JButton historyFilterButton;
    private JButton currentInventoryFilterButton;
    private JButton historyFilterClearButton;
    private JButton currentInventoryFilterClearButton;
    private JPanel deleteOrderPanel;
    private JPanel removeCustomerPanel;
    private JTextField deleteOrderCustomerUserInput;
    private JSpinner fixOrderQuantityUserInput;
    private JTextField fixOrderStorageLocationUserInput;
    private JButton deleteOrderCancel;
    private JButton deleteOrderEnter;
    private JLabel deleteOrderCustomer;
    private JLabel deleteOrderInvoiceNumber;
    private JTextField deleteOrderInvoiceNumberUserInput;
    private JTextField fixOrderDescriptionUserInput;
    private JXDatePicker fixOrderImportDateUserInput;
    private JButton removeCustomerCancel;
    private JButton removeCustomerEnter;
    private JPasswordField removeCustomerAdminPasswordUserInput;
    private JPasswordField deleteOrderAdminPasswordUserInput;
    private JLabel removeCustomerName;
    private JTextField removeCustomerNameUserInput;
    private JLabel removeCustomerAdminPassword;
    private JLabel deleteOrderAdminPassword;
    private JLabel exportExportInvoiceNumber;
    private JTextField exportExportInvoiceNumberUserInput;
    private JPanel addMonthlyChargePanel;
    private JLabel addMonthlyChargeStartDate;
    private JLabel addMonthlyChargeEndDate;
    private JButton addMonthlyChargeCancel;
    private JButton addMonthlyChargeEnter;
    private JSpinner addMonthlyChargeQuantityUserInput;
    private JTextField addMonthlyChargeMonthlyInvoiceNumUserInput;
    private JXDatePicker addMonthlyChargeStartDateUserInput;
    private JXDatePicker addMonthlyChargeEndDateUserInput;
    private JLabel addMonthlyChargeQuantity;
    private JLabel addMonthlyChargeInvoiceNum;
    private JTextField addMonthlyChargeCustomerUserInput;
    private JTextField addMonthlyChargeImportInvoiceNumUserInput;
    private JLabel addMonthlyChargeImportInvoiceNum;
    private TableRowSorter currentInventorySorter;
    private TableRowSorter historyInventorySorter;
    private final Warehouse myWarehouse = new Warehouse("My Warehouse");

    // Constructor
    public WarehouseApplicationTwo(String frameTitle) {
        // set Frame title
        super(frameTitle);

        // frame setup
        initializeFrameSettings();

        // setup frame icon if possible
        setUpFrameIcon();

        // add action listeners to import and export buttons (utilizes event propagation -- bubbling)
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

        // initialize current/history display
        renderCurrentInventory();
        renderHistory();

        // set as visible
        setVisible(true);
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
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File("./data/warehouse.png"));
            setIconImage(icon);
        } catch (IOException e) {
            // do nothing
        }
    }

    @Override
    // EFFECTS: directs to correct operation given user button clicks
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
            this.myWarehouse.importProduct(name, content, importDate, invoiceNumber, quantity, location);
            renderCurrentInventory();
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText("Successfully imported " + quantity + " pallets of "
                    + content + " ---- Invoice Number " + invoiceNumber);
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
            this.myWarehouse.exportOrder(customerName, importInvoiceNumber,
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
    // EFFECTS: a helper that clears user inputs on export screen
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
            this.myWarehouse.addCustomer(name);
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
     * Out/Export Panel
     */

    private void addMonthlyCharge() {
        String customerName = this.addMonthlyChargeCustomerUserInput.getText().trim().toLowerCase();
        String importInvoiceNumber = this.addMonthlyChargeImportInvoiceNumUserInput.getText().trim().toLowerCase();
        int quantity = (Integer) this.addMonthlyChargeQuantityUserInput.getValue();
        LocalDate startDate = getLocalDate(this.addMonthlyChargeStartDateUserInput.getDate());
        LocalDate endDate = getLocalDate(this.addMonthlyChargeEndDateUserInput.getDate());
        String monthlyInvoiceNumber = this.addMonthlyChargeMonthlyInvoiceNumUserInput.getText().trim().toLowerCase();

        try {
            this.myWarehouse.recordMonthlyCharge(customerName, importInvoiceNumber,
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
     * Delete Order Panel
     */

    // EFFECTS: updates/edits an existing order's properties
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
            this.myWarehouse.deleteCustomerOrder(name, invoiceNumber);
            renderCurrentInventory();           // updates current inventory display
            this.commentLabel.setForeground(SUCCESS_TEXT_COLOR);
            this.commentLabel.setText("Successfully deleted " + name + "'s order ---- Invoice Number " + invoiceNumber);
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
    // EFFECTS: clears user inputs on Fix Order screen
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
            this.myWarehouse.deleteCustomer(name);
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
        MyTableModel model = new MyTableModel(this.myWarehouse.getOrders(true), COLUMN_NAMES);

        // create a TableRowSorter for inventory
        this.currentInventorySorter = new TableRowSorter(model);

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
        MyTableModel model = new MyTableModel(this.myWarehouse.getOrders(false), COLUMN_NAMES);

        // create a TableRowSorter for history
        this.historyInventorySorter = new TableRowSorter(model);

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
    private void filterRow(JTextField tf, TableRowSorter rs) {
        RowFilter<MyTableModel, Object> rowFilter = null;

        try {
            rowFilter = RowFilter.regexFilter(tf.getText());
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }

        rs.setRowFilter(rowFilter);
    }

    // MODIFIES: this
    // EFFECTS: if number of column matches the number of elements in COLUMN_WIDTHs sets column widths on JTable,
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
        if (model.getColumnCount() != COLUMN_WIDTHS.length) return;

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
            double greaterNumLines =
                    heightThree > heightSix ? heightThree > heightSeven ? heightThree : heightSeven : heightSix;
            jt.setRowHeight(i, (int) Math.ceil((fontHeightPixels * greaterNumLines)));
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
     *  MyTableModel class which slightly alters DefaultTableModel
     */
    private class MyTableModel extends DefaultTableModel {

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
    private class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
        private int columnIndex;

        public MultiLineTableCellRenderer(int columnIndex) {
            // save argument in class field
            this.columnIndex = columnIndex;

            // initialize cell properties to allow multiple lines
            setGeneralTextAreaProperties();
        }

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
            // guard clause --> do not need to call this method if component is selected or has focus
//            System.out.println("I too");
//
//            System.out.println("I ran " + column + " " + row);
            // adjust cells in specified column to have associated width

            setText(value == null ? "" : value.toString());

            return this;
        }
    }

}
