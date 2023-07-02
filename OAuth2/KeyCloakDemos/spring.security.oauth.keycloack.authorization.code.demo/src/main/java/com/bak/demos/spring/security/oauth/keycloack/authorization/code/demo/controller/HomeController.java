package com.bak.demos.spring.security.oauth.keycloack.authorization.code.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * To work with thymeleaf we need to use @Controller.
 * If user @RestController the app won't behave as MVC application and simply returns "login"
 * or "home" string to the browser. not loading the actual template using thymeleaf
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

}
