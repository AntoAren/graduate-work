package by.bsu.zakharankou.restservices.service.serviceapi;

import java.util.Collections;
import java.util.Map;

/**
 * Base class for all service exceptions.
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Map<String, Object> context = Collections.emptyMap();

    public ServiceException(final String message) {
        super(message);
    }

    /**
     * Constructs a new service exception with the specified detailed message and context.
     *
     * @param message the detailed message of the issue
     * @param context map with ancillary data for the exception
     */
    public ServiceException(final String message, final Map<String, Object> context) {
        super(message);
        if (context != null) {
            this.context = context;
        }
    }

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServiceException(final Throwable cause) {
        super(cause);
    }

    public Map<String, Object> getContext() {
        return context;
    }
}

