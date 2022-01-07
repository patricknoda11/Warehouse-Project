package model.exceptions;

/**
 * Represents an Exception thrown when the "initial date" and "end date" does not span between 28 - 31 days
 */
public class InvalidMonthRangeException extends Exception {

    public InvalidMonthRangeException() {
        super("ERROR--- The \"initial date\" and \"end date\" does not accurately represent a month in length");
    }
}
