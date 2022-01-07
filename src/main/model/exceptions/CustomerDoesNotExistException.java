package model.exceptions;

/**
 * Represents an Exception thrown if the customer indicated/specified does not exist
 */
public class CustomerDoesNotExistException extends Exception {

    public CustomerDoesNotExistException(String name) {
        super("ERROR--- The indicated customer called " + name + " does not exist");
    }

}
