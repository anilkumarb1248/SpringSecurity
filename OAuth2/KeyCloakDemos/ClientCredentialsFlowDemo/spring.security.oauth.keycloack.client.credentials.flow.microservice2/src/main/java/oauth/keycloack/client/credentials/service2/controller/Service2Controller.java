package oauth.keycloack.client.credentials.service2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service2")
public class Service2Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Service2Controller.class);

    @GetMapping("/hello")
    public ResponseEntity<String> callService2WithRestTemplate(){

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info("JWT Token value in Service 2: {}", jwt.getTokenValue());

        return ResponseEntity.ok("Message from Service2: How are you Service1");
    }


}
