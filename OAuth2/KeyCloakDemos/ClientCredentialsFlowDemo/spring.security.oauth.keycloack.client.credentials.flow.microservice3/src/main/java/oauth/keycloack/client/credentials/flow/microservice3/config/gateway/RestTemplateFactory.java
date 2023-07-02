package oauth.keycloack.client.credentials.flow.microservice3.config.gateway;

import oauth.keycloack.client.credentials.flow.microservice3.interceptors.RequestHeadersInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateFactory.class);

    private final GatewayFactory gatewayFactory;

    public RestTemplateFactory(GatewayFactory gatewayFactory) {
        this.gatewayFactory = gatewayFactory;
    }

    public RestTemplate getAspectLoggingRestTemplate(String gatewayName) {
        return gatewayFactory.getAspectLoggingRestTemplate(getBaseBuilder(), gatewayName);
    }

    public RestTemplateBuilder getBaseBuilder() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        try {
            // Here it is returning new builder while adding request factory and interceptors.
            builder = builder.requestFactory(HttpComponentsClientHttpRequestFactory::new);
            builder = builder.additionalInterceptors(new RequestHeadersInterceptor());
            //messageConverters() // here we can add Message converters
        } catch (Exception e) {
            LOGGER.error("Exception occurred in RestTemplateFactory while creating RestTemplateBuilder, ex: {}", e.getMessage());
        }
        return builder;
    }

    public String getEndPoint(String gatewayName) {
        return gatewayFactory.getEndpoint(gatewayName);
    }
}
