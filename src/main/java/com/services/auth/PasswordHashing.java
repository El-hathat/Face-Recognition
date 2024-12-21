package com.services.auth;

import com.services.ConfigServices;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHashing {

    // Generate a random salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[ConfigServices.SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash the password with salt using SHA-256
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(ConfigServices.HASH_ALGORITHM);
        String saltedPassword = salt + password;
        byte[] hash = md.digest(saltedPassword.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

}
