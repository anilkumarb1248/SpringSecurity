package oauth.keycloack.client.credentials.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/service1/caller")
public class ServiceCallerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCallerController.class);

    RestTemplate restTemplate = new RestTemplateBuilder().build();

    WebClient webClient = WebClient.builder().build();


    private static final String SERVICE2_HOST = "http://localhost:2051";
    private static final String SERVICE2_HELLO_ENDPOINT = "/service2/hello";

    @GetMapping("/restTemplate")
    public ResponseEntity<String> callService2WithRestTemplate() {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info("Service1 calling Service with JWT: {}", jwt.getTokenValue());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt.getTokenValue());

        ResponseEntity<String> service2Response = restTemplate.exchange(SERVICE2_HOST + SERVICE2_HELLO_ENDPOINT, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);

        return ResponseEntity.ok("Message from Service1: Hello RestTemplate, Service2 Message: " + service2Response.getBody());
    }

    @GetMapping("/webclient")
    public ResponseEntity<String> callService2WithWebClient() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String response = webClient.get().uri(SERVICE2_HOST + SERVICE2_HELLO_ENDPOINT)
                .headers(header -> header.setBearerAuth(jwt.getTokenValue()))
                .retrieve().bodyToMono(String.class)
                .block();

        return ResponseEntity.ok("Message from Service1: Hello Webclient, Service2 Message: " + response);
    }

}
