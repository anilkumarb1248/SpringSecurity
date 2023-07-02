package oauth.keycloack.client.credentials.flow.microservice3.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Map;

/**
 *  The GatewayProperties class is used to create Gateway objects, which is triggered from GatewaysConfiguration class.
 *  Note: Here the 'gateway' property name should match the property name under client.config.[gateways] in application.yml file
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "client.config")
public class GatewayProperties {

    private Map<String, Gateway> gateways;

    public GatewayProperties(Map<String, Gateway> gateways){
        this.gateways = gateways;
    }

    public Map<String, Gateway> getGateways(){
        return gateways;
    }
}
