package com.orco.service;

import com.orco.dao.UserRepository;
import com.orco.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger logger = LogManager.getLogger(LoginService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(String username, String password) throws Exception {
        if(getSavedUser(username) == null) {
            saveUser(username,passwordEncoder.encode(password));
        } else{
            logger.error("username already exists "+username);
            throw new Exception("Username already exists!");
        }
    }

    private User getSavedUser(String username) {
        return userRepository.findByUsername(username);
    }

    private void saveUser(String username, String password) {
        User user = new User(username,password);
        userRepository.save(user);
    }
}
