package model.exceptions;

/**
 * Represents an Exception thrown when the end date for a monthly charge label is a date that has not yet occurred
 */
public class InvalidEndDateException extends Exception {

    public InvalidEndDateException() {
        super("ERROR--- The end date is a future date");
    }
}
