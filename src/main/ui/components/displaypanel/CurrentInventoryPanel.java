package ui.components.displaypanel;

import model.Warehouse;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class CurrentInventoryPanel extends DisplayPanel {
    private JTextField filterInput;
    private JButton clearButton;
    private JButton filterButton;
    private JTable currentInventoryTable;
    private JPanel currentInventoryPanel;
    private TableRowSorter<MyTableModel> currentInventorySorter;

    public CurrentInventoryPanel() {
        // NOTE: cannot call addActionListener in supertype DisplayPanel as $$$SetupUI$$$ gets called after call to
        // super type constructor
        addActionListeners();
    }

    @Override
    public void addActionListeners() {
        this.clearButton.addActionListener(this);
        this.filterButton.addActionListener(this);
    }

    @Override
    public void clearUserInputs() {
        this.filterInput.setText("");
    }

    @Override
    public JTextField getFilterInput() {
        return this.filterInput;
    }

    @Override
    protected MyTableModel createTableModel() {
        Warehouse warehouse = this.warehouseApplication.getWarehouse();
        return new MyTableModel(warehouse.getOrders(true), DISPLAY_COLUMN_NAMES);
    }

    @Override
    public TableRowSorter<MyTableModel> getTableRowSorter() {
        return this.currentInventorySorter;
    }

    @Override
    protected void setSorter(TableRowSorter<MyTableModel> sorter) {
        this.currentInventorySorter = sorter;
    }

    @Override
    protected JTable getTable() {
        return this.currentInventoryTable;
    }
}
