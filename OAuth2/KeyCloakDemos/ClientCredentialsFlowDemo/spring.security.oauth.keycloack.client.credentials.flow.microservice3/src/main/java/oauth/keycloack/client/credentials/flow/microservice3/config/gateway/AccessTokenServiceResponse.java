package oauth.keycloack.client.credentials.flow.microservice3.config.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AccessTokenServiceResponse is used to store all the token related values like access token,
 * refresh token, id token and expires time
 */
public class AccessTokenServiceResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("id_token")
    private String idToken;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("refresh_expires_in")
    private String refreshExpiresIn;

    @JsonProperty("scope")
    private String scope;

    private long tokenExpiresAt = 0;
    private long refreshTokenExpiresAt = 0;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
        long expireSeconds = Long.parseLong(expiresIn);
        this.setTokenExpiresAt(System.currentTimeMillis() + (1000 * expireSeconds) - 60000);
    }

    public String getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(String refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
        long expireSeconds = Long.parseLong(refreshExpiresIn);
        this.setRefreshTokenExpiresAt(System.currentTimeMillis() + (1000 * expireSeconds) - 60000);
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getTokenExpiresAt() {
        return tokenExpiresAt;
    }

    public void setTokenExpiresAt(long tokenExpiresAt) {
        this.tokenExpiresAt = tokenExpiresAt;
    }

    public long getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(long refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }
}
