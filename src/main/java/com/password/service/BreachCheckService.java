package com.password.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class BreachCheckService {

    private static final String HIBP_API_URL = "https://api.pwnedpasswords.com/range/";

    public boolean isPasswordPwned(String password) {
        try {
            String sha1Hash = getSHA1(password);
            String prefix = sha1Hash.substring(0, 5);
            String suffix = sha1Hash.substring(5);

            System.out.println("Checking password breach - Prefix: " + prefix + ", Suffix: " + suffix.substring(0, 5) + "...");

            URL url = new URL(HIBP_API_URL + prefix);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("User-Agent", "Password-Validator-App");
            conn.setRequestProperty("Add-Padding", "true");

            int responseCode = conn.getResponseCode();
            System.out.println("API Response Code: " + responseCode);
            
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                String suffixUpper = suffix.toUpperCase();

                // Читаем построчно и проверяем сразу, не накапливая весь ответ
                while ((inputLine = in.readLine()) != null) {
                    // Каждая строка формата: HASH_SUFFIX:COUNT
                    String hashPart = inputLine.split(":")[0].toUpperCase();
                    if (hashPart.equals(suffixUpper)) {
                        in.close();
                        System.out.println("Password found in breach: true");
                        return true;
                    }
                }
                in.close();
                
                System.out.println("Password found in breach: false");
                return false;
            }
            System.out.println("Non-200 response code, returning false");
            return false;
        } catch (Exception e) {
            // В случае ошибки API возвращаем false (не можем проверить)
            System.err.println("Breach check failed: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private String getSHA1(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }
}
