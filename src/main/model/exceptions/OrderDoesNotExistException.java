package model.exceptions;

/**
 * Represents an Exception that is thrown when the indicated/specified Order does not exist
 */
public class OrderDoesNotExistException extends Exception {

    public OrderDoesNotExistException(String msg) {
        super("ERROR--- The Order with the given invoice number: " + msg + " does not exist");
    }
}
