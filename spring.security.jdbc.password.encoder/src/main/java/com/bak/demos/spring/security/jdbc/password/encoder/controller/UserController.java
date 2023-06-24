package com.bak.demos.spring.security.jdbc.password.encoder.controller;

import com.bak.demos.spring.security.jdbc.password.encoder.dto.User;
import com.bak.demos.spring.security.jdbc.password.encoder.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<User> getUserWithPassword(@PathVariable String userName){
        return ResponseEntity.ok(userService.getUserWithPassword(userName));
    }
}
