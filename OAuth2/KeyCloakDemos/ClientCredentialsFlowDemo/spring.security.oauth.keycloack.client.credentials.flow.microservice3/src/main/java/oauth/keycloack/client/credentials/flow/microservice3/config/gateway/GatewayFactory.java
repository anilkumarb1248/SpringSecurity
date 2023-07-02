package oauth.keycloack.client.credentials.flow.microservice3.config.gateway;

import oauth.keycloack.client.credentials.flow.microservice3.config.properties.Gateway;
import oauth.keycloack.client.credentials.flow.microservice3.interceptors.GatewayTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;

@Component
public class GatewayFactory {

    private final GenericApplicationContext genericApplicationContext;
    private final Map<String, Gateway> gateways;

    private final RestTemplate tokenRestTemplate = new RestTemplateBuilder().build();

    @Autowired
    public GatewayFactory(GenericApplicationContext genericApplicationContext, Map<String, Gateway> gateways) {
        this.genericApplicationContext = genericApplicationContext;
        this.gateways = gateways;
    }

    public RestTemplateBuilder addGatewayTokenSupport(RestTemplateBuilder builder, String gatewayName) {
        if (builder != null) {
            ClientHttpRequestInterceptor interceptor = getGatewayTokenInterceptor(gatewayName);
            if (interceptor != null) {
                builder = builder.additionalInterceptors(interceptor);
            }
        } else {
            throw new IllegalArgumentException("RestTemplateBuilder object should not be null while adding token support");
        }
        return builder;
    }

    public ClientHttpRequestInterceptor getGatewayTokenInterceptor(String gatewayName) {
        Gateway gateway = getGateway(gatewayName);
        GatewayServiceTokenKeeper gatewayServiceTokenKeeper = new GatewayServiceTokenKeeper(gateway, tokenRestTemplate);
        return new GatewayTokenInterceptor(gatewayServiceTokenKeeper);
    }

    public Gateway getGateway(String gatewayName) {
        if (gatewayName == null || "".equals(gatewayName.trim())) {
            throw new IllegalArgumentException("Gateway name should not be null");
        } else {
            Gateway gateway = gateways.get(gatewayName);
            if (gateway == null) {
                throw new IllegalArgumentException("Gateway with name: " + gatewayName + " is not exist");
            }
            return gateway;
        }
    }

    public RestTemplate getAspectLoggingRestTemplate(RestTemplateBuilder builder, String gatewayName) {
        Random random = new Random();
        String beanName = gatewayName + (random.nextInt(800) + 100);
        RestTemplate restTemplate = addGatewayTokenSupport(builder, gatewayName).build();
        genericApplicationContext.registerBean(beanName, RestTemplate.class, () -> restTemplate);

        return (RestTemplate) genericApplicationContext.getBean(beanName);
    }

    public String getEndpoint(String gatewayName) {
        return getGateway(gatewayName).getTokenEndPoint();
    }

    public boolean isValidGateway(String gatewayName) {
        return gateways.containsKey(gatewayName);
    }

}
