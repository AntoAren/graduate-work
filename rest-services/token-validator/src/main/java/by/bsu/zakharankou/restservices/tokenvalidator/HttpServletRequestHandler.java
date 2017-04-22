package by.bsu.zakharankou.restservices.tokenvalidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.zakharankou.restservices.common.TokenType;
import by.bsu.zakharankou.restservices.common.Utils;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.ExpiredTokenException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidScopeException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidTokenException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidTokenTypeException;

/**
 * Request handler that can be used for validating access token contained in HttpServletRequest.
 */
public class HttpServletRequestHandler extends RequestHandler<HttpServletRequest> {

    public boolean handle(HttpServletRequest request, HttpServletResponse response) {
        return handle(request, null);
    }

    public boolean handle(HttpServletRequest request, HttpServletResponse response, String resourceId) {
        String token = TokenExtractor.extractToken(request);

        if (Utils.isBlank(token)) {
            String authHeader = new AuthenticateHeader(TokenType.BEARER.getAuthScheme())
                    .realm(REALM).toString();

            response.addHeader(HttpHeader.WWW_AUTHENTICATE, authHeader);
            response.setStatus(HttpStatus.UNAUTHORIZED);
            //response.flushBuffer(); do we need it?
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

            response.addHeader(HttpHeader.WWW_AUTHENTICATE, authHeader);
            response.setStatus(HttpStatus.FORBIDDEN);
            return false;
        } catch (InvalidTokenException | ExpiredTokenException e2) {
            String authHeader = new AuthenticateHeader(TokenType.BEARER.getAuthScheme())
                    .realm(REALM)
                    .error(ErrorCode.ERROR_INVALID_TOKEN)
                    .errorDescription(e2.getMessage()).toString();

            response.addHeader(HttpHeader.WWW_AUTHENTICATE, authHeader);
            response.setStatus(HttpStatus.UNAUTHORIZED);
            //response.flushBuffer(); do we need it?
            return false;
        }
    }

    @Override
    protected String getAccessToken(HttpServletRequest request) {
        return TokenExtractor.extractToken(request);
    }
}
