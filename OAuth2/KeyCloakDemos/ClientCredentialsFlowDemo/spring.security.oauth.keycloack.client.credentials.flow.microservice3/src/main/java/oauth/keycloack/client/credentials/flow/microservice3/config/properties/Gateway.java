package oauth.keycloack.client.credentials.flow.microservice3.config.properties;

/**
 * The Gateway class is used to store all the Authorization Server details like client id, client secret and token url ..etc.
 * Note: Gateway class properties names should be same as application.yml file keys names under "client.config.gateways.<authorization-server>"
 */
public class Gateway {
    private String clientId;
    private String clientSecret;
    private String tokenEndPoint;
    private String clientScope;
    private String grantType;
    private String serviceName;

    public Gateway(){
    }

    public Gateway(String clientId, String clientSecret, String tokenEndPoint,
                   String clientScope, String grantType, String serviceName) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenEndPoint = tokenEndPoint;
        this.clientScope = clientScope;
        this.grantType = grantType;
        this.serviceName = serviceName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getTokenEndPoint() {
        return tokenEndPoint;
    }

    public void setTokenEndPoint(String tokenEndPoint) {
        this.tokenEndPoint = tokenEndPoint;
    }

    public String getClientScope() {
        return clientScope;
    }

    public void setClientScope(String clientScope) {
        this.clientScope = clientScope;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
