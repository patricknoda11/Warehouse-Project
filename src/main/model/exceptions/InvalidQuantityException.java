package model.exceptions;

/**
 * Represents an Exception that is thrown when the quantity is invalid
 */
public class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String msg) {
        super("ERROR--- " + msg);
    }
}
