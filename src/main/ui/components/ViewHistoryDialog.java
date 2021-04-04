package ui.components;

import model.Package;
import model.Warehouse;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// This class handles the history portion of the warehouse application gui
public class ViewHistoryDialog implements ActionListener {
    private final WarehouseApplication warehouseApplication;
    private final Warehouse myWarehouse;
    private final ButtonGroup buttonGroup;
    private final JRadioButton importHistoryButton;
    private final JRadioButton exportHistoryButton;
    private final JButton loadHistory;
    private final JLabel operationPicture;
    private JDialog viewHistoryDialog;
    private JTextArea transactionHistory;

    // MODIFIES: this
    // EFFECTS: ViewHistoryEvent constructor
    public ViewHistoryDialog(WarehouseApplication app, Warehouse warehouse) {
        buttonGroup = new ButtonGroup();
        importHistoryButton = new JRadioButton("Import");
        exportHistoryButton = new JRadioButton("Export");
        loadHistory = new JButton("Load History");
        operationPicture = new JLabel();
        this.warehouseApplication = app;
        this.myWarehouse = warehouse;

        buttonGroup.add(importHistoryButton);
        buttonGroup.add(exportHistoryButton);
    }

    // MODIFIES: this
    // EFFECTS: creates view history dialog
    public void generateViewHistoryDialog() {
        this.viewHistoryDialog = new JDialog(this.warehouseApplication, "View Transaction History");
        viewHistoryDialog.setLayout(new GridLayout(1,2));
        organizeViewHistoryDialogContent();
        viewHistoryDialog.setSize(800, 700);
        viewHistoryDialog.setLocationRelativeTo(null);
        viewHistoryDialog.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: organizes the content on the view history dialog
    private void organizeViewHistoryDialogContent() {
        JPanel interactivePanel = new JPanel(new GridLayout(3,1));
        organizeInteractivePanelContent(interactivePanel);
        JScrollPane historyDisplay = setUpHistoryDisplay();

        viewHistoryDialog.add(interactivePanel);
        viewHistoryDialog.add(historyDisplay);
    }

    // MODIFIES: this, interactivePanel
    // EFFECTS: organizes the content on the interactive panel located on left side of view history dialog
    private void organizeInteractivePanelContent(JPanel interactivePanel) {
        addImportExportButtonPanel(interactivePanel);
        interactivePanel.add(operationPicture);
        addLoadHistoryButtonFunctionality(interactivePanel);
    }

    // MODIFIES: this, interactivePanel
    // EFFECTS: adds button panel to the interactive panel
    private void addImportExportButtonPanel(JPanel interactivePanel) {
        JPanel importExportButtonPanel = new JPanel();
        importExportButtonPanel.add(importHistoryButton);
        importExportButtonPanel.add(exportHistoryButton);
        importHistoryButton.setActionCommand("Import");
        exportHistoryButton.setActionCommand("Export");
        importHistoryButton.setSelected(true);
        interactivePanel.add(importExportButtonPanel);
    }

    // MODIFIES: this, interactivePanel
    // EFFECTS: implements load history button functionality
    private void addLoadHistoryButtonFunctionality(JPanel interactivePanel) {
        loadHistory.addActionListener(this);
        interactivePanel.add(loadHistory);
    }

    // MODIFIES: this
    // EFFECTS: sets up the import/export history display located on the right side of view history dialog
    private JScrollPane setUpHistoryDisplay() {
        transactionHistory = new JTextArea();
        transactionHistory.setEditable(false);
        transactionHistory.setLineWrap(true);
        transactionHistory.setWrapStyleWord(true);

        JScrollPane historyDisplay = new JScrollPane(transactionHistory);
        historyDisplay.createHorizontalScrollBar();
        historyDisplay.createVerticalScrollBar();
        historyDisplay.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        historyDisplay.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return historyDisplay;
    }

    // MODIFIES: this
    // EFFECTS: directs user to the correct operation given the option the user selected
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = buttonGroup.getSelection().getActionCommand();
        Toolkit.getDefaultToolkit().beep();

        if (actionCommand.equals("Import")) {
            updateOperationPicture("Import");
            printHistory("Import");
        } else {
            updateOperationPicture("Export");
            printHistory("Export");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the operation picture displayed to a picture that represents the given operation
    //          Picture borrowed from:
    //          https://www.canada-usblog.com/2016/01/20/directors-and-officers-liability-for-failure-to-obtain-an-
    //          import-permit/
    //          https://www.sagecity.com/support_communities/sage_erp_x3/b/sageerp_x3_product_support_blog/posts/
    //          trouble-with-the-same-records-exporting-over-and-over-chrono-management-to-the-rescue
    private void updateOperationPicture(String process) {
        ImageIcon processIcon = new ImageIcon("data/" + process + ".jpeg");
        // scale processIcon to fit operationPicture (JLabel)
        Image scaledImage = processIcon.getImage()
                .getScaledInstance(operationPicture.getWidth(),operationPicture.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledProcessIcon = new ImageIcon(scaledImage);
        // set scaled processIcon to operationPicture (JLabel)
        operationPicture.setIcon(scaledProcessIcon);
    }


    // MODIFIES: this
    // EFFECTS: prints the history of all packages that have been imported
    private void printHistory(String process) {
        List<Package> history;
        if (process.equals("Import")) {
            history = myWarehouse.getImportEvent().getImportHistory();
        } else {
            history = myWarehouse.getExportEvent().getExportHistory();
        }

        if (history.size() == 0) {
            transactionHistory.setText("There are no records of package " + process + "s");
        } else {
            transactionHistory.setText("");
            for (Package p : history) {
                transactionHistory.append(p.toString());
                transactionHistory.append("\n\n");
            }
        }
    }

}
