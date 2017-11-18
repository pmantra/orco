package com.orco.controller;

import com.orco.service.LoginService;
import com.orco.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RequestMapping(value = "/auth/")
@RestController
public class UserController {

    @Autowired
    private LoginService service;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping(value = "/signup", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void registerUser(@RequestBody Map<String,Map<String,String>> data) {
        try {
            Map<String, String> loginData = data.get("data");
            String username = loginData.get("username");
            String password = loginData.get("password");
            if(username == null || password == null) {
                throw new ServletException("Please check username and password");
            }
            service.registerUser(username, password);
        } catch (Exception e) {
            //todo
            e.printStackTrace();
        }
    }

    @GetMapping(produces = "application/json", value = "/userInfo/{username}")
    @ResponseBody
    public UserDetails getUserInfo(@PathVariable String username) {
        return userDetailsServiceImpl.loadUserByUsername(username);
    }
}
