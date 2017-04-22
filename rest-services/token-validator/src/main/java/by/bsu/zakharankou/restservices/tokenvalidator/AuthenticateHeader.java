package by.bsu.zakharankou.restservices.tokenvalidator;

import by.bsu.zakharankou.restservices.common.Utils;
import static by.bsu.zakharankou.restservices.common.Utils.*;

/**
 * Helper class for constructing value of "WWW-Authenticate" header.
 */
public class AuthenticateHeader {
    
    private static final String REALM = "realm";
    
    private static final String ERROR = "error";
    
    private static final String ERROR_DESCRIPTION = "error_description";   
    
    private String authScheme;
    
    private String realm;
    
    private String error;
    
    private String errorDescription;

    public AuthenticateHeader(String authScheme) {
        if (isBlank(authScheme)) {
            throw new NullPointerException("Auth scheme can't be empty.");
        }
        
        this.authScheme = authScheme.trim();
    }        
    
    public AuthenticateHeader realm(String realm) {
        this.realm = realm;
        return this;
    }
    
    public AuthenticateHeader error(String error) {
        this.error = error;
        return this;
    }
    
    public AuthenticateHeader errorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }
    
    private void append(StringBuilder builder, String name, String value) {
        if (!Utils.isBlank(value)) {
            builder.append(" ").append(name).append("=\"").append(value).append("\",");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(authScheme);
        
        append(builder, REALM, this.realm);
        append(builder, ERROR, this.error);
        append(builder, ERROR_DESCRIPTION, this.errorDescription);   
        
        String result = builder.toString();
        
        //remove comma at the end
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        
        return result;
    }        
}
