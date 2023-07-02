package com.bak.demos.spring.security.oauth.google.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Welcome to Spring Security OAuth Google platform Application");
    }

    @GetMapping("/user")
    public ResponseEntity<Principal> getLoggedInUserName(Principal principal){
        return ResponseEntity.ok(principal);
    }

}
