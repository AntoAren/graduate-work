package by.bsu.zakharankou.restservices.controller.util;

import by.bsu.zakharankou.restservices.common.JWS;
import by.bsu.zakharankou.restservices.common.JWS.JWSSubject;
import by.bsu.zakharankou.restservices.model.token.Credentials;
import by.bsu.zakharankou.restservices.model.token.IdentityCredentials;
import by.bsu.zakharankou.restservices.tokenvalidator.TokenExtractor;
import by.bsu.zakharankou.restservices.tokenvalidator.TokenValidator;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static by.bsu.zakharankou.restservices.common.Utils.isBlank;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Helper class with methods for extracting access credentials from request.
 */
@Component
public class AuthenticationExtractor {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BASIC_AUTH_PREFIX = "Basic";
    public static final String BEARER_AUTH_PREFIX = "Bearer";
    public static final String BASIC_AUTH_SEPARATOR = ":";

    public Credentials extractAuthenticationCredentials(HttpServletRequest request) {
        Credentials basicAuthenticationCredentials = extractBasicAuthentication(request);

        return basicAuthenticationCredentials == null ? extractBearerAuthentication(request) : basicAuthenticationCredentials;
    }

    /**
     * Extract basic access authentication credentials.
     * @param request http request
     * @return access credentials or {@code null} if there is no authentication details in request
     */
    public Credentials extractBasicAuthentication(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if (authorization == null) {
            return null;
        }

        authorization = authorization.trim();

        if (authorization.startsWith(BASIC_AUTH_PREFIX) && (authorization.length() > BASIC_AUTH_PREFIX.length())) {
            String base64Credentials = authorization.substring(BASIC_AUTH_PREFIX.length());
            String credentials = new String(Base64.decodeBase64(base64Credentials), UTF_8);
            String[] credParts = credentials.split(BASIC_AUTH_SEPARATOR, 2);

            if (credParts.length != 2) {
                return null;
            }

            String username = credParts[0];
            String password = credParts[1];

            if (isBlank(username) || isBlank(password)) {
                return null;
            }

            return new Credentials(username.toLowerCase(), password);
        }

        return null;
    }

    /**
     * Extract bearer access authentication credentials.
     * @param request http request
     * @return access credentials or {@code null} if there is no authentication details in request
     */
    public IdentityCredentials extractBearerAuthentication(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if (authorization == null) {
            return null;
        }

        authorization = authorization.trim();

        if (authorization.startsWith(BEARER_AUTH_PREFIX) && (authorization.length() > BEARER_AUTH_PREFIX.length())) {
            String token = TokenExtractor.extractToken(request);
            JWS jws = TokenValidator.getInstance().validateIdentityToken(token);
            JWSSubject subject = JWSSubject.parse(jws.getPayload().getSub());
            return new IdentityCredentials(subject, jws.getPayload().getAud().getUser());
        }

        return null;
    }
}
