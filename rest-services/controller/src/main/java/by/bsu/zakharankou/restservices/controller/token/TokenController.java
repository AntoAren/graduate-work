package by.bsu.zakharankou.restservices.controller.token;

import by.bsu.zakharankou.restservices.controller.util.AuthenticationExtractor;
import by.bsu.zakharankou.restservices.model.authority.Authority;
import by.bsu.zakharankou.restservices.model.token.Credentials;
import by.bsu.zakharankou.restservices.model.token.IdentityToken;
import by.bsu.zakharankou.restservices.service.serviceapi.token.*;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserDetails;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.ERROR_BAD_CREDENTIALS;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationExtractor authenticationExtractor;

    @RequestMapping(value = "/identity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public IdentityToken createIdentityToken(HttpServletRequest request)
            throws IOException, GeneralSecurityException, URISyntaxException {
        Credentials credentials = authenticationExtractor.extractBasicAuthentication(request);

        if (credentials == null) {
            throw new BadCredentialsException(ERROR_BAD_CREDENTIALS);
        }

        return tokenService.createIdentityToken(credentials);
    }

    @RequestMapping(value = "/refresh/identity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public IdentityToken refreshIdentityToken(@RequestBody RefreshTokenDetails refreshTokenDetails)
            throws IOException, GeneralSecurityException, URISyntaxException {
        return tokenService.refreshIdentityToken(refreshTokenDetails);
    }

    private Map<String, Object> createUserDetails(String username, String userDisplayName, String organizationId) {
        Map<String, Object> userDetails = new HashMap<>();
        String password = RandomStringUtils.random(10, true, false);

        userDetails.put(UserDetails.KEY_USERNAME, username);
        userDetails.put(UserDetails.KEY_ORGANIZATION, organizationId);
        userDetails.put(UserDetails.KEY_PASSWORD, password);
        userDetails.put(UserDetails.KEY_PASSWORD_CONFIRM, password);
        userDetails.put(UserDetails.KEY_DISPLAY_NAME, userDisplayName);
        userDetails.put(UserDetails.KEY_ROLES, Arrays.asList(Authority.ROLE_DEVELOPER));

        return userDetails;
    }
}
