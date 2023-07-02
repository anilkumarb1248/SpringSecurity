package oauth.keycloack.client.credentials.flow.microservice3.controller;

import oauth.keycloack.client.credentials.flow.microservice3.service.Service2CallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Rest Controller which is having only one endpoint to make a call Resource Server
 */
@RestController
@RequestMapping
public class HomeController {

    private final Service2CallerService service2Caller;

    @Autowired
    public HomeController(Service2CallerService service2Caller) {
        this.service2Caller = service2Caller;
    }

    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        String message = "Welcome to Home";
        String service2Msg = service2Caller.callService2();

        return ResponseEntity.ok(message + ", Message from Service2: " + service2Msg);
    }
}
