package by.bsu.zakharankou.restservices.service.serviceapi;

import java.util.Map;

public class EntityAlreadyExistsException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistsException(final String message) {
        super(message);
    }

    public EntityAlreadyExistsException(final String message, final Map<String, Object> context) {
        super(message, context);
    }
}