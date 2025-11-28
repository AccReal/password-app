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
    private static final String CYRILLIC_LOWER = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String CYRILLIC_UPPER = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String SIMILAR_CHARS = "0O1lI";
    
    private final SecureRandom random = new SecureRandom();
    private final StatisticsService statisticsService;

    public PasswordGeneratorService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    public GenerationResponse generatePassword(GenerationRequest request) {
        int length = request.getLength() != null ? request.getLength() : 12;
        boolean includeLowercase = request.getIncludeLowercase() != null ? request.getIncludeLowercase() : true;
        boolean includeUppercase = request.getIncludeUppercase() != null ? request.getIncludeUppercase() : true;
        boolean includeDigits = request.getIncludeDigits() != null ? request.getIncludeDigits() : true;
        boolean includeSpecial = request.getIncludeSpecial() != null ? request.getIncludeSpecial() : false;
        boolean excludeSimilar = request.getExcludeSimilar() != null ? request.getExcludeSimilar() : false;
        boolean includeCyrillic = request.getIncludeCyrillic() != null ? request.getIncludeCyrillic() : false;
        String excludeChars = request.getExcludeCharacters() != null ? request.getExcludeCharacters() : "";

        StringBuilder charPool = new StringBuilder();
        if (includeLowercase) charPool.append(LOWERCASE);
        if (includeUppercase) charPool.append(UPPERCASE);
        if (includeDigits) charPool.append(DIGITS);
        if (includeSpecial) charPool.append(SPECIAL);
        if (includeCyrillic) {
            charPool.append(CYRILLIC_LOWER);
            charPool.append(CYRILLIC_UPPER);
        }

        if (charPool.length() == 0) {
            charPool.append(LOWERCASE);
        }

        // Исключение похожих символов
        if (excludeSimilar) {
            for (char c : SIMILAR_CHARS.toCharArray()) {
                int index;
                while ((index = charPool.indexOf(String.valueOf(c))) != -1) {
                    charPool.deleteCharAt(index);
                }
            }
        }

        // Исключение пользовательских символов
        if (!excludeChars.isEmpty()) {
            for (char c : excludeChars.toCharArray()) {
                int index;
                while ((index = charPool.indexOf(String.valueOf(c))) != -1) {
                    charPool.deleteCharAt(index);
                }
            }
        }

        String password = generateWithRules(charPool.toString(), length, request);

        statisticsService.incrementGenerated();

        GenerationResponse response = new GenerationResponse();
        response.setPassword(password);
        return response;
    }

    private String generateWithRules(String charPool, int length, GenerationRequest request) {
        int maxAttempts = 100;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            StringBuilder password = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(charPool.length());
                password.append(charPool.charAt(index));
            }

            if (meetsMinimumRequirements(password.toString(), request)) {
                return password.toString();
            }
        }

        // Если не удалось сгенерировать с правилами, генерируем базовый
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charPool.length());
            password.append(charPool.charAt(index));
        }
        return password.toString();
    }

    private boolean meetsMinimumRequirements(String password, GenerationRequest request) {
        if (request.getMinLowercase() != null && countLowercase(password) < request.getMinLowercase()) {
            return false;
        }
        if (request.getMinUppercase() != null && countUppercase(password) < request.getMinUppercase()) {
            return false;
        }
        if (request.getMinDigits() != null && countDigits(password) < request.getMinDigits()) {
            return false;
        }
        if (request.getMinSpecial() != null && countSpecial(password) < request.getMinSpecial()) {
            return false;
        }
        return true;
    }

    private int countLowercase(String password) {
        return (int) password.chars().filter(Character::isLowerCase).count();
    }

    private int countUppercase(String password) {
        return (int) password.chars().filter(Character::isUpperCase).count();
    }

    private int countDigits(String password) {
        return (int) password.chars().filter(Character::isDigit).count();
    }

    private int countSpecial(String password) {
        return (int) password.chars().filter(c -> !Character.isLetterOrDigit(c)).count();
    }

    public com.password.dto.BatchGenerationResponse generateBatch(com.password.dto.BatchGenerationRequest request) {
        int count = request.getCount() != null ? request.getCount() : 10;
        count = Math.min(count, 1000); // Максимум 1000 паролей за раз

        java.util.List<String> passwords = new java.util.ArrayList<>();
        java.util.Set<String> uniquePasswords = new java.util.HashSet<>();

        while (uniquePasswords.size() < count) {
            GenerationResponse response = generatePassword(request);
            uniquePasswords.add(response.getPassword());
        }

        passwords.addAll(uniquePasswords);
        return new com.password.dto.BatchGenerationResponse(passwords, passwords.size());
    }
}
