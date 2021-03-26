package ui.operations;

import model.Package;
import model.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// This class handles the history portion of the warehouse application gui
public class History implements ActionListener {
    private Warehouse myWarehouse;
    private JDialog viewHistoryDialog;
    private ButtonGroup buttonGroup;
    private JRadioButton importHistoryButton;
    private JRadioButton exportHistoryButton;
    private JButton loadHistory;
    private JTextArea transactionHistory;

    public History(Warehouse warehouse, JDialog viewHistoryDialog) {
        buttonGroup = new ButtonGroup();
        importHistoryButton = new JRadioButton("Import");
        exportHistoryButton = new JRadioButton("Export");
        loadHistory = new JButton("Load History");
        this.myWarehouse = warehouse;
        this.viewHistoryDialog = viewHistoryDialog;

        buttonGroup.add(importHistoryButton);
        buttonGroup.add(exportHistoryButton);
    }

    public void implementFunctionality() {
        JPanel importExportButtonPanel = new JPanel();
        importExportButtonPanel.add(importHistoryButton);
        importExportButtonPanel.add(exportHistoryButton);
        importHistoryButton.setActionCommand("Import");
        exportHistoryButton.setActionCommand("Export");
        importHistoryButton.setSelected(true);
        JScrollPane historyDisplay = setUpHistoryDisplay();
        loadHistory.addActionListener(this);

        viewHistoryDialog.add(importExportButtonPanel, BorderLayout.NORTH);
        viewHistoryDialog.add(historyDisplay, BorderLayout.CENTER);
        viewHistoryDialog.add(loadHistory, BorderLayout.SOUTH);
    }

    private JScrollPane setUpHistoryDisplay() {
        transactionHistory = new JTextArea();
        transactionHistory.setEditable(false);
        transactionHistory.setLineWrap(true);
        transactionHistory.setWrapStyleWord(true);

        JScrollPane currentInventoryDisplay = new JScrollPane(transactionHistory);
        currentInventoryDisplay.createHorizontalScrollBar();
        currentInventoryDisplay.createVerticalScrollBar();
        currentInventoryDisplay.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        currentInventoryDisplay.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return currentInventoryDisplay;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = buttonGroup.getSelection().getActionCommand();

        if (actionCommand.equals("Import")) {
            printHistory("Import");
        } else {
            printHistory("Export");
        }
    }


    // EFFECTS: prints the history of all packages that have been imported
    private void printHistory(String action) {
        List<Package> history;
        if (action.equals("Import")) {
            history = myWarehouse.getImportHistory();
        } else {
            history = myWarehouse.getExportHistory();
        }

        if (history.size() == 0) {
            transactionHistory.setText("There are no records of package " + action + "s");
        } else {
            transactionHistory.setText("");
            for (Package p : history) {
                transactionHistory.append(p.toString());
                transactionHistory.append("\n\n");
            }
        }
    }

}
