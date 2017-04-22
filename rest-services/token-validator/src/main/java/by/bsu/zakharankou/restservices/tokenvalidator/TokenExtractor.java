package by.bsu.zakharankou.restservices.tokenvalidator;

import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.vertx.java.core.MultiMap;
import org.vertx.java.core.http.CaseInsensitiveMultiMap;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;

import by.bsu.zakharankou.restservices.common.TokenType;
import by.bsu.zakharankou.restservices.common.Utils;

/**
 * Helper class with methods for extracting access token from request.
 * Access token can be placed in:
 * <p>
 * - the Authorization request header<br/>
 * - the HTTP request entity-body<br/>
 * - the HTTP request URI
 * </p>
 */
public final class TokenExtractor {

    private static final String SPACE_REGEX = "\\s+";

    private static final String COMMA_REGEX = ",";

    private static final String AMPERSAND_REGEX = "&";

    private static final String EQUALS_REGEX = "=";

    public static final String ACCESS_TOKEN_PARAM = "accessToken";

    public static final String IDENTITY_TOKEN_PARAM = "identityToken";

    private TokenExtractor() {
    }

    /**
     * Extract access token from http request.
     * @param request http request
     * @return access token or {@code null} if there is no access token in request
     */
    public static String extractToken(HttpServletRequest request) {
        MultiMap headers = new CaseInsensitiveMultiMap();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            headers.add(headerName, Collections.list(headerValues));
        }

        return TokenExtractor.extractToken(headers, request.getQueryString());
    }

    /**
     * Extract access token from http request.
     * @param request http request
     * @return access token or {@code null} if there is no access token in request
     */
    public static String extractToken(HttpServerRequest request) {
        String query = request.query();

        try {
            String decodedQuery = (query == null) ? null : URLDecoder.decode(query, CharsetUtil.UTF_8.name());
            return TokenExtractor.extractToken(request.headers(), decodedQuery);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Request query can't be encoded.", e);
        }
    }

    /**
     * Extract access token from websocket.
     * @param webSocket websocket
     * @return access token or {@code null} if there is no access token in request
     */
    public static String extractToken(ServerWebSocket webSocket) {
        //NOTE: websocket query already decoded
        return TokenExtractor.extractToken(webSocket.headers(), webSocket.query());
    }

    private static String extractToken(MultiMap headers, String query) {
        String token = TokenExtractor.extractTokenFromHeaders(headers);

        if (token == null) {
            token = TokenExtractor.extractTokenFromQuery(query);
        }

        return token;
    }

    /**
     * Extract access token from request headers.
     * @param headers a map of request headers
     * @return access token or {@code null} if there is no access token in headers
     */
    protected static String extractTokenFromHeaders(MultiMap headers) {
        List<String> authHeaders = headers.getAll(HttpHeader.AUTHORIZATION);

        if (Utils.isBlank(authHeaders)) {
            return null;
        }

        //The first header should be taken.
        String authHeader = authHeaders.get(0);

        if (Utils.isBlank(authHeader)) {
            return null;
        }

        //'headers.getAll' doesn't understand multiple comma-separated header values,
        //so we do it mannualy
        for (String value : authHeader.split(COMMA_REGEX)) {
            String[] parts = value.trim().split(SPACE_REGEX);

            if ((parts.length >= 2) && TokenType.BEARER.getAuthScheme().equalsIgnoreCase(parts[0])) {
                return parts[1];
            }
        }

        return null;
    }

    /**
     * Extract access token from request query.
     * @param query request query string. Has to be decoded.
     * @return access token or {@code null} if there is no access token in query
     */
    protected static String extractTokenFromQuery(String query) {
        if (Utils.isBlank(query)) {
            return null;
        }

        String[] queryParts = query.split(AMPERSAND_REGEX);
        for (String part: queryParts) {
            String[] parameter = part.split(EQUALS_REGEX, 2);
            if (parameter.length == 2 && ACCESS_TOKEN_PARAM.equalsIgnoreCase(parameter[0])) {
                return parameter[1];
            } else if (parameter.length == 2 && IDENTITY_TOKEN_PARAM.equalsIgnoreCase(parameter[0])) {
                return parameter[1];
            }
        }

        return null;
    }

}
