package com.password.service;

import com.password.dto.GenerationRequest;
import com.password.dto.GenerationResponse;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGeneratorService {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    
    private final SecureRandom random = new SecureRandom();

    public GenerationResponse generatePassword(GenerationRequest request) {
        int length = request.getLength() != null ? request.getLength() : 12;
        boolean includeLowercase = request.getIncludeLowercase() != null ? request.getIncludeLowercase() : true;
        boolean includeUppercase = request.getIncludeUppercase() != null ? request.getIncludeUppercase() : true;
        boolean includeDigits = request.getIncludeDigits() != null ? request.getIncludeDigits() : true;
        boolean includeSpecial = request.getIncludeSpecial() != null ? request.getIncludeSpecial() : false;

        StringBuilder charPool = new StringBuilder();
        if (includeLowercase) charPool.append(LOWERCASE);
        if (includeUppercase) charPool.append(UPPERCASE);
        if (includeDigits) charPool.append(DIGITS);
        if (includeSpecial) charPool.append(SPECIAL);

        if (charPool.length() == 0) {
            charPool.append(LOWERCASE);
        }

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charPool.length());
            password.append(charPool.charAt(index));
        }

        GenerationResponse response = new GenerationResponse();
        response.setPassword(password.toString());
        return response;
    }
}
