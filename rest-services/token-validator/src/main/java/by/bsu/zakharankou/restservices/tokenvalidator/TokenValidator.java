package by.bsu.zakharankou.restservices.tokenvalidator;

import by.bsu.zakharankou.restservices.common.JWS;
import by.bsu.zakharankou.restservices.common.JWS.JWSType;
import by.bsu.zakharankou.restservices.common.JWS.NewJWSSubject;
import by.bsu.zakharankou.restservices.common.JWSService;
import by.bsu.zakharankou.restservices.common.TokenFormatException;
import by.bsu.zakharankou.restservices.common.Utils;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.ExpiredTokenException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidScopeException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidTokenException;
import by.bsu.zakharankou.restservices.tokenvalidator.exceptions.InvalidTokenTypeException;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Class with methods for validating access token.
 */
public class TokenValidator {

    private static final TokenValidator INSTANCE = new TokenValidator();

    private static final Map<String, X509Certificate> CERTIFICATES = new HashMap<>(1);

    private JWSService jwsService = JWSService.getInstance();

    static {
        //create signatures based on available x509 certificates and store them in cache
        try {
            Set<String> certResources = new Reflections("x509-credentials", new ResourcesScanner()).getResources(Pattern.compile(".*\\.der"));

            if (certResources.isEmpty()) {
                throw new ExceptionInInitializerError("No X.509 certificates available.");
            }

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            for (String certResource : certResources) {
                InputStream certStream = TokenValidator.class.getResourceAsStream("/" + certResource);
                X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(certStream);
                String thumbprint = Utils.calculateThumbrint(certificate);

                //We check here whether we can make such a signature, but we won't use it.
                //We'll make a new signature object in the validate() method in order to be thread-safe.
                Signature signature = Signature.getInstance(certificate.getSigAlgName());
                signature.initVerify(certificate);

                CERTIFICATES.put(thumbprint, certificate);
            }
        } catch(Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private TokenValidator() {
    }

    public static TokenValidator getInstance() {
        return INSTANCE;
    }

    /**
     * Validate access token represented in JWS (JSON Web Signature) format.
     * Expects token which is used for accessing resources.
     * Checks that:
     * - token has correct type and can be used for accessing resources
     * - token is not expired
     * - scope of token is suitable for accessing the specified resource
     * - token is valid
     * @param token access token to validate
     * @param resourceId id of resource which is accessed using the specified access token
     * @return token in JWS format
     * @throws InvalidTokenException when token is not valid
     * @throws InvalidScopeException when scope of token is not suitable for accessing resources
     * @throws ExpiredTokenException when token is expired
     * @throws InvalidTokenTypeException when type of token is not valid
     */
    public JWS validateToken(String token, String resourceId) {
        JWS jws = parse(token);
        JWSType expectedTokenType = resourceId == null ? JWSType.IDENTITY_TOKEN : JWSType.ACCESS_TOKEN;

        validateType(jws, expectedTokenType);
        validateExpiration(jws);
        if (expectedTokenType == JWSType.ACCESS_TOKEN) {
            validateScope(jws, resourceId);
        }
        validateSignature(token, jws.getHeader().getX5t());

        return jws;
    }

    /**
     * Validate identity token represented in JWS (JSON Web Signature) format.
     * Uses {@link #validateToken(String, String)} method.
     * @param token identity token to validate
     * @return {@link JWS} token
     * @throws InvalidTokenException when token is not valid
     * @throws ExpiredTokenException when token is expired
     * @throws InvalidTokenTypeException when type of token is not valid
     */
    public JWS validateIdentityToken(String token) {
        return this.validateToken(token, null);
    }

    /**
     * Validate access refresh token represented in JWS (JSON Web Signature) format.
     * Checks that:
     * - token has correct type and can be used for refreshing access token
     * - token is not expired
     * - token is valid
     * @param token access refresh token to validate
     * @return token in JWS format
     * @throws InvalidTokenException when token is not valid
     * @throws ExpiredTokenException when token is expired
     * @throws InvalidTokenTypeException when type of token is not valid
     */
    public JWS validateAccessRefreshToken(String token) {
        return validateRefreshToken(token, JWSType.ACCESS_REFRESH_TOKEN);
    }

    /**
     * Validate identity refresh token represented in JWS (JSON Web Signature) format.
     * Checks that:
     * - token has correct type and can be used for refreshing identity token
     * - token is not expired
     * - token is valid
     * @param token identity refresh token to validate
     * @return token in JWS format
     * @throws InvalidTokenException when token is not valid
     * @throws ExpiredTokenException when token is expired
     * @throws InvalidTokenTypeException when type of token is not valid
     */
    public JWS validateIdentityRefreshToken(String token) {
        return validateRefreshToken(token, JWSType.IDENTITY_REFRESH_TOKEN);
    }

    public JWS validateMasterToken(String token) {
        JWS jws = parse(token);

        validateType(jws, JWSType.MASTER_TOKEN);
        validateExpiration(jws);

        if (!NewJWSSubject.TOKENS.equalsIgnoreCase(jws.getPayload().getSub())) {
            throw new InvalidScopeException("Scope of master token is not valid.");
        }

        String component = jws.getPayload().getAud().getComponent();
        if (!JWS.JWSAudience.ALLOWED_COMPONENTS.contains(component)) {
            throw new InvalidTokenException(String.format("The '%s' is not an allowed component.", component));
        }

        validateSignature(token, jws.getHeader().getX5t());

        return jws;
    }

    private JWS validateRefreshToken(String token, JWSType type) {
        if (JWSType.ACCESS_REFRESH_TOKEN != type && JWSType.IDENTITY_REFRESH_TOKEN != type) {
            throw new IllegalArgumentException(String.format("Specified token's type is not a 'refresh' type: %s", type));
        }

        JWS jws = parse(token);

        validateType(jws, type);
        validateExpiration(jws);
        validateSignature(token, jws.getHeader().getX5t());

        return jws;
    }

    private JWS parse(String token) {
        if (Utils.isBlank(token)) {
            throw new InvalidTokenException("Token empty.");
        }

        try {
            return jwsService.parse(token);
        } catch (IOException | TokenFormatException e) {
            throw new InvalidTokenException("Token cannot be parsed.", e);
        }
    }

    private void validateType(JWS jws, JWSType expectedType) {
        String type = jws.getPayload().getTyp();

        if (!expectedType.getValue().equalsIgnoreCase(type)) {
            throw new InvalidTokenTypeException("Token has invalid type.");
        }
    }

    private void validateExpiration(JWS jws) {
        long currentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();//TODO: use synchronized time

        if (jws.getPayload().getExp() < currentTime) {
            throw new ExpiredTokenException("Token expired.");
        }
    }

    private void validateScope(JWS jws, String resourceId) {
        NewJWSSubject subject = NewJWSSubject.parse(jws.getPayload().getSub());

        if (!subject.containsResource(resourceId)) {
            throw new InvalidScopeException("Scope of access token not suitable for accessing specified resource.");
        }
    }

    private void validateSignature(String token, String thumbprint) {
        X509Certificate certificate = CERTIFICATES.get(thumbprint);
        if (certificate == null) {
            throw new InvalidTokenException("Token cannot be verified.");
        }

        Signature signature = createInitializedSignature(certificate, thumbprint);
        try {
            if (!jwsService.verify(signature, token)) {
                throw new InvalidTokenException("Token invalid.");
            }
        } catch (SignatureException e) {
            throw new InvalidTokenException("Token invalid. Exception occurred during signature verification.", e);
        }
    }

    Signature createInitializedSignature(X509Certificate certificate, String thumbprint) {
        try {
            Signature signature = Signature.getInstance(certificate.getSigAlgName());
            signature.initVerify(certificate);

            return signature;
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("Signature for the specified certificate can't be created. Cert's thumbprint: %s", thumbprint), e);
        }
    }

    static Map<String, X509Certificate> getCertificates() {
        return CERTIFICATES;
    }

}