package model;

import model.exceptions.InvalidMonthRangeException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Represents an monthly charge transaction for an Order as a Label. Each MonthlyChargeLabel shows the quantity,
 * invoice number, and date range for the monthly charge.
 */
public class MonthlyChargeLabel extends Label {
    private static final int SMALLEST_DAYS_IN_MONTH = 28;
    private static final int LONGEST_DAYS_IN_MONTH = 31;
    private LocalDate startDate;
    private LocalDate endDate;

    // EFFECTS: if the date range does not correctly represent a month throws InvalidMonthRangeException,
    //          else sets the fields
    public MonthlyChargeLabel(int quantity, String monthlyInvoiceNum, LocalDate startDate, LocalDate endDate)
            throws InvalidMonthRangeException {
        super(quantity, monthlyInvoiceNum);

        long monthlyChargeRange = DAYS.between(startDate, endDate);
        if (monthlyChargeRange < SMALLEST_DAYS_IN_MONTH || LONGEST_DAYS_IN_MONTH < monthlyChargeRange) {
            throw new InvalidMonthRangeException();
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public JSONObject convertToJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantity", super.quantity);
        jsonObject.put("invoiceNumber", super.invoiceNumber);
        jsonObject.put("startDate", this.startDate);
        jsonObject.put("endDate", this.endDate);
        return jsonObject;
    }

    @Override
    public String toString() {
        return "QTY=" + super.quantity + "; INV=" + super.invoiceNumber + "; "
                + this.startDate.format(super.format) + "-" + this.endDate.format(super.format) + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MonthlyChargeLabel that = (MonthlyChargeLabel) o;
        return Objects.equals(this.startDate, that.startDate) && Objects.equals(this.endDate, that.endDate)
                && super.quantity == that.quantity && Objects.equals(super.invoiceNumber, that.invoiceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.startDate, this.endDate, super.quantity, super.invoiceNumber);
    }

    // getters
    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }
}
