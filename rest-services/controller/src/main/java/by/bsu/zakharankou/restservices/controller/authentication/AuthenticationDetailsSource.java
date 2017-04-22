package by.bsu.zakharankou.restservices.controller.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import by.bsu.zakharankou.restservices.common.JWS.JWSAudience;
import by.bsu.zakharankou.restservices.model.user.UserAuthenticationDetails;
import by.bsu.zakharankou.restservices.tokenvalidator.HttpServletRequestHandler;

public class AuthenticationDetailsSource extends WebAuthenticationDetailsSource {

    private HttpServletRequestHandler requestHandler = new HttpServletRequestHandler();

    @Override
    public UserAuthenticationDetails buildDetails(HttpServletRequest context) {
        final JWSAudience audience = requestHandler.getSenderDetails(context);
        return new UserAuthenticationDetails(context, audience.getUser());
    }

}
