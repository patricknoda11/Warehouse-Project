package model;

import model.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Represents an Order a Customer can make. Each Order has content, import date, export date, invoice number, quantity
 *
 *          *** Note: An Order can have partial removals ***
 */
public class Order {
    private final String content;
    private LocalDate importDate;
    private List<ExportLabel> exports = new ArrayList<>();
    private List<MonthlyChargeLabel> monthlyChargeLabels = new ArrayList<>();
    private String invoiceNumber;
    private int originalQuantity;
    private int currentQuantity;
    private String storageLocation;

    public Order(String content, LocalDate importDate, String invoiceNumber, int quantity,
                 String storageLocation) throws QuantityNegativeException, QuantityZeroException,
            InvalidImportDateException {
        // if quantity is not valid throw error
        validateQuantity(quantity);

        // throw InvalidExportException(FutureExportDateException), if export date is a date that has not yet occurred
        if (DAYS.between(LocalDate.now(), importDate) > 0) {
            throw new InvalidImportDateException();
        }

        this.content = content;
        this.importDate = importDate;
        this.invoiceNumber = invoiceNumber;
        this.originalQuantity = quantity;
        this.currentQuantity = quantity;
        this.storageLocation = storageLocation;
    }

    // MODIFIES: this
    // EFFECTS: if quantity is not valid throws InvalidQuantityException,
    //          if removal quantity is greater than original quantity throw QuantityExceedsMaxQuantityException,
    //          if removal quantity greater than available quantity throw RemovalQuantityExceedsAvailabilityException,
    //          if export date before this import date or is a future date, throw InvalidExportDateException,
    //          else decrement removal quantity from current quantity and add removal details into export history
    public void remove(int removalQuantity, String exportInvoiceNum, LocalDate exportDate)
            throws QuantityNegativeException, QuantityZeroException, QuantityExceedsMaxQuantityException,
            RemovalQuantityExceedsAvailabilityException, InvalidExportDateException {
        // confirms if quantity is valid, if not throws exception
        validateQuantity(removalQuantity);

        // if quantity is greater than original quantity throw QuantityExceedsMaxQuantityException
        if (removalQuantity > this.originalQuantity) {
            throw new QuantityExceedsMaxQuantityException(removalQuantity, this.originalQuantity);
        }

        // if removing more than available, throw RemovalQuantityExceedsAvailabilityException
        if (this.currentQuantity < removalQuantity) {
            throw new RemovalQuantityExceedsAvailabilityException();
        }

        // if export date is before import date or has not occurred (In the future), throw InvalidExportDateException
        if (DAYS.between(this.importDate, exportDate) < 0 || DAYS.between(LocalDate.now(), exportDate) > 0) {
            throw new InvalidExportDateException();
        }

        // decrement current quantity appropriately
        this.currentQuantity -= removalQuantity;

        // record export details into exports list
        this.exports.add(new ExportLabel(removalQuantity,exportInvoiceNum,exportDate));
    }

    // MODIFIES: this
    // EFFECTS: if quantity is not valid throws InvalidQuantityException,
    //          if quantity is greater than original quantity throw QuantityExceedsMaxQuantityException,
    //          if start date is prior to this Orders import date throw InvalidStartDateException,
    //          if end date has not yet occurred throw InvalidEndDateException,
    //          otherwise create a new MonthlyChargeLabel with the given details and record/save it
    public void addMonthlyChargeLabel(int quantity, String invoiceNum, LocalDate startDate, LocalDate endDate)
            throws QuantityNegativeException, QuantityZeroException, QuantityExceedsMaxQuantityException,
            InvalidStartDateException, InvalidEndDateException, InvalidMonthRangeException {
        // confirms if quantity is valid, if not throws exception
        validateQuantity(quantity);

        // if quantity is greater than original quantity throw QuantityExceedsMaxQuantityException
        if (quantity > this.originalQuantity) {
            throw new QuantityExceedsMaxQuantityException(quantity, this.originalQuantity);
        }

        // if start date is prior to this Orders import date throw InvalidStartDateException
        if (DAYS.between(this.importDate, startDate) < 0) {
            throw new InvalidStartDateException();
        }

        // if end date has not yet occurred throw InvalidEndDateException
        if (DAYS.between(LocalDate.now(), endDate) > 0) {
            throw new InvalidEndDateException();
        }

        // create a new MonthlyChargeLabel with the given details and record/save it
        this.monthlyChargeLabels.add(new MonthlyChargeLabel(quantity, invoiceNum, startDate, endDate));
    }

    // EFFECTS: if quantity is negative throw NegativeQuantityException,
    //          if quantity is zero throw QuantityZeroException,
    //          if quantity is greater than original quantity throw QuantityExceedsMaxQuantityException
    private void validateQuantity(int quantity) throws QuantityNegativeException, QuantityZeroException {
        // if quantity is negative throw NegativeQuantityException
        if (quantity < 0) {
            throw new QuantityNegativeException();
        }

        // if quantity is zero throw QuantityZeroException
        if (quantity == 0) {
            throw new QuantityZeroException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(invoiceNumber, order.invoiceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNumber);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getters


    // EFFECTS: returns a string representation of all the exports for this
    public String getExportsString() {
        String retVal = "";
        for (ExportLabel el : this.exports) {
            retVal += el.toString();
        }
        return retVal;
    }

    // EFFECTS: returns a string representation of all the monthly charges for this
    public String getMonthlyChargeLabelsString() {
        String retVal = "";
        for (MonthlyChargeLabel mcl : this.monthlyChargeLabels) {
            retVal += mcl.toString();
        }
        return retVal;
    }

    public String getContent() {
        return this.content;
    }

    public LocalDate getImportDate() {
        return this.importDate;
    }

    public List<ExportLabel> getExports() {
        return Collections.unmodifiableList(this.exports);
    }

    public List<MonthlyChargeLabel> getMonthlyChargeLabels() {
        return Collections.unmodifiableList(this.monthlyChargeLabels);
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public int getOriginalQuantity() {
        return this.originalQuantity;
    }

    public int getCurrentQuantity() {
        return this.currentQuantity;
    }

    public String getStorageLocation() {
        return this.storageLocation;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // editors (NOTE: SHOULD ONLY BE CALLED TO FIX ERRORS)
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public void setCurrentQuantity(int quantity) {
        this.currentQuantity = quantity;
    }

    public void setOriginalQuantity(int currentQuantity) {
        this.originalQuantity = currentQuantity;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }
}
