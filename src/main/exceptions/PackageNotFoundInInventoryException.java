package exceptions;

public class PackageNotFoundInInventoryException extends Exception {

    public PackageNotFoundInInventoryException(String msg) {
        super(msg);
    }
}
