package by.bsu.zakharankou.restservices.service.serviceapi.token;

import by.bsu.zakharankou.restservices.common.StringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class RefreshTokenDetails {

    private String refreshToken;

    @JsonDeserialize(using = StringDeserializer.class)
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
