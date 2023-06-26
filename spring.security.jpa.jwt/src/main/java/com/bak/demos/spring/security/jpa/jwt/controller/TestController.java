package com.bak.demos.spring.security.jpa.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/data")
    public ResponseEntity<String> getData(){
        return ResponseEntity.ok("Test Data");
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUserData(){
        return ResponseEntity.ok("Test User Data");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdminData(){
        return ResponseEntity.ok("Test Admin Data");
    }
}
