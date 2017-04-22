package by.bsu.zakharankou.restservices.tokenvalidator.exceptions;

public class InvalidTokenTypeException extends RuntimeException {

    public InvalidTokenTypeException(String message) {
        super(message);
    }
}
