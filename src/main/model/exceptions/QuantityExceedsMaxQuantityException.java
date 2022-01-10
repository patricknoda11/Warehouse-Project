package model.exceptions;

/**
 * Represents an Exception thrown when the quantity exceeds the max quantity (upper limit) for an order
 */
public class QuantityExceedsMaxQuantityException extends InvalidQuantityException {

    public QuantityExceedsMaxQuantityException(int quantity, int maxQuantity) {
        super("The quantity given " + quantity + " exceeds the maximum/original quantity " + maxQuantity);
    }
}
