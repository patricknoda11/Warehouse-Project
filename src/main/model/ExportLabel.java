package model;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents an export transaction for an Order as a Label. Each Order can have multiple ExportLabels that show the
 * quantity, invoice number, and date at which the export event took place.
 */
public class ExportLabel extends Label {
    private LocalDate exportDate;

    public ExportLabel(int quantity, String exportInvoiceNum, LocalDate exportDate) {
        super(quantity, exportInvoiceNum);
        this.exportDate = exportDate;
    }

    @Override
    public JSONObject convertToJsonObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("quantity", super.quantity);
        jsonObject.put("invoiceNumber", super.invoiceNumber);
        jsonObject.put("exportDate", this.exportDate);

        return jsonObject;
    }

    @Override
    public String toString() {
        return "QTY=" + super.quantity + "; INV="
                + super.invoiceNumber + "; " + this.exportDate.format(super.format) + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExportLabel that = (ExportLabel) o;
        return Objects.equals(this.exportDate, that.exportDate) && super.quantity == that.quantity
                && Objects.equals(super.invoiceNumber, that.invoiceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.exportDate, super.quantity, super.invoiceNumber);
    }

    // getters
    public LocalDate getExportDate() {
        return this.exportDate;
    }
}
