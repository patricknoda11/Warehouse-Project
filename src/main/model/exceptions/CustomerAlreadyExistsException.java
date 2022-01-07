package model.exceptions;

/**
 * Represents an Exception thrown when the customer indicated/specified already exists
 */
public class CustomerAlreadyExistsException extends Exception {

    public CustomerAlreadyExistsException(String msg) {
        super("ERROR--- The specified customer \"" + msg + "\" already exists");
    }

}
