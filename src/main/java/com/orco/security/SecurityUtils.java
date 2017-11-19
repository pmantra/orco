package com.orco.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

class SecurityUtils {
    static final String SECRET = "TheSecretOfGraySkull";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/auth/signup";

    static String generateToken(String username) {
        return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(SignatureAlgorithm.HS512,SECRET.getBytes()).compact();
    }
}
