package by.bsu.zakharankou.restservices.controller.authentication;

import by.bsu.zakharankou.restservices.common.JWS.JWSAudience;
import by.bsu.zakharankou.restservices.model.user.UserAuthenticationDetails;
import by.bsu.zakharankou.restservices.service.serviceapi.util.AuthenticationUtils;
import by.bsu.zakharankou.restservices.tokenvalidator.HttpServletRequestHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class BearerHeaderAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private List<Pattern> excludePatterns = Collections.emptyList();
    private HttpServletRequestHandler requestHandler = new HttpServletRequestHandler();

    public BearerHeaderAuthenticationFilter() {
        super("/default");
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (requestHandler.handle(request, response)) {
            final Authentication authentication = createAuthenticationToken(request);
            return this.getAuthenticationManager().authenticate(authentication);
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
    }

    @Override
    protected boolean requiresAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null && !AuthenticationFilterUtils.isRequestExcluded(request, excludePatterns)
                && !"OPTIONS".equalsIgnoreCase(request.getMethod());

    }

    /**
     * Set list of URL patterns that don't require bearer authorization header check.
     *
     * @param excludePatterns the list of patterns
     */
    public void setExcludePatterns(final List<String> excludePatterns) {
        this.excludePatterns = AuthenticationFilterUtils.prepareExcludePatterns(excludePatterns);
    }

    private Authentication createAuthenticationToken(final HttpServletRequest request) {
        final JWSAudience audience = requestHandler.getSenderDetails(request);
        final Set<String> roles = audience.getUserRoles();
        final List<GrantedAuthority> authorities = new ArrayList<>(roles.size());

        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        UserAuthenticationDetails authenticationDetails =
                (UserAuthenticationDetails) authenticationDetailsSource.buildDetails(request);

        return AuthenticationUtils.createAuthentication(authenticationDetails, authorities);
    }
}
