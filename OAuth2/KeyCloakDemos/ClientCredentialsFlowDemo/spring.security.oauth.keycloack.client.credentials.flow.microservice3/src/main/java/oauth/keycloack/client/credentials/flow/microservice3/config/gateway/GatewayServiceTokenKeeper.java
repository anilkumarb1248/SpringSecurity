package oauth.keycloack.client.credentials.flow.microservice3.config.gateway;

import oauth.keycloack.client.credentials.flow.microservice3.config.properties.Gateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *  The GatewayServiceTokenKeeper is used to hold the Gateway (Authorization Server details)
 *  and AccessTokenServiceResponse (token details).
 *  It is also used to create the access token or request the new access token if the current access token expires.
 */

public class GatewayServiceTokenKeeper {

    protected static final String CLIENT_ID_PARAM = "client_id";
    protected static final String CLIENT_SECRET_PARAM = "client_secret";
    protected static final String GRANT_TYPE_PARAM = "grant_type";
    protected static final String GRANT_TYPE_CLIENT_CREDENTIALS_PARAM = "client_credentials";
    protected static final String SCOPE_PARAM = "scope";
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServiceTokenKeeper.class);
    private final RestTemplate tokenCallRestTemplate;

    private final Gateway gateway;

    private AccessTokenServiceResponse accessTokenServiceResponse;

    public GatewayServiceTokenKeeper(Gateway gateway, RestTemplate tokenCallRestTemplate) {
        this.tokenCallRestTemplate = tokenCallRestTemplate;
        this.gateway = gateway;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public String getScopeForResource() {
        return gateway.getClientScope();
    }

    public synchronized AccessTokenServiceResponse getToken() {
        AccessTokenServiceResponse currentAccessToken = accessTokenServiceResponse;
        if (!tokenExpired(currentAccessToken)) {
            return currentAccessToken;
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.setBasicAuth(gateway.getClientId(), gateway.getClientSecret());

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add(GRANT_TYPE_PARAM, GRANT_TYPE_CLIENT_CREDENTIALS_PARAM);
        form.add(CLIENT_ID_PARAM, gateway.getClientId());
        form.add(CLIENT_SECRET_PARAM, gateway.getClientSecret());
        if (gateway.getClientScope() != null) {
            form.add(SCOPE_PARAM, gateway.getClientScope());
        }

        ResponseEntity<AccessTokenServiceResponse> responseEntity = tokenCallRestTemplate.exchange(
                gateway.getTokenEndPoint(), HttpMethod.POST,
                new HttpEntity<>(form, httpHeaders), AccessTokenServiceResponse.class);

        try {
            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
                accessTokenServiceResponse = responseEntity.getBody();
            } else {
                LOGGER.warn("Something went wrong while requesting the access token, got the empty body");
            }
        } catch (RestClientException e) {
            LOGGER.error("Exception occurred while getting the access token: {}" + e.getMessage());
        }

        return accessTokenServiceResponse;
    }

    private boolean tokenExpired(AccessTokenServiceResponse currentAccessToken) {
        return currentAccessToken == null || System.currentTimeMillis() >= currentAccessToken.getTokenExpiresAt();
    }

    public HttpHeaders getGatewayHeaders() {
        AccessTokenServiceResponse currentToken = getToken();
        return buildHttpHeaders(currentToken);
    }

    private HttpHeaders buildHttpHeaders(AccessTokenServiceResponse currentToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + currentToken.getAccessToken());
        return httpHeaders;
    }
}
