package ui.components.displaypanel;

import model.Warehouse;
import ui.WarehouseApplication;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class DisplayPanel extends JComponent implements ActionListener {
    // INVARIANT: the size of COLUMN_NAMES must match TABLE_COLUMN_WIDTHS
    protected static final String[] DISPLAY_COLUMN_NAMES = {"Name", "Invoice #", "Qty", "Product-Description",
            "Import Date", "Location", "Export Info", "Monthly Charge"};
    protected static final int[] DISPLAY_COLUMN_WIDTHS = {55, 60, 15, 180, 60, 60, 220, 280};
    protected Warehouse warehouse;
    protected WarehouseApplication warehouseApplication;

    public abstract void addActionListeners();

    public abstract void clearUserInputs();

    protected abstract JTextField getFilterInput();

    protected abstract TableRowSorter<MyTableModel> getTableRowSorter();

    protected abstract void setSorter(TableRowSorter<MyTableModel> sorter);

    protected abstract JTable getTable();

    public void renderDisplay() {
        // create TableModel with updated history
        MyTableModel model = new MyTableModel(this.warehouse.getOrders(false), DISPLAY_COLUMN_NAMES);

        // create a TableRowSorter for history
        setSorter(new TableRowSorter<>(model));

        // set/update history JTable with TableModel
        getTable().setModel(model);

        // set TableRowSorter to history JTable
        getTable().setRowSorter(getTableRowSorter());

        // adjust column/row characteristics in current history JTable
        setColumns(getTable());
        setRowProperties(getTable());
    }

    private void filterRow() {
        RowFilter<MyTableModel, Object> rowFilter;
        try {
            rowFilter = RowFilter.regexFilter(getFilterInput().getText());
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        getTableRowSorter().setRowFilter(rowFilter);
    }


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

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand == "Filter") {
            filterRow();
        }

        if (actionCommand == "Clear") {
            clearUserInputs();
        }

        this.warehouseApplication.update();
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setWarehouseApplication(WarehouseApplication warehouseApplication) {
        this.warehouseApplication = warehouseApplication;
    }

    /**
     *  MyTableModel class
     */
    protected static class MyTableModel extends DefaultTableModel {

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
    protected static class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
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
