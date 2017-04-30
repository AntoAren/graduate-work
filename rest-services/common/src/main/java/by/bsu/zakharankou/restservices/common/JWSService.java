package by.bsu.zakharankou.restservices.common;

import by.bsu.zakharankou.restservices.common.JWS.JWSAudience;
import by.bsu.zakharankou.restservices.common.JWS.JWSHeader;
import by.bsu.zakharankou.restservices.common.JWS.JWSPayload;
import by.bsu.zakharankou.restservices.common.JWS.JWSSubject;
import by.bsu.zakharankou.restservices.common.JWS.JWSType;
import by.bsu.zakharankou.restservices.common.JWS.Views;

import static by.bsu.zakharankou.restservices.common.Utils.MAPPER;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.security.Signature;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Service with methods to work with token in JWS format.
 * Contains methods for token creation, verification, parsing etc.
 */
public class JWSService {

    private static final JWSService INSTANCE = new JWSService();

    static final String TOKEN_PARTS_SEPARATOR = ".";

    static final String TOKEN_PARTS_SEPARATOR_REG_EXP = "\\.";

    private static final String UTF_8 = "UTF-8";

    private JWSService() {
    }

    public static JWSService getInstance() {
        return INSTANCE;
    }

    /**
     * Generate a pair of signed access token and appropriate refresh token in JWS format.
     * @param jwsType token type to generate
     * @param refreshTokenJwsType related refresh token type
     * @param signature signature which should be used for signing token, it has to be initialized for signing
     * @param thumbprint thumbprint of X.509 certificate which should be used for verification of token
     * @param audience token's audience
     * @param lifetime tokens's lifetime in milliseconds
     * @return a pair of access token and appropriate refresh token
     * @throws IOException if I/O exception occurs during generation of token
     * @throws SignatureException if this signature object is not initialized properly
     * or if this signature algorithm is unable to process the input data provided.
     */
    public TokensPair generateTokensPair(JWSType jwsType, JWSType refreshTokenJwsType, Signature signature, String thumbprint, JWSAudience audience, long lifetime) throws IOException, SignatureException {
        JWSHeader header = createHeader(signature.getAlgorithm(), thumbprint);
        JWSPayload payload = createPayload(audience, jwsType, lifetime);

        String accessToken = generateToken(header, payload, signature);

        payload.setTyp(refreshTokenJwsType.getValue());
        payload.setJti(UUID.randomUUID().toString());
        String refreshToken = generateToken(header, payload, signature);

        return new TokensPair(accessToken, refreshToken);
    }

    /**
     * Verify token in JWS format by using the specified signature.
     * @param signature signature to use for verifying the token, it has to be initialized for verification
     * @param token token to verify
     * @return true if token was verified, otherwise - false
     * @throws SignatureException if this signature object is not initialized properly,
     * the passed-in signature is improperly encoded or of the wrong type,
     * if this signature algorithm is unable to process the input data provided, etc.
     */
    public boolean verify(Signature signature, String token) throws SignatureException {
        String[] tokenParts = token.split(TOKEN_PARTS_SEPARATOR_REG_EXP);

        if (tokenParts.length != 3) {
            return false;
        }

        byte[] signInput = (tokenParts[0] + TOKEN_PARTS_SEPARATOR + tokenParts[1]).getBytes(US_ASCII);
        signature.update(signInput);
        byte[] sign = Base64.decodeBase64(tokenParts[2]);

        return signature.verify(sign);
    }

    /**
     * Parse token in JWS format.
     * @param token token to parse
     * @return instance of {@link JWS}
     * @throws IOException if I/O exception occurs during parsing
     * @throws TokenFormatException if token is not in correct format
     */
    public JWS parse(String token) throws IOException, TokenFormatException {
        String[] tokenParts = token.split(TOKEN_PARTS_SEPARATOR_REG_EXP);

        if (tokenParts.length != 3) {
            throw new TokenFormatException("Token should contain three parts.");
        }

        byte[] headerBytes = Base64.decodeBase64(tokenParts[0]);
        byte[] payloadBytes = Base64.decodeBase64(tokenParts[1]);

        JWSHeader header = MAPPER.readValue(new String(headerBytes, UTF_8), JWSHeader.class);
        JWSPayload payload = MAPPER.readValue(new String(payloadBytes, UTF_8), JWSPayload.class);


        return new JWS(header, payload);
    }

    private String generateToken(JWSHeader jwsHeader, JWSPayload jwsPayload, Signature signature) throws IOException, SignatureException {
        return generateToken(jwsHeader, jwsPayload, signature, null);
    }

    private String generateToken(JWSHeader jwsHeader, JWSPayload jwsPayload, Signature signature, Class serializationView) throws IOException, SignatureException {
        StringBuilder result = new StringBuilder();
        ObjectWriter viewWriter = (serializationView == null) ? MAPPER.writerWithView(Views.AccessToken.class) : MAPPER.writerWithView(serializationView);

        byte[] headerBytes = viewWriter.writeValueAsBytes(jwsHeader);
        byte[] payloadBytes = viewWriter.writeValueAsBytes(jwsPayload);

        result.append(Base64.encodeBase64String(headerBytes))
                .append(TOKEN_PARTS_SEPARATOR)
                .append(Base64.encodeBase64String(payloadBytes));

        byte[] signInput = result.toString().getBytes(US_ASCII);
        signature.update(signInput);

        byte[] signatureBytes = signature.sign();
        result.append(TOKEN_PARTS_SEPARATOR).append(Base64.encodeBase64String(signatureBytes));

        return result.toString();
    }

    private JWSHeader createHeader(String algorithm, String thumbprint) {
        JWSHeader header = new JWSHeader();
        header.setTyp("JOSE");
        header.setCty("json");
        header.setAlg(algorithm);
        header.setX5t(thumbprint);

        return header;
    }

    private JWSPayload createPayload(JWSAudience audience, JWSType type, long lifetime) {
        JWSPayload payload = new JWSPayload();
        payload.setIss("appsngen");
        payload.setIat(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
        payload.setExp(payload.getIat() + lifetime);
        payload.setJti(UUID.randomUUID().toString());
        payload.setAud(audience);
        payload.setTyp(type.getValue());

        return payload;
    }

    public static class TokensPair {
        private final String token;

        private final String refreshToken;

        public TokensPair(String token, String refreshToken) {
            this.token = token;
            this.refreshToken = refreshToken;
        }

        public String getToken() {
            return token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
