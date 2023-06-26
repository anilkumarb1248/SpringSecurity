package com.bak.demos.spring.security.jpa.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JWTUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);

    private String secret;
    private int jwtExpirationInMs;

    public JWTUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.jwtExpirationInMs}") int jwtExpirationInMs){
        this.secret = secret;
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String createJwtToken(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

        Map<String, Object> claims = new HashMap<>();
        if(roles.contains("ADMIN")){
            claims.put("role", "ADMIN");
        } else if(roles.contains("USER")){
            claims.put("role", "USER");
        }

        LOGGER.info("Creating the jwt token for username: {}", userName);

        return generateJwtToken(claims, userName);

    }

    private String generateJwtToken(Map<String, Object> claims, String userName) {
        Date currentTime = new Date(System.currentTimeMillis());
        Date expiryTime = new Date(System.currentTimeMillis() + jwtExpirationInMs);

        String jwtToken = Jwts.builder()
                .addClaims(claims)
                .setSubject(userName)
                .setIssuedAt(currentTime)
                .setExpiration(expiryTime)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        LOGGER.info("Created the jwt token for username: {}, jwtToken: {}", userName, jwtToken);

        return "Bearer " + jwtToken;
    }

    public boolean validateJwtToken(String authToken){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("Token has Expired");
        }
    }

    public String getUsernameFromToken(String authToken) {
        if(authToken != null && !"".equals(authToken.trim())){
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
            return claims.getSubject();
        }
        return null;
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String authToken) {
        List<SimpleGrantedAuthority> roles = null;
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
        String role = (String) claims.get("role");
        if("ADMIN".equalsIgnoreCase(role)){
            roles = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else if("USER".equalsIgnoreCase(role)){
            roles = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return roles;
    }
}
