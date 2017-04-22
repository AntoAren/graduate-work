package by.bsu.zakharankou.restservices.tokenvalidator;

import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.ExpiredTokenException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidScopeException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidTokenException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidTokenTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.http.ServerWebSocket;

import by.bsu.zakharankou.restservices.common.Utils;

/**
 * Handler that can be used in Vert.x application for validating access token contained in websocket.
 */
public class VertxWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(VertxWebSocketHandler.class);

    private TokenValidator tokenValidator;

    public VertxWebSocketHandler() {
        tokenValidator = TokenValidator.getInstance();
    }

    /**
     * Handle websocket which should contain access token.
     * Check that websocket contains valid access token.
     * Expects token which is used for identifying user.
     * Rejects websocket if access token is not valid/presented.
     * @param webSocket websocket which contains access token
     * @return true if websocket contains valid access token, otherwise - false
     */
    public boolean handle(ServerWebSocket webSocket) {
        return handle(webSocket, null);
    }

    /**
     * Handle websocket which should contain access token.
     * Check that websocket contains valid access token.
     * Expects token which is used for accessing resources.
     * Rejects websocket if access token is not valid/presented.
     * @param webSocket websocket which contains access token
     * @param resourceId id of resource which is accessed using the access token from the specified websocket
     * @return true if websocket contains valid access token, otherwise - false
     */
    public boolean handle(ServerWebSocket webSocket, String resourceId) {
        String token = TokenExtractor.extractToken(webSocket);

        if (Utils.isBlank(token)) {
            LOGGER.warn("There is no access token. Query: " + webSocket.query());
            webSocket.reject();
            return false;
        }

        try {
            tokenValidator.validateToken(token, resourceId);
        } catch (InvalidTokenException | InvalidTokenTypeException | ExpiredTokenException | InvalidScopeException e) {
            LOGGER.warn("Access token is not valid.", e);
            webSocket.reject();
            return false;
        }

        return true;
    }

}
