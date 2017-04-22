package by.bsu.zakharankou.restservices.common;

/**
 * Defines types of access token.
 */
public enum TokenType {

    BEARER("Bearer", "Bearer");

    private String typeName;

    private String authScheme;

    TokenType(String typeName, String authScheme) {
        this.typeName = typeName;
        this.authScheme = authScheme;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getAuthScheme() {
        return authScheme;
    }

}
