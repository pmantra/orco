package com.coffeeki.orco.Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class LoginUtil {

    private static final Integer PBKDF2_DERIVED_LENGTH = 32;
    private static final String PBKDF2_INSTANCE = "PBKDF2WithHmacSHA256";
    private static final Integer PBKDF2_ITERATIONS = 100000;

    public static byte[] getEncryptedPassword(String password, byte[] salt) throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, PBKDF2_DERIVED_LENGTH);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance(PBKDF2_INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        }
        return factory.generateSecret(keySpec).getEncoded();
    }

    public static byte[] getGeneratedSalt() {
        Random random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
