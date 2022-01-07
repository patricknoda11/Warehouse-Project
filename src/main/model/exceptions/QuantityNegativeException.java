package model.exceptions;

/**
 * Represents an Exception thrown when the quantity is negative
 */
public class QuantityNegativeException extends InvalidQuantityException {

    public QuantityNegativeException() {
        super("The quantity inputted is negative");
    }
}
