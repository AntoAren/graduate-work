package by.bsu.zakharankou.restservices.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import by.bsu.zakharankou.restservices.service.serviceapi.ServiceException;
import by.bsu.zakharankou.restservices.service.serviceapi.util.SequenceGenerator;

/**
 * Implementation of the {@link HandlerExceptionResolver}.
 * Resolves exceptions thrown during handler mapping or execution to error views.
 */
@Component
public class ExceptionHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerExceptionResolver.class);

    private static final String SERVER_ERROR_PLEASE_TRY_LATER = "Server error: %s. Please try later.";
    private static final String INCORRECT_FORMAT_OF_REQUEST_BODY = "Incorrect format of the request body.";
    private static final String INCORRECT_TYPE_OF_REQUEST_PARAMETER = "Incorrect type of request parameter with value '%s'.";
    private static final String MESSAGE = "message";
    private static final String CONTEXT = "context";

    @Autowired
    private ExceptionStatusCodeResolver exceptionStatusCodeResolver;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Autowired
    private View view;

    /**
     * By default some exceptions may be crabbed and handled by one of the default handlers.
     * Puts this resolver at the front of the queue for resolving exceptions.
     *
     * @return order (the lowest value having the highest priority)
     */
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    /**
     * Resolves the given exception that got thrown during on handler execution.
     * Returns a ModelAndView that represents a specific status code and error message.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  not used (<code>null</code>)
     * @param ex       the exception that got thrown during handler execution
     * @return json ModelAndView with appropriate status code and error message
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        final HttpStatus httpStatus = exceptionStatusCodeResolver.resolve(ex.getClass());
        response.setStatus(httpStatus.value());

        final String reason;

        if (httpStatus == INTERNAL_SERVER_ERROR) {
            final String issueId = sequenceGenerator.uniqueIdentifier();
            reason = String.format(SERVER_ERROR_PLEASE_TRY_LATER, issueId);
            LOGGER.error(String.format("Internal server error: %s. ", issueId), ex);
        } else if (ex instanceof HttpMessageNotReadableException) {
            reason = INCORRECT_FORMAT_OF_REQUEST_BODY;
        } else if (ex instanceof TypeMismatchException) {
            reason = String.format(INCORRECT_TYPE_OF_REQUEST_PARAMETER, ((TypeMismatchException)ex).getValue());
        } else {
            reason = ex.getMessage();
            LOGGER.warn("Warning. ", ex);
        }

        final Map<String, Object> model = new HashMap<>();
        model.put(MESSAGE, reason);

        if (ex instanceof ServiceException) {
            final Map<String, Object> context = ((ServiceException) ex).getContext();
            if (!context.isEmpty()) {
                model.put(CONTEXT, context);
            }
        }

        return new ModelAndView(view, model);
    }

    /**
     * Renders the view from the specified exception.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param ex       the exception that got thrown
     */
    public void renderException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        try {
            final ModelAndView modelAndView = resolveException(request, response, null, ex);
            final View view = modelAndView.getView();
            final Map<String, Object> model = modelAndView.getModel();
            view.render(model, request, response);
        } catch (Exception e) {
            final String issueId = sequenceGenerator.uniqueIdentifier();
            LOGGER.error(String.format("Render exception failed: %s", issueId), e);
            throw new RuntimeException(String.format(SERVER_ERROR_PLEASE_TRY_LATER, issueId));
        }
    }
}
