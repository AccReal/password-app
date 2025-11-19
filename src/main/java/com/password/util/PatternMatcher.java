package com.password.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Утилитный класс для проверки паролей на наличие слабых паттернов
 */
public class PatternMatcher {

    // Список распространенных слабых паролей
    private static final Set<String> COMMON_WEAK_PASSWORDS = new HashSet<>(Arrays.asList(
        "password", "password1", "password123",
        "qwerty", "qwerty123", "qwertyuiop",
        "12345", "123456", "1234567", "12345678", "123456789", "1234567890",
        "admin", "admin123", "administrator",
        "letmein", "welcome", "welcome1",
        "monkey", "dragon", "master",
        "abc123", "abcd1234",
        "iloveyou", "sunshine",
        "princess", "starwars",
        "football", "baseball",
        "пароль", "пароль123"
    ));

    /**
     * Проверяет наличие последовательных символов в пароле
     * Примеры: abc, 123, xyz, cba, 321
     * 
     * @param password пароль для проверки
     * @return true если найдены последовательные символы (3 или более подряд)
     */
    public static boolean hasSequentialCharacters(String password) {
        if (password == null || password.length() < 3) {
            return false;
        }

        String lowerPassword = password.toLowerCase();
        
        for (int i = 0; i < lowerPassword.length() - 2; i++) {
            char first = lowerPassword.charAt(i);
            char second = lowerPassword.charAt(i + 1);
            char third = lowerPassword.charAt(i + 2);

            // Проверка возрастающей последовательности
            if (second == first + 1 && third == second + 1) {
                return true;
            }

            // Проверка убывающей последовательности
            if (second == first - 1 && third == second - 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Проверяет наличие повторяющихся символов в пароле
     * Примеры: aaa, 111, zzz
     * 
     * @param password пароль для проверки
     * @return true если найдены повторяющиеся символы (3 или более одинаковых подряд)
     */
    public static boolean hasRepeatedCharacters(String password) {
        if (password == null || password.length() < 3) {
            return false;
        }

        for (int i = 0; i < password.length() - 2; i++) {
            char current = password.charAt(i);
            char next1 = password.charAt(i + 1);
            char next2 = password.charAt(i + 2);

            if (current == next1 && current == next2) {
                return true;
            }
        }

        return false;
    }

    /**
     * Проверяет соответствие пароля общим слабым паттернам
     * Проверяет как точное совпадение, так и наличие паттерна внутри пароля
     * 
     * @param password пароль для проверки
     * @return true если пароль содержит общий слабый паттерн
     */
    public static boolean matchesCommonPattern(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        String lowerPassword = password.toLowerCase();

        // Проверка точного совпадения
        if (COMMON_WEAK_PASSWORDS.contains(lowerPassword)) {
            return true;
        }

        // Проверка наличия слабого паттерна внутри пароля
        for (String weakPassword : COMMON_WEAK_PASSWORDS) {
            if (lowerPassword.contains(weakPassword)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Возвращает набор распространенных слабых паролей
     * 
     * @return неизменяемый набор слабых паролей
     */
    public static Set<String> getCommonWeakPasswords() {
        return new HashSet<>(COMMON_WEAK_PASSWORDS);
    }
}
