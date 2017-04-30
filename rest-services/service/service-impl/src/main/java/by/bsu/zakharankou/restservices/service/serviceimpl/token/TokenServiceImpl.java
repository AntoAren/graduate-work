package by.bsu.zakharankou.restservices.service.serviceimpl.token;

import by.bsu.zakharankou.restservices.common.JWS;
import by.bsu.zakharankou.restservices.common.JWS.JWSAudience;
import by.bsu.zakharankou.restservices.common.JWS.JWSType;
import by.bsu.zakharankou.restservices.common.JWS.JWSSubject;
import by.bsu.zakharankou.restservices.common.JWSService;
import by.bsu.zakharankou.restservices.common.JWSService.TokensPair;
import by.bsu.zakharankou.restservices.common.TokenType;
import by.bsu.zakharankou.restservices.model.authority.Authority;
import by.bsu.zakharankou.restservices.model.token.*;
import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.service.serviceapi.EntityNotFoundException;
import by.bsu.zakharankou.restservices.service.serviceapi.EntityValidationException;
import by.bsu.zakharankou.restservices.service.serviceapi.Messages;
import by.bsu.zakharankou.restservices.service.serviceapi.token.RefreshTokenDetails;
import by.bsu.zakharankou.restservices.service.serviceapi.token.TokenService;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserService;
import by.bsu.zakharankou.restservices.tokenvalidator.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashSet;
import java.util.Set;

import static by.bsu.zakharankou.restservices.common.Utils.*;

@Service
public class TokenServiceImpl implements TokenService {

    private static final long ONE_MONTH_IN_MILLISECONDS = 2592000000L;

    private static final long ONE_HUNDRED_YEARS_IN_MILLISECONDS = 3155692597470L;

    private static final PrivateKey PRIVATE_KEY;

    private static final String SIG_ALG_NAME;

    private static final String THUMBPRINT;

    private JWSService jwsService = JWSService.getInstance();

    private TokenValidator tokenValidator = TokenValidator.getInstance();

    @Autowired
    private UserService userService;

    static {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            InputStream certStream = TokenServiceImpl.class.getResourceAsStream("/x509-credentials/cert.der");
            X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(certStream);

            InputStream keyStream = TokenServiceImpl.class.getResourceAsStream("/x509-credentials/key.pk8");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(toByteArray(keyStream));
            KeyFactory keyFactory = KeyFactory.getInstance(certificate.getPublicKey().getAlgorithm());

            PRIVATE_KEY = keyFactory.generatePrivate(keySpec);
            SIG_ALG_NAME = certificate.getSigAlgName();

            //We check here whether we can make such a signature, but we won't use it.
            //We'll make a new signature object in the createAccessToken() method in order to be thread-safe.
            Signature signature = Signature.getInstance(SIG_ALG_NAME);
            signature.initSign(PRIVATE_KEY);

            THUMBPRINT = calculateThumbrint(certificate);
        } catch(Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public IdentityToken refreshIdentityToken(RefreshTokenDetails refreshTokenDetails)
            throws IOException, GeneralSecurityException, URISyntaxException {
        JWS jws = tokenValidator.validateIdentityRefreshToken(refreshTokenDetails.getRefreshToken());

        Credentials credentials = new Credentials(jws.getPayload().getAud().getUser(), null);

        return createIdentityToken(credentials);
    }

    @Override
    public IdentityToken createIdentityToken(Credentials credentials)
            throws IOException, GeneralSecurityException, URISyntaxException {
        User user = findUser(credentials);

        JWSAudience audience = createTokenAudience(user);
        Signature signature = createInitializedSignature();

        long tokenLifetime = ONE_MONTH_IN_MILLISECONDS;
        TokensPair tokensPair = jwsService.generateTokensPair(JWSType.IDENTITY_TOKEN, JWSType.IDENTITY_REFRESH_TOKEN,
                signature, THUMBPRINT, audience, tokenLifetime);

        return new IdentityToken(tokensPair.getToken(), TokenType.BEARER.getTypeName(), tokensPair.getRefreshToken(), tokenLifetime);
    }

    private JWSAudience createTokenAudience(User user) {
        JWSAudience audience = new JWSAudience();
        audience.setUser(user.getUsername());
        audience.setUserRoles(convertAuthoritiesToStrings(user.getAuthorities()));
        return audience;
    }

    private Signature createInitializedSignature() throws NoSuchAlgorithmException, InvalidKeyException {
        Signature signature = Signature.getInstance(SIG_ALG_NAME);
        signature.initSign(PRIVATE_KEY);

        return signature;
    }

    private User findUser(Credentials credentials) {
        try {
            String password = credentials.getPassword();
            String username = credentials.getUsername();
            return (password == null) ? userService.getUser(username) : userService.getUser(username, password);
        } catch (EntityNotFoundException e) {
            throw new BadCredentialsException(Messages.ERROR_BAD_CREDENTIALS, e);
        }
    }

    private Set<String> convertAuthoritiesToStrings(Set<Authority> authorities) {
        Set<String> strings = new HashSet<>();
        for (Authority authority : authorities) {
            strings.add(authority.getName());
        }
        return strings;
    }
}