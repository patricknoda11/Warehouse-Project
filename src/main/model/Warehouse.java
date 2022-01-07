package model;

import model.exceptions.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

/**
 * A representation of a Warehouse with a set of customers
 */
public class Warehouse {
    private final Set<Customer> customerSet = new HashSet<>();
    private String name;

    public Warehouse(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: if a customer with given name already exists throw CustomerAlreadyExistsException,
    //          otherwise create a new customer and add to customerSet
    public void addCustomer(String customerName) throws CustomerAlreadyExistsException {

        // if a customer with given name already exists throw CustomerAlreadyExistsException
        if (findCustomer(customerName) != null) {
            throw new CustomerAlreadyExistsException(customerName);
        }

        this.customerSet.add(new Customer(customerName));
    }

    // EFFECTS: if the specified customer does not exist then throw CustomerDoesNotExistException,
    //          otherwise find specified customer and import order for it
    public void importProduct(String customerName, String content, LocalDate importDate, String invoiceNum,
                              int quantity, String storageLocation) throws CustomerDoesNotExistException,
            OrderAlreadyExistsException, QuantityNegativeException, QuantityZeroException, InvalidImportDateException {

        Customer existingCustomer = findCustomer(customerName);

        if (existingCustomer == null) {
            throw new CustomerDoesNotExistException(customerName);
        }

        // throws OrderAlreadyExistsException if invoice number used in previous order
        checkInvoiceNumberValid(invoiceNum);

        existingCustomer.importOrder(content, importDate, invoiceNum, quantity, storageLocation);
    }

    // EFFECTS: helper function that throws OrderAlreadyExistsException if given invoice number has been used previously
    private void checkInvoiceNumberValid(String invoiceNum) throws OrderAlreadyExistsException {
        // check to make sure no duplicate invoice number in current inventory as well as any past orders
        for (Customer c : this.customerSet) {
            // check if each customer's active orders have given invoice number
            for (String key : c.getActiveOrders().keySet()) {
                if (key.equals(invoiceNum)) {
                    throw new OrderAlreadyExistsException(invoiceNum);
                }
            }
            // check if each customer's previous orders have given invoice number
            for (Order o : c.getCompleteOrders()) {
                if (o.getInvoiceNumber().equals(invoiceNum)) {
                    throw new OrderAlreadyExistsException(invoiceNum);
                }
            }
        }
    }

    // EFFECTS: if the specified customer does not exist then throw CustomerDoesNotExistException,
    //          otherwise finds specified customer and remove order from it
    public void exportOrder(String customerName, String importInvoiceNum, int quantity, LocalDate exportDate,
                            String exportInvoiceNum) throws CustomerDoesNotExistException,
            OrderDoesNotExistException, QuantityNegativeException, QuantityZeroException,
            QuantityExceedsMaxQuantityException, RemovalQuantityExceedsAvailabilityException,
            InvalidExportDateException, ParseException {
        Customer existingCustomer = findCustomer(customerName);

        if (existingCustomer == null) {
            throw new CustomerDoesNotExistException(customerName);
        }

        existingCustomer.removeFromOrder(importInvoiceNum, quantity, exportDate, exportInvoiceNum);
    }

    // EFFECTS: if the specified customer does not exist throw CustomerDoesNotExistException,
    //          otherwise finds customer and records monthly charge
    public void recordMonthlyCharge(String customerName, String importInvoiceNum, LocalDate initialDate,
                                    LocalDate endDate, int quantity, String monthlyInvoiceNum)
            throws CustomerDoesNotExistException, OrderDoesNotExistException,
            QuantityZeroException, QuantityNegativeException, QuantityExceedsMaxQuantityException,
            InvalidStartDateException, InvalidEndDateException, InvalidMonthRangeException {
        Customer existingCustomer = findCustomer(customerName);

        if (existingCustomer == null) {
            throw new CustomerDoesNotExistException(customerName);
        }

        existingCustomer.recordMonthlyCharge(importInvoiceNum, initialDate, endDate, quantity, monthlyInvoiceNum);
    }

    // EFFECTS: finds and returns reference to specified customer from customerSet, if not found return NULL
    private Customer findCustomer(String customerName) {
        Customer referenceCustomer = new Customer(customerName);
        Customer existingCustomer = null;
        for (Customer c : this.customerSet) {
            if (c.equals(referenceCustomer)) {
                existingCustomer = c;
            }
        }
        return existingCustomer;
    }

    // MODIFIES: this
    // EFFECTS: updates name of this warehouse
    public void updateWarehouseName(String name) {
        this.name = name;
    }

    // EFFECTS: returns warehouse represented as a JSON object
    public JSONObject convertToJsonObject() {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("warehouseName", this.warehouseName);
//        jsonObject.put("largeSizedPackages", addListToJsonObject(this.largeSizedPackages));
//        jsonObject.put("mediumSizedPackages", addListToJsonObject(this.mediumSizedPackages));
//        jsonObject.put("smallSizedPackages", addListToJsonObject(this.smallSizedPackages));
//        jsonObject.put("importHistory", addListToJsonObject(this.anImport.getImportHistory()));
//        jsonObject.put("exportHistory", addListToJsonObject(this.export.getExportHistory()));
//        jsonObject.put("numberPackagesInInventory", this.allPackagesAvailableInInventory.size());
        return jsonObject;
    }

    // EFFECTS: adds list representation of inventory, import history, export history to JSON object
    private JSONArray addListToJsonObject(List<Package> packageList) {
        JSONArray jsonArray = new JSONArray();

        for (Package p : packageList) {
            jsonArray.put(p.convertToJsonObject());
        }
        return jsonArray;
    }

    // EFFECTS: if a customer of specified name does not exist throw new CustomerDoesNotExistException,
    //          else removes customer from customerSet
    public void deleteCustomer(String name) throws CustomerDoesNotExistException {
        Customer customer = findCustomer(name);
        if (customer == null) {
            throw new CustomerDoesNotExistException(name);
        }
        this.customerSet.remove(customer);
    }

    // EFFECTS if the specified customer does not exist throw CustomerDoesNotExistException,
    //         otherwise finds customer and deletes indicated order from it
    public void deleteCustomerOrder(String customerName, String invoiceNum) throws CustomerDoesNotExistException,
            QuantityNegativeException, QuantityZeroException, InvalidImportDateException, OrderDoesNotExistException {
        Customer existingCustomer = findCustomer(customerName);

        if (existingCustomer == null) {
            throw new CustomerDoesNotExistException(customerName);
        }

        existingCustomer.deleteOrder(invoiceNum);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // getters
    public String getWarehouseName() {
        return this.name;
    }

    public Set<Customer> getCustomerSet() {
        return Collections.unmodifiableSet(this.customerSet);
    }

    // EFFECTS: if isActiveOrder is true return active order as String[][], else return complete order as String[][]
    public String[][] getOrders(boolean isActiveOrder) {
        int totalOrders = 0;
        for (Customer c : this.customerSet) {
            if (isActiveOrder) {
                totalOrders += c.getActiveOrderSize();
            } else {
                totalOrders += c.getCompleteOrderSize();
            }
        }
        String[][] returnArray = new String[totalOrders][];
        int index = 0;
        for (Customer c : this.customerSet) {
            c.chooseIterator(isActiveOrder);
            for (String[] o : c) {
                returnArray[index] = o;
                index++;
            }
        }
        return returnArray;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
}

