package com.bak.demos.spring.security.jpa.jwt.config;

import com.bak.demos.spring.security.jpa.jwt.entity.User;
import com.bak.demos.spring.security.jpa.jwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDetails.class);

    private final UserRepository userRepository;

    @Autowired
    public AppUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(()-> {
            LOGGER.warn("User details not found with username: {}", username);
            throw new UsernameNotFoundException("User details not found with username: " + username);
        });

        UserDetails userDetails = user.map(AppUserDetails::new).get();
        LOGGER.info("User Details: ", userDetails);
        return userDetails;
    }
}
