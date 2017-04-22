package by.bsu.zakharankou.restservices.tokenvalidator;

import java.io.IOException;

import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidTokenException;
import by.bsu.zakharankou.restservices.common.JWS;
import by.bsu.zakharankou.restservices.common.JWSService;
import by.bsu.zakharankou.restservices.common.TokenFormatException;
import by.bsu.zakharankou.restservices.common.Utils;

public class RequestHandler<T> {

    public static final String REALM = "itest";

    protected TokenValidator tokenValidator;

    protected JWSService jwsService;

    public RequestHandler() {
        tokenValidator = TokenValidator.getInstance();
        jwsService = JWSService.getInstance();
    }

    /**
     * Get details of request sender that are defined in access token inside the request.
     * @param request request with access token
     * @throws InvalidTokenException if access token from request can't be parsed
     * @return details of request sender
     */
    public JWS.JWSAudience getSenderDetails(T request) {
        String accessToken = getAccessToken(request);

        if (Utils.isBlank(accessToken)) {
            throw new InvalidTokenException("Access token is empty.");
        }

        JWS jws;

        try {
            jws = jwsService.parse(accessToken);
            return jws.getPayload().getAud();
        } catch (IOException | TokenFormatException e) {
            throw new InvalidTokenException("Access token can't be parsed.", e);
        }
    }

    protected String getAccessToken(T request) {
        //Override me.
        return Utils.EMPTY;
    }

}
