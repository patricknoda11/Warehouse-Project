package model.exceptions;

/**
 * Represents an Exception thrown when the quantity is zero
 */
public class QuantityZeroException extends InvalidQuantityException {

    public QuantityZeroException() {
        super("The quantity indicated is \"0\"");
    }
}
