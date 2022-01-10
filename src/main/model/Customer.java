package model;

import model.exceptions.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Represents a Customer with current orders, as well as a list of all previous orders made
 */
public class Customer implements Iterable<String[]> {
    private String name;
    private HashMap<String, Order> activeOrders = new HashMap();   // key = Invoice Number :: value = Order
    private List<Order> completeOrders = new ArrayList<>();

    private boolean iterateActiveOrders = true;                    // set iterator to iterate activeOrders by default

    public Customer(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds a new order with given invoice number into activeOrders
    public void importOrder(String content, LocalDate importDate, String invoiceNum, int quantity, String location)
            throws QuantityNegativeException, QuantityZeroException, InvalidImportDateException {
        this.activeOrders.put(invoiceNum,
                new Order(content, importDate, invoiceNum, quantity, location));
    }

    // MODIFIES: this
    // EFFECTS: if order with given invoiceNumber does not exist throw OrderDoesNotExistException,
    //          otherwise remove indicated quantity from specified order and check if any items are left for given order
    //          if nothing is remaining register it as complete
    public void removeFromOrder(String importInvoiceNumber, int removalQuantity, LocalDate exportDate,
                                String exportInvoiceNumber) throws OrderDoesNotExistException,
            QuantityNegativeException, QuantityZeroException, QuantityExceedsMaxQuantityException,
            RemovalQuantityExceedsAvailabilityException, InvalidExportDateException {
        Order currentOrder = this.activeOrders.get(importInvoiceNumber);

        // throw OrderDoesNotExistException, if order with given invoiceNumber does not already exist
        if (currentOrder == null) {
            throw new OrderDoesNotExistException(importInvoiceNumber);
        }

        // remove indicated quantity from currentOrder
        currentOrder.remove(removalQuantity,exportInvoiceNumber, exportDate);

        // if the current order has no items remaining in inventory, remove from activeOrders & add to completeOrders
        if (currentOrder.getCurrentQuantity() == 0) {
            this.activeOrders.remove(importInvoiceNumber);
            this.completeOrders.add(currentOrder);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the order we are looking for was an active order remove it,
    //          if the order we are looking for was an complete order remove it,
    //          otherwise throw OrderDoesNotExistException
    public void deleteOrder(String invoiceNumber) throws QuantityNegativeException, QuantityZeroException,
            InvalidImportDateException, OrderDoesNotExistException {
        Order currentOrder = this.activeOrders.get(invoiceNumber);

        if (currentOrder != null) {
            this.activeOrders.remove(invoiceNumber);
        } else {
            if (!this.completeOrders.remove(new Order("", null,
                    invoiceNumber,1, ""))) {
                throw new OrderDoesNotExistException(invoiceNumber);
            }
        }
    }

    // EFFECTS: if the order we are looking for is not an active order throw OrderDoesNotExistException,
    //          otherwise add monthly charge label to the current label with the specified/indicated details
    public void recordMonthlyCharge(String importInvoiceNum, LocalDate startDate, LocalDate endDate, int quantity,
                                    String monthlyInvoiceNum) throws OrderDoesNotExistException,
            QuantityZeroException, QuantityNegativeException, QuantityExceedsMaxQuantityException,
            InvalidStartDateException, InvalidEndDateException, InvalidMonthRangeException {
        Order currentOrder = this.activeOrders.get(importInvoiceNum);

        if (currentOrder == null) {
            throw new OrderDoesNotExistException(importInvoiceNum);
        }

        currentOrder.addMonthlyChargeLabel(quantity, monthlyInvoiceNum, startDate, endDate);
    }

    // EFFECTS: if the order we are looking for is not an active order throw OrderDoesNotExistException,
    //          otherwise update the orders content and storage location details
    public void editActiveOrder(String importInvoiceNum, String content, String storageLocation)
            throws OrderDoesNotExistException {
        Order currentOrder = this.activeOrders.get(importInvoiceNum);

        if (currentOrder == null) {
            throw new OrderDoesNotExistException(importInvoiceNum);
        }

        // update order
        currentOrder.setContent(content);
        currentOrder.setStorageLocation(storageLocation);
    }


    // MODIFIES: this
    // EFFECTS: sets the iterator that will be returned when iterator() is called
    public void chooseIterator(boolean b) {
        this.iterateActiveOrders = b;
    }


    // EFFECTS: returns JSON object representation of this Customer
    public JSONObject convertToJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.name);
        jsonObject.put("activeOrders", convertOrderListToJsonArray(this.activeOrders.values()));
        jsonObject.put("completeOrders", convertOrderListToJsonArray(this.completeOrders));
        return jsonObject;
    }

    // EFFECTS: converts and returns a list of Orders as a JSON array
    private JSONArray convertOrderListToJsonArray(Collection<Order> orderList) {
        JSONArray jsonArray = new JSONArray();
        for (Order o : orderList) {
            jsonArray.put(o.convertToJsonObject());
        }
        return jsonArray;
    }

    @Override
    public Iterator<String[]> iterator() {
        return new CustomerIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(this.name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * getters
     */

    public String getName() {
        return this.name;
    }

    public Map<String, Order> getActiveOrders() {
        return Collections.unmodifiableMap(this.activeOrders);
    }

    public List<Order> getCompleteOrders() {
        return Collections.unmodifiableList(this.completeOrders);
    }

    public int getActiveOrderSize() {
        return this.activeOrders.size();
    }

    public int getCompleteOrderSize() {
        return this.completeOrders.size();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * setters
     */

    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: sets Orders by converting given JSON Array representation of it
    public void setOrdersFromJsonArray(boolean setActiveOrders, JSONArray jsonOrders) throws CorruptFileException {
        for (Object o : jsonOrders) {
            JSONObject jo = (JSONObject) o;
            String content = jo.getString("content");
            LocalDate importDate = LocalDate.parse(jo.getString("importDate"));
            JSONArray exports = jo.getJSONArray("exports");
            JSONArray monthlyChargeLabels = jo.getJSONArray("monthlyChargeLabels");
            String invoiceNumber = jo.getString("invoiceNumber");
            int originalQuantity = jo.getInt("originalQuantity");
            int currentQuantity = jo.getInt("currentQuantity");
            String storageLocation = jo.getString("storageLocation");
            try {
                Order order = new Order(content, importDate, invoiceNumber, originalQuantity, storageLocation);
                order.setCurrentQuantity(currentQuantity);
                order.setLabelsFromJsonArray(true, exports);
                order.setLabelsFromJsonArray(false, monthlyChargeLabels);
                if (setActiveOrders) {
                    this.activeOrders.put(invoiceNumber, order);
                } else {
                    this.completeOrders.add(order);
                }
            } catch (QuantityNegativeException | QuantityZeroException | InvalidImportDateException e) {
                throw new CorruptFileException();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Private class that allows active orders or complete orders to be iterated
     */
    private class CustomerIterator implements Iterator<String[]> {
        private Iterator<Order> activeOrderIterator = activeOrders.values().iterator();
        private Iterator<Order> completeOrderIterator = completeOrders.iterator();

        @Override
        public boolean hasNext() {
            return iterateActiveOrders ? this.activeOrderIterator.hasNext() : this.completeOrderIterator.hasNext();
        }

        @Override
        public String[] next() {
            Order currentOrder
                    = iterateActiveOrders ? this.activeOrderIterator.next() : this.completeOrderIterator.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

            String invoiceNumber = currentOrder.getInvoiceNumber();
            String quantity = Integer.toString(currentOrder.getCurrentQuantity());
            String content = currentOrder.getContent();
            String importDate = currentOrder.getImportDate().format(formatter);
            String location = currentOrder.getStorageLocation();
            String exportInfo = currentOrder.getExportsString();
            String monthlyChargeInfo = currentOrder.getMonthlyChargeLabelsString();


            String[] returnArray = {name, invoiceNumber, quantity, content, importDate,
                    location, exportInfo, monthlyChargeInfo};

            return returnArray;
        }
    }
}
