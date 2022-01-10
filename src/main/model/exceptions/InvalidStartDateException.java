package model.exceptions;

/**
 * Represents an Exception that is thrown when the monthly charge label's start date occurs before the Order import date
 */
public class InvalidStartDateException extends Exception {

    public InvalidStartDateException() {
        super("ERROR--- The monthly charge start date occurs before the product import date");
    }
}
