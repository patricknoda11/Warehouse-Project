package model.exceptions;

/**
 * Represents an Exception thrown when the removal quantity exceeds the quantity currently available
 */
public class RemovalQuantityExceedsAvailabilityException extends InvalidQuantityException {

    public RemovalQuantityExceedsAvailabilityException() {
        super("The quantity indicated exceeds the available quantity");
    }

}
