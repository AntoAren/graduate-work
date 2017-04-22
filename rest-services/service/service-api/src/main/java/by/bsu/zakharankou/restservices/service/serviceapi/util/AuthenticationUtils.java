package by.bsu.zakharankou.restservices.service.serviceapi.util;

import by.bsu.zakharankou.restservices.model.authority.Authority;
import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.model.user.UserAuthenticationDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class AuthenticationUtils {

    private static final String N_A = "N/A";

    private AuthenticationUtils() {}

    public static Authentication createAuthentication(User user, HttpServletRequest request) {
        Set<Authority> authorities = user.getAuthorities();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(authorities.size());

        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }

        UserAuthenticationDetails userAuthenticationDetails = new UserAuthenticationDetails(
                request, user.getUsername());

        return createAuthentication(userAuthenticationDetails, grantedAuthorities);
    }

    public static Authentication createAuthentication(UserAuthenticationDetails userAuthenticationDetails,
                                                      Collection<GrantedAuthority> grantedAuthorities) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userAuthenticationDetails.getUsername(), N_A, grantedAuthorities);

        authentication.setDetails(userAuthenticationDetails);

        return authentication;
    }
}
