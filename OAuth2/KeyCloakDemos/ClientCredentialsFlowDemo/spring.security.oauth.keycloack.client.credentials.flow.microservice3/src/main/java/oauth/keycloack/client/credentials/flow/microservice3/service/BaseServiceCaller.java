package oauth.keycloack.client.credentials.flow.microservice3.service;

import oauth.keycloack.client.credentials.flow.microservice3.config.gateway.RestTemplateFactory;
import oauth.keycloack.client.credentials.flow.microservice3.config.gateway.ServiceCallerErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class BaseServiceCaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceCaller.class);

    // restTemplateMap is used to store all the RestTemplates based on the gateway name.
    private Map<String, RestTemplate> restTemplateMap = new HashMap<>();

    private RestTemplateFactory restTemplateFactory;
    private ServiceCallerErrorHandler serviceCallerErrorHandler;

    public BaseServiceCaller(RestTemplateFactory restTemplateFactory, ServiceCallerErrorHandler serviceCallerErrorHandler){
        this.restTemplateFactory = restTemplateFactory;
        this.serviceCallerErrorHandler = serviceCallerErrorHandler;
    }


    /**
     * This method is used to create a RestTemplate based on the gateway name.
     * Where restTemplateMap is used to store all the RestTemplates based on the gateway name once created.
     * If the RestTemplate is not available for any gateway, creating new or else returning the existed one.
     *
     * @param gatewayName
     * @return RestTemplate
     */
    protected synchronized RestTemplate getRestTemplate(String gatewayName){
        RestTemplate restTemplate = restTemplateMap.computeIfAbsent(gatewayName, k -> {
            RestTemplate rt = restTemplateFactory.getAspectLoggingRestTemplate(gatewayName);
            rt.setErrorHandler(serviceCallerErrorHandler);
            return rt;
        });
        return restTemplate;
    }

    protected HttpHeaders buildHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return  httpHeaders;
    }
}
