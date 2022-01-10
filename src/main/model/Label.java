package model;

import org.json.JSONObject;

import java.time.format.DateTimeFormatter;

/**
 * Abstract Label class
 */
public abstract class Label {
    protected int quantity;
    protected String invoiceNumber;
    protected DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy");

    public Label(int quantity, String invoiceNumber) {
        this.quantity = quantity;
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    // EFFECTS: converts label into a JSON Object
    public abstract JSONObject convertToJsonObject();

    // getters
    public int getQuantity() {
        return this.quantity;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

}
