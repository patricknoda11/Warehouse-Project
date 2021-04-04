package ui.exceptions;

// This class represents an checked exception that is thrown when an indicated package is not found in inventory
public class PackageNotFoundInInventoryException extends Exception {

    public PackageNotFoundInInventoryException(String msg) {
        super(msg);
    }
}
