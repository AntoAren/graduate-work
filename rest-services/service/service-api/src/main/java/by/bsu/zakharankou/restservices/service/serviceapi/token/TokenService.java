package by.bsu.zakharankou.restservices.service.serviceapi.token;

import by.bsu.zakharankou.restservices.model.token.Credentials;
import by.bsu.zakharankou.restservices.model.token.IdentityToken;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

public interface TokenService {

    IdentityToken createIdentityToken(Credentials credentials) throws IOException, GeneralSecurityException, URISyntaxException;

    IdentityToken refreshIdentityToken(RefreshTokenDetails refreshTokenDetails)
            throws IOException, GeneralSecurityException, URISyntaxException;
}
