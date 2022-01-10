package model.exceptions;

/**
 * Represents an Exception that is thrown when the import date indicated/specified is a date that has not yet occurred
 */
public class InvalidImportDateException extends Exception {

    public InvalidImportDateException() {
        super("The Import date specified is a future date");
    }
}
