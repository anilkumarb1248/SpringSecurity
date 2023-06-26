package com.bak.demos.spring.security.jpa.jwt.controller;

import com.bak.demos.spring.security.jpa.jwt.model.AuthenticationRequest;
import com.bak.demos.spring.security.jpa.jwt.model.AuthenticationResponse;
import com.bak.demos.spring.security.jpa.jwt.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JWTUtil jWTUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserDetailsService userDetailsService, JWTUtil jWTUtil){
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jWTUtil = jWTUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationJwtToken(@RequestBody AuthenticationRequest request){

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity("User name or password not correct", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            LOGGER.error("Exception occurred while validating the user: {} ", e.getMessage());
            return new ResponseEntity("Exception occurred while validating the user:  "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = jWTUtil.createJwtToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
