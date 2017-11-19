package com.orco.controller;

import com.orco.service.LoginService;
import com.orco.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RequestMapping(value = "/auth/")
@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private LoginService service;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping(value = "/signup", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void registerUser(@RequestBody Map<String,Map<String,String>> data) {
        logger.info("invoke user sign-up");
        try {
            Map<String, String> loginData = data.get("data");
            String username = loginData.get("username");
            String password = loginData.get("password");

            logger.info(username==null?"username is null":"username is "+username);
            logger.info(password==null?"password is null":"password was entered");

            if(username == null || password == null) {
                throw new ServletException("Please enter username and password");
            }

            service.registerUser(username, password);
        } catch (Exception e) {
            logger.error("Error during user sign-up! ",e);
        }
    }

    @GetMapping(produces = "application/json", value = "/userInfo/{username}")
    @ResponseBody
    public UserDetails getUserInfo(@PathVariable String username) {
        if(username == null) return null;
        logger.info("invoke user info for username ", username);
        return userDetailsServiceImpl.loadUserByUsername(username);
    }
}
