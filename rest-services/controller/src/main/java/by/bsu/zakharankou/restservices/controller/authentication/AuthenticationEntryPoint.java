package by.bsu.zakharankou.restservices.controller.authentication;

import by.bsu.zakharankou.restservices.controller.ExceptionHandlerExceptionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of {@link org.springframework.security.web.AuthenticationEntryPoint}.
 * Uses {@link ExceptionHandlerExceptionResolver} to render exception view.
 */
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @Autowired
    private ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        exceptionHandlerExceptionResolver.renderException(request, response, authException);
    }
}
