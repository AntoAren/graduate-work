package by.bsu.zakharankou.restservices.model.token;

public class IdentityToken {
    private String identityToken;

    private String tokenType;

    private String refreshToken;

    private long expiresIn;

    public IdentityToken(String token, String tokenType, String refreshToken, long expiresIn) {
        super();
        this.identityToken = token;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getIdentityToken() {
        return identityToken;
    }

    public void setIdentityToken(String token) {
        this.identityToken = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
