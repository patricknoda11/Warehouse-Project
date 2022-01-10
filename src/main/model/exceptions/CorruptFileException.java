package model.exceptions;

public class CorruptFileException extends Exception {

    public CorruptFileException() {
        super("ERROR--- Cannot read from file");
    }

}
