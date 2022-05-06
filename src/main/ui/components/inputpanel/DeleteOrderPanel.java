package ui.components.inputpanel;

import model.Warehouse;
import ui.WarehouseApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteOrderPanel extends InputPanel {
    private JPanel deleteOrderPanel;
    private JTextField customerNameInput;
    private JTextField invoiceNumberInput;
    private JPasswordField adminPasswordInput;
    private JButton cancelButton;
    private JButton enterButton;

    public DeleteOrderPanel(Warehouse warehouse, WarehouseApplication warehouseApplication) {
        super(warehouse, warehouseApplication);
    }


    @Override
    public void clearUserInputs() {
        this.customerNameInput.setText("");
        this.invoiceNumberInput.setText("");
        this.adminPasswordInput.setText("");
    }

    @Override
    public void addActionListeners() {
        this.enterButton.addActionListener(this);
        this.cancelButton.addActionListener(this);
    }

    @Override
    public void submitInput() {
        // TODO
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.enterButton) {
            submitInput();
        }

        if (e.getSource() == this.cancelButton) {
            clearUserInputs();
        }
    }
}
