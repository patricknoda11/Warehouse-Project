package model.exceptions;

/**
 * Represents an Exception thrown when the indicated/specified Order already exists
 */
public class OrderAlreadyExistsException extends Exception {

    public OrderAlreadyExistsException(String invoiceNumber) {
        super("ERROR--- Order number: " + invoiceNumber + " already exists");
    }

}
