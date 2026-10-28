package com.example.demo.services;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private byte[] derive(String password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 600000, 256);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (java.security.GeneralSecurityException e) {
            throw new IllegalStateException(e);
        } finally { spec.clearPassword(); }
    }
    public String hash(String password) {
        // Each password gets its own random salt. Never save the original password.
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return "pbkdf2$" + Base64.getEncoder().encodeToString(salt) + "$"
            + Base64.getEncoder().encodeToString(derive(password, salt));
    }
    public boolean matches(String password, String stored) {
        try {
            String[] parts = stored.split("\\$");
            return parts.length == 3 && parts[0].equals("pbkdf2") && MessageDigest.isEqual(
                derive(password, Base64.getDecoder().decode(parts[1])),
                Base64.getDecoder().decode(parts[2]));
        } catch (IllegalArgumentException | NullPointerException e) { return false; }
    }
}
