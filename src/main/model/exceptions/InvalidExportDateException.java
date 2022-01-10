package model.exceptions;

/**
 * Represents an Exception thrown when the export date specified occurs before the import date or has not yet occurred
 */
public class InvalidExportDateException extends Exception {

    public InvalidExportDateException() {
        super("ERROR--- The export date specified is invalid");
    }

}
