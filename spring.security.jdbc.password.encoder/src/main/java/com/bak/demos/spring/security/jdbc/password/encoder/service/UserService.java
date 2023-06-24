package com.bak.demos.spring.security.jdbc.password.encoder.service;

import com.bak.demos.spring.security.jdbc.password.encoder.dao.UserDAO;
import com.bak.demos.spring.security.jdbc.password.encoder.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public String registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(userDAO.addUser(user)){
            return "User created successfully";
        }else{
            return "Failed to create the User with user name: " + user.getUserName();
        }
    }

    public User getUserWithPassword(String userName) {
        try{
            return userDAO.getUser(userName);
        }catch (Exception e){
            System.out.println("Exception occurred while retrieving the user with user name: " + userName);
            return new User();
        }
    }

}
