package by.bsu.zakharankou.restservices.controller;

/**
 * Base class for all controller exceptions.
 */
public class WebException extends RuntimeException {

    /**
     * Constructs a new controller exception with the specified detail message.
     *
     * @param message the detailed message of the issue
     */
    public WebException(String message) {
        super(message);
    }

    public WebException(Throwable cause) {
        super(cause);
    }
}