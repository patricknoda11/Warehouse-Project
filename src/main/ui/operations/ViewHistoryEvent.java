package ui.operations;

import model.Package;
import model.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

// This class handles the history portion of the warehouse application gui
public class ViewHistoryEvent implements ActionListener {
    private Warehouse myWarehouse;
    private JDialog viewHistoryDialog;
    private ButtonGroup buttonGroup;
    private JRadioButton importHistoryButton;
    private JRadioButton exportHistoryButton;
    private JButton loadHistory;
    private JLabel operationPicture;
    private JTextArea transactionHistory;

    public ViewHistoryEvent(Warehouse warehouse, JDialog viewHistoryDialog) {
        buttonGroup = new ButtonGroup();
        importHistoryButton = new JRadioButton("Import");
        exportHistoryButton = new JRadioButton("Export");
        loadHistory = new JButton("Load History");
        operationPicture = new JLabel();
        this.myWarehouse = warehouse;
        this.viewHistoryDialog = viewHistoryDialog;

        buttonGroup.add(importHistoryButton);
        buttonGroup.add(exportHistoryButton);
    }

    public void organizeViewHistoryDialogContent() {
        JPanel interactivePanel = new JPanel(new GridLayout(3,1));
        organizeInteractivePanelContent(interactivePanel);
        JScrollPane historyDisplay = setUpHistoryDisplay();

        viewHistoryDialog.add(interactivePanel);
        viewHistoryDialog.add(historyDisplay);
    }

    private void organizeInteractivePanelContent(JPanel interactivePanel) {
        addImportExportButtonPanel(interactivePanel);
        interactivePanel.add(operationPicture);
        addLoadHistoryButtonFunctionality(interactivePanel);
    }

    private void addImportExportButtonPanel(JPanel interactivePanel) {
        JPanel importExportButtonPanel = new JPanel();
        importExportButtonPanel.add(importHistoryButton);
        importExportButtonPanel.add(exportHistoryButton);
        importHistoryButton.setActionCommand("Import");
        exportHistoryButton.setActionCommand("Export");
        importHistoryButton.setSelected(true);
        interactivePanel.add(importExportButtonPanel);
    }

    private void addLoadHistoryButtonFunctionality(JPanel interactivePanel) {
        loadHistory.addActionListener(this);
        interactivePanel.add(loadHistory);
    }

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

    private void updateOperationPicture(String process) {
        ImageIcon processIcon = new ImageIcon("data/" + process + ".jpeg");
        // scale processIcon to fit operationPicture (JLabel)
        Image scaledImage = processIcon.getImage()
                .getScaledInstance(operationPicture.getWidth(),operationPicture.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledProcessIcon = new ImageIcon(scaledImage);
        // set scaled processIcon to operationPicture (Jlabel)
        operationPicture.setIcon(scaledProcessIcon);
    }


    // EFFECTS: prints the history of all packages that have been imported
    private void printHistory(String process) {
        List<Package> history;
        if (process.equals("Import")) {
            history = myWarehouse.getImportHistory();
        } else {
            history = myWarehouse.getExportHistory();
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
