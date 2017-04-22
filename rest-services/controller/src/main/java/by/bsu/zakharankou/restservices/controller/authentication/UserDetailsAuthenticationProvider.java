package by.bsu.zakharankou.restservices.controller.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final String N_A = "N/A";

    @Override
    protected void additionalAuthenticationChecks(final UserDetails userDetails, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // Additional authentication checks may be added here.
    }

    @Override
    protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return new org.springframework.security.core.userdetails.User(username, N_A, authentication.getAuthorities());
    }

}