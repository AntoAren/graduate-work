package by.bsu.zakharankou.restservices.tokenvalidator;

import static by.bsu.zakharankou.restservices.tokenvalidator.HttpHeader.WWW_AUTHENTICATE;
import static by.bsu.zakharankou.restservices.tokenvalidator.HttpStatus.FORBIDDEN;
import static by.bsu.zakharankou.restservices.tokenvalidator.HttpStatus.UNAUTHORIZED;

import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;

import by.bsu.zakharankou.restservices.common.TokenType;
import by.bsu.zakharankou.restservices.common.Utils;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.ExpiredTokenException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidTokenException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidTokenTypeException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidScopeException;

/**
 * Request handler that can be used in Vert.x application for validating access token contained in HTTP request.
 */
public class VertxRequestHandler extends RequestHandler<HttpServerRequest> {

    /**
     * Handle http request which should contain access token.
     * Check that request contains valid access token.
     * Expects token which is used for identifying user.
     * Ends response with appropriate error status code if access token is not valid/presented.
     * @param request request which contains access token
     * @return true if request contains valid access token, otherwise - false
     */
    public boolean handle(HttpServerRequest request) {
        return handle(request, null);
    }

    /**
     * Handle http request which should contain access token.
     * Check that request contains valid access token.
     * Expects token which is used for accessing resources.
     * Ends response with appropriate error status code if access token is not valid/presented.
     * @param request request which contains access token
     * @param resourceId id of resource which is accessed using the access token from the specified request
     * @return true if request contains valid access token, otherwise - false
     */
    public boolean handle(HttpServerRequest request, String resourceId) {
        HttpServerResponse response = getResponse(request);
        String token = TokenExtractor.extractToken(request);

        if (Utils.isBlank(token)) {
            String authHeader = new AuthenticateHeader(TokenType.BEARER.getAuthScheme())
                    .realm(REALM).toString();

            response.putHeader(WWW_AUTHENTICATE, authHeader);
            response.setStatusCode(UNAUTHORIZED);
            response.end();
            return false;
        }

        try {
            tokenValidator.validateToken(token, resourceId);
            return true;
        } catch (InvalidTokenTypeException | InvalidScopeException e1) {
            String authHeader = new AuthenticateHeader(TokenType.BEARER.getAuthScheme())
                    .realm(REALM)
                    .error((e1 instanceof InvalidScopeException) ? ErrorCode.ERROR_INSUFFICIENT_SCOPE : ErrorCode.ERROR_INVALID_TOKEN)
                    .errorDescription(e1.getMessage()).toString();

            response.putHeader(WWW_AUTHENTICATE, authHeader);
            response.setStatusCode(FORBIDDEN);
            response.end();
            return false;
        } catch (InvalidTokenException | ExpiredTokenException e2) {
            String authHeader = new AuthenticateHeader(TokenType.BEARER.getAuthScheme())
                    .realm(REALM)
                    .error(ErrorCode.ERROR_INVALID_TOKEN)
                    .errorDescription(e2.getMessage()).toString();

            response.putHeader(WWW_AUTHENTICATE, authHeader);
            response.setStatusCode(UNAUTHORIZED);
            response.end();
            return false;
        }
    }

    @Override
    protected String getAccessToken(HttpServerRequest request) {
        return TokenExtractor.extractToken(request);
    }

    HttpServerResponse getResponse(HttpServerRequest request) {
        return request.response();
    }
}
