package com.bak.demos.spring.security.jdbc.password.encoder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/test")
public class TestController {

    @GetMapping("/data")
    public ResponseEntity<String> getTestData() {
        return new ResponseEntity<>("Test data", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUserTestData() {
        return new ResponseEntity<>("User Test data", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdminTestData() {
        return new ResponseEntity<>("Admin Test data", HttpStatus.OK);
    }


}
