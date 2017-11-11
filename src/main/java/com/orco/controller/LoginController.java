package com.orco.controller;

import com.orco.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(value = "/auth/")
@RestController
public class LoginController {

    @Autowired
    private LoginService service;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean registerUser(@RequestParam final String username, @RequestParam final String password) {
        try {
            return service.registerUser(username,password);
        } catch (Exception e) {
            //todo
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean loginUser(@RequestBody Map<String,Map<String,String>> data) {
        try {
            Map<String, String> loginData = data.get("data");
            return service.login(loginData.get("username"),loginData.get("password"));
        } catch (Exception e) {
            //todo
            e.printStackTrace();
            return false;
        }
    }
}
