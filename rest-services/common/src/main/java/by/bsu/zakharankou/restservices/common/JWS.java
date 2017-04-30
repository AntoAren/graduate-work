package by.bsu.zakharankou.restservices.common;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.*;

/**
 * Class describes JSON Web Signature token format.
 */
public class JWS {

    /**
     * JWS header. The members of the JSON object(s) representing the JWS Header describe the digital signature to the token.
     */
    private JWSHeader header;

    /**
     * JWS Payload The sequence of octets to be secured -- a.k.a., the message.
     */
    private JWSPayload payload;

    public JWS(JWSHeader header, JWSPayload payload) {
        this.header = header;
        this.payload = payload;
    }

    public JWSHeader getHeader() {
        return header;
    }

    public JWSPayload getPayload() {
        return payload;
    }

    public static class JWSHeader {

        /**
         * Identifies the cryptographic algorithm used to secure the JWS.
         */
        private String alg;

        /**
         * Declares the MIME Media Type of this complete JWS object in contexts where this is useful to the application.
         */
        private String typ;

        /**
         * Declares the MIME Media Type of the secured content (the payload) in contexts where this is useful to the application.
         */
        private String cty;

        /**
         * Base64url encoded thumbprint (a.k.a. digest) of the DER encoding of the X.509 certificate corresponding to the key used to digitally sign the JWS.
         */
        private String x5t;

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }

        public String getTyp() {
            return typ;
        }

        public void setTyp(String typ) {
            this.typ = typ;
        }

        public String getCty() {
            return cty;
        }

        public void setCty(String cty) {
            this.cty = cty;
        }

        public String getX5t() {
            return x5t;
        }

        public void setX5t(String x5t) {
            this.x5t = x5t;
        }
    }

    public static class JWSPayload {

        /**
         * Identifies the principal that issued the token.
         */
        private String iss;

        /**
         * Identifies the principal that is the subject of the token.
         */
        private String sub;

        /**
         * Identifies the expiration time on or after which the token MUST NOT be accepted for processing.
         * Time in UTC format.
         */
        private long exp;

        /**
         * Identifies the time at which the token was issued.
         * This claim can be used to determine the age of the token.
         */
        private long iat;

        /**
         * Unique identifier for the token.
         */
        private String jti;

        /**
         * Identifies the recipients that the token is intended for.
         */
        private JWSAudience aud;

        /**
         * Identifies the type of the token.
         */
        private String typ;

        public JWSPayload() {
        }

        public JWSPayload(String sub, long exp, long iat, String jti, JWSAudience aud, String typ) {
            this.iss = "appsngen";
            this.sub = sub;
            this.exp = exp;
            this.iat = iat;
            this.jti = jti;
            this.aud = aud;
            this.typ = typ;
        }

        public String getIss() {
            return iss;
        }

        public void setIss(String iss) {
            this.iss = iss;
        }

        public String getSub() {
            return sub;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }

        public long getExp() {
            return exp;
        }

        public void setExp(long exp) {
            this.exp = exp;
        }

        public long getIat() {
            return iat;
        }

        public void setIat(long iat) {
            this.iat = iat;
        }

        public String getJti() {
            return jti;
        }

        public void setJti(String jti) {
            this.jti = jti;
        }

        public void setAud(JWSAudience aud) {
            this.aud = aud;
        }

        public JWSAudience getAud() {
            return aud;
        }

        public void setTyp(String typ) {
            this.typ = typ;
        }

        public String getTyp() {
            return typ;
        }
    }

    public static class JWSSubject {

        public static final String TOKENS = "tokens";

        public static final String SPACE = " ";

        /**
         * Parse string representation of subject and return appropriate instance of {@link JWSSubject}
         * @param subject string representation of the jws subject
         * @return instance of {@link JWSSubject}
         */
        public static JWSSubject parse(String subject) {
            JWSSubject result = new JWSSubject();

            if (Utils.isBlank(subject)) {
                return result;
            }

            String[] subjectItems = subject.split(SPACE);
            for (String item : subjectItems) {
                if (Utils.isBlank(item)) {
                    continue;
                }
            }

            return result;
        }

        @Override
        public String toString() {

            StringBuilder builder = new StringBuilder();

            //remove last separator
            if (builder.length() > 0) {
                int lastIndex = builder.length() - 1;
                builder.delete(lastIndex, lastIndex + SPACE.length());
            }

            return builder.toString();
        }
    }

    public static class JWSAudience {

        private String user;

        private Set<String> userRoles = Collections.emptySet();

        public void setUser(String user) {
            this.user = user;
        }

        @JsonView(Views.AccessToken.class)
        public String getUser() {
            return user;
        }

        public void setUserRoles(Set<String> userRoles) {
            this.userRoles = userRoles;
        }

        @JsonView(Views.AccessToken.class)
        public Set<String> getUserRoles() {
            return userRoles;
        }
    }

    /**
     * Describes different token types.
     * Type describes purpose of use of the token. Depending on the type
     * token could be used for retrieving access for some resources or for refreshing another token.
     */
    public static enum JWSType {
        IDENTITY_TOKEN("it"), IDENTITY_REFRESH_TOKEN("irt");

        private final String value;

        private JWSType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Views {
        public static class MasterToken {}
        public static class AccessToken {}
    }
}
