package model;

import java.time.format.DateTimeFormatter;

/**
 * Abstract Label class
 */
public abstract class Label {
    protected int quantity;
    protected String invoiceNumber;
    protected DateTimeFormatter format;

    public Label(int quantity, String invoiceNumber) {
        this.quantity = quantity;
        this.invoiceNumber = invoiceNumber;
        this.format = DateTimeFormatter.ofPattern("MM/dd/yy");
    }

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();


    // getters
    public int getQuantity() {
        return this.quantity;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }
}
