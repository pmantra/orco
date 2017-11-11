package com.orco.service;

import com.orco.utils.LoginUtil;
import com.orco.dao.UserRepository;
import com.orco.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public boolean login(String username, String password) throws Exception {
        User savedUser = getSavedUser(username);
        if(savedUser != null) {
            byte[] savedUserPassword = savedUser.getEncryptedPassword();
            byte[] salt = savedUser.getSalt();
            byte[] encryptedPassword = LoginUtil.getEncryptedPassword(password,salt);
            return Arrays.equals(encryptedPassword, savedUserPassword);
        }return false;
    }

    public boolean registerUser(String username, String password) throws Exception {
        if(getSavedUser(username) == null) {
            byte[] salt = LoginUtil.getGeneratedSalt();
            byte[] encryptedPassword = LoginUtil.getEncryptedPassword(password,salt);
            saveUser(username,encryptedPassword,salt);
            return true;
        } else{
            //todo
            throw new Exception("Username already exists!");
        }
    }

    private void saveUser(String username, byte[] encryptedPassword, byte[] salt) {
        userRepository.save(new User(username,encryptedPassword,salt));
    }

    private User getSavedUser(String username) {
        return userRepository.findByUsername(username);
    }

}
