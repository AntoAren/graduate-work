package by.bsu.zakharankou.restservices.controller;

import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Class that manages mapping between exceptions and HTTP statuses.
 */
public class ExceptionStatusCodeResolver {

    private final Map<Class<? extends Throwable>, HttpStatus> exceptionStatuses;

    /**
     * Constructor.
     *
     * @param exceptionStatuses Map with Throwable class as key and corresponding HttpStatus as value
     */
    public ExceptionStatusCodeResolver(Map<Class<? extends Throwable>, HttpStatus> exceptionStatuses) {
        this.exceptionStatuses = exceptionStatuses;
    }

    /**
     * Get HTTP status associated with an exception.
     *
     * @param exceptionClass exception
     * @return HTTP status object
     */
    public HttpStatus resolve(Class<? extends Throwable> exceptionClass) {
        HttpStatus result = exceptionStatuses.get(exceptionClass);
        if (result == null) {
            result = INTERNAL_SERVER_ERROR;
        }
        return result;
    }
}
