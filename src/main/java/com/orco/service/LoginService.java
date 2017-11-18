package com.orco.service;

import com.orco.dao.UserRepository;
import com.orco.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.orco.security.SecurityUtils.BCRYPT_STRENGTH;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(String username, String password) throws Exception {
        if(getSavedUser(username) == null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCRYPT_STRENGTH);
            saveUser(username,passwordEncoder.encode(password));
            return true;
        } else{
            //todo
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
