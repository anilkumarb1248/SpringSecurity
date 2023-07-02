package oauth.keycloack.client.credentials.flow.microservice3.config.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 *  The GatewaysConfiguration configuration class is used build the GatewayProperties,
 *  the values will be taken form the application.yml file.
 *  Note: The properties names must match the keys names in yml file.
 */
@Configuration
@EnableConfigurationProperties({GatewayProperties.class})
public class GatewaysConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewaysConfiguration.class);

    @Bean
    public Map<String, Gateway> gatewaysByGatewayName(GatewayProperties gatewayProperties) {
        if (gatewayProperties != null && gatewayProperties.getGateways() != null && gatewayProperties.getGateways().size() > 0) {
            LOGGER.info("gateway properties size: {}", gatewayProperties.getGateways().size());
        } else{
            LOGGER.warn("No GatewayProperties found");
        }
        return gatewayProperties.getGateways();
    }

}
