package oauth.keycloack.client.credentials.flow.microservice3.service;

import oauth.keycloack.client.credentials.flow.microservice3.config.gateway.RestTemplateFactory;
import oauth.keycloack.client.credentials.flow.microservice3.config.gateway.ServiceCallerErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class Service2CallerService extends BaseServiceCaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Service2CallerService.class);
    private final String host;
    private final String helloEndpoint;

    @Autowired
    public Service2CallerService(@Value("${client.config.keycloak.host}") String host,
                                 @Value("${client.config.keycloak.hello-endpoint}") String helloEndpoint,
                                 RestTemplateFactory restTemplateFactory,
                                 ServiceCallerErrorHandler serviceCallerErrorHandler) {

        super(restTemplateFactory, serviceCallerErrorHandler);
        this.host = host;
        this.helloEndpoint = helloEndpoint;
    }

    public String callService2() {
        String message = "Failed to make service 2 call";
        try {
            // We need a RestTemplate which is having the Interceptors to make a call to Authorization Server and get the access_token.
            RestTemplate restTemplate = getRestTemplate("keycloak");
            HttpEntity httpEntity = new HttpEntity(buildHeaders());

            ResponseEntity<String> responseEntity = restTemplate.exchange(host + helloEndpoint, HttpMethod.GET, httpEntity, String.class);
            if(HttpStatus.OK == responseEntity.getStatusCode() && responseEntity.getBody() != null){
                message = responseEntity.getBody();
            }
        } catch(HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound e){
            LOGGER.error("HttpClientErrorException occurred while calling service 2: {} ", e.getMessage());
            message = "HttpClientErrorException occurred while calling service 2:  " + e.getMessage();
        } catch (RestClientException e){
            LOGGER.error("RestClientException occurred while calling service 2: {} ", e.getMessage());
            message = "RestClientException occurred while calling service 2:  " + e.getMessage();
        } catch (Exception e) {
            LOGGER.error("Exception occurred while calling service 2: {} ", e.getMessage());
            message = "Exception occurred while calling service 2:  " + e.getMessage();
        }
        return message;
    }

}
