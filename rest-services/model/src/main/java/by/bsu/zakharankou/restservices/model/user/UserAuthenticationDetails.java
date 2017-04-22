package by.bsu.zakharankou.restservices.model.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class UserAuthenticationDetails extends WebAuthenticationDetails {

    private final String username;

    public UserAuthenticationDetails(HttpServletRequest request, String username) {
        super(request);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "AuthenticationDetails [username=" + username + "]";
    }

}