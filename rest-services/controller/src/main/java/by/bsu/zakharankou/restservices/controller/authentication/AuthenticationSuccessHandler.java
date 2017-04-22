package by.bsu.zakharankou.restservices.controller.authentication;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @PostConstruct
    public void afterPropertiesSet() {
        setRedirectStrategy(new NoRedirectStrategy());
    }

    private static class NoRedirectStrategy implements RedirectStrategy {
        @Override
        public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url) throws IOException {
            // There is not need in redirection of spring default implementation.
        }
    }

}
