package by.bsu.zakharankou.restservices.controller.util;

import by.bsu.zakharankou.restservices.controller.WebException;

/**
 * Shows that JSON is not valid.
 */
public class InvalidJsonException extends WebException {

    public InvalidJsonException(final String message) {
        super(message);
    }
}
