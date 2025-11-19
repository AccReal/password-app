package com.password.util;

import com.password.dto.Feedback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Утилитный класс для расчета надежности пароля
 * Оценка производится по шкале от 0 до 100 баллов
 */
public class PasswordStrengthCalculator {

    /**
     * Рассчитывает баллы за длину пароля (0-30 баллов)
     * 
     * @param password пароль для оценки
     * @return баллы за длину (0-30)
     */
    public static int calculateLengthScore(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int length = password.length();

        if (length < 8) {
            return 0;
        } else if (length <= 11) {
            return 10;
        } else if (length <= 15) {
            return 20;
        } else {
            return 30;
        }
    }

    /**
     * Рассчитывает баллы за разнообразие символов (0-30 баллов)
     * 
     * @param password пароль для оценки
     * @return баллы за разнообразие (0-30)
     */
    public static int calculateDiversityScore(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int score = 0;
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        // Базовые 5 баллов за наличие хотя бы одного типа символов
        if (hasLowercase) {
            score = 5;
        }

        // +10 баллов за заглавные буквы
        if (hasUppercase) {
            score += 10;
        }

        // +10 баллов за цифры
        if (hasDigit) {
            score += 10;
        }

        // +10 баллов за специальные символы (максимум 30)
        if (hasSpecial) {
            score += 10;
        }

        return Math.min(score, 30);
    }

    /**
     * Рассчитывает энтропию пароля (0-25 баллов)
     * Энтропия основана на уникальности символов в пароле
     * 
     * @param password пароль для оценки
     * @return баллы за энтропию (0-25)
     */
    public static int calculateEntropyScore(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        // Подсчет уникальных символов
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : password.toCharArray()) {
            uniqueChars.add(c);
        }

        int length = password.length();
        int uniqueCount = uniqueChars.size();

        // Расчет коэффициента уникальности (от 0 до 1)
        double uniquenessRatio = (double) uniqueCount / length;

        // Базовая энтропия на основе длины и уникальности
        double entropy = uniquenessRatio * Math.log(length + 1) / Math.log(2);

        // Нормализация к шкале 0-25
        int score = (int) Math.min(entropy * 5, 25);

        return score;
    }

    /**
     * Применяет штрафы за обнаруженные паттерны (до -15 баллов)
     * 
     * @param password пароль для проверки
     * @return штрафные баллы (отрицательное значение от 0 до -15)
     */
    public static int calculatePatternPenalty(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int penalty = 0;

        // -5 баллов за последовательные символы
        if (PatternMatcher.hasSequentialCharacters(password)) {
            penalty -= 5;
        }

        // -5 баллов за повторяющиеся символы
        if (PatternMatcher.hasRepeatedCharacters(password)) {
            penalty -= 5;
        }

        // -10 баллов за общие паттерны
        if (PatternMatcher.matchesCommonPattern(password)) {
            penalty -= 10;
        }

        return Math.max(penalty, -15);
    }

    /**
     * Добавляет бонусные баллы за высокое качество пароля (0-15 баллов)
     * 
     * @param password пароль для оценки
     * @param diversityScore оценка разнообразия символов
     * @return бонусные баллы (0-15)
     */
    public static int calculateBonusPoints(String password, int diversityScore) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int bonus = 0;

        // +5 баллов за отсутствие общих паттернов
        if (!PatternMatcher.matchesCommonPattern(password)) {
            bonus += 5;
        }

        // +5 баллов за высокое разнообразие (все 4 типа символов)
        if (diversityScore >= 30) {
            bonus += 5;
        }

        // +5 баллов за длину больше 20 символов
        if (password.length() > 20) {
            bonus += 5;
        }

        return Math.min(bonus, 15);
    }

    /**
     * Определяет категорию надежности пароля по баллам
     * 
     * @param score общая оценка пароля (0-100)
     * @return категория надежности на русском языке
     */
    public static String determineStrengthCategory(int score) {
        if (score < 0) {
            score = 0;
        } else if (score > 100) {
            score = 100;
        }

        if (score <= 25) {
            return "Очень слабый";
        } else if (score <= 50) {
            return "Слабый";
        } else if (score <= 75) {
            return "Средний";
        } else if (score <= 90) {
            return "Сильный";
        } else {
            return "Очень сильный";
        }
    }

    /**
     * Генерирует рекомендации по улучшению пароля на русском языке
     * 
     * @param password пароль для анализа
     * @param lengthScore оценка длины
     * @param diversityScore оценка разнообразия
     * @return список рекомендаций
     */
    public static List<String> generateRecommendations(String password, int lengthScore, int diversityScore) {
        List<String> recommendations = new ArrayList<>();

        if (password == null || password.isEmpty()) {
            recommendations.add("Пароль не может быть пустым");
            return recommendations;
        }

        // Рекомендации по длине
        if (password.length() < 8) {
            recommendations.add("Увеличьте длину пароля минимум до 8 символов");
        } else if (password.length() < 12) {
            recommendations.add("Рекомендуется использовать пароль длиной не менее 12 символов");
        } else if (password.length() < 16) {
            recommendations.add("Для максимальной безопасности используйте пароль длиной 16+ символов");
        }

        // Рекомендации по разнообразию символов
        boolean hasLowercase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

        if (!hasLowercase) {
            recommendations.add("Добавьте строчные буквы (a-z)");
        }
        if (!hasUppercase) {
            recommendations.add("Добавьте заглавные буквы (A-Z)");
        }
        if (!hasDigit) {
            recommendations.add("Добавьте цифры (0-9)");
        }
        if (!hasSpecial) {
            recommendations.add("Добавьте специальные символы (!@#$%^&* и т.д.)");
        }

        // Рекомендации по паттернам
        if (PatternMatcher.hasSequentialCharacters(password)) {
            recommendations.add("Избегайте последовательных символов (abc, 123, xyz)");
        }
        if (PatternMatcher.hasRepeatedCharacters(password)) {
            recommendations.add("Избегайте повторяющихся символов (aaa, 111)");
        }
        if (PatternMatcher.matchesCommonPattern(password)) {
            recommendations.add("Не используйте распространенные слова и паттерны (password, qwerty, 12345)");
        }

        // Общие рекомендации
        if (recommendations.isEmpty()) {
            recommendations.add("Отличный пароль! Продолжайте использовать уникальные и сложные пароли");
        }

        return recommendations;
    }

    /**
     * Рассчитывает общую оценку надежности пароля и создает объект Feedback
     * 
     * @param password пароль для полного анализа
     * @return объект Feedback с детальной информацией
     */
    public static Feedback calculateFullStrength(String password) {
        if (password == null || password.isEmpty()) {
            Feedback feedback = new Feedback();
            feedback.setHasSequentialChars(false);
            feedback.setHasRepeatedChars(false);
            feedback.setMatchesCommonPattern(false);
            feedback.setLengthScore(0);
            feedback.setDiversityScore(0);
            feedback.setRecommendations(generateRecommendations(password, 0, 0));
            return feedback;
        }

        // Расчет компонентов оценки
        int lengthScore = calculateLengthScore(password);
        int diversityScore = calculateDiversityScore(password);
        int entropyScore = calculateEntropyScore(password);
        int patternPenalty = calculatePatternPenalty(password);
        int bonusPoints = calculateBonusPoints(password, diversityScore);

        // Проверка паттернов
        boolean hasSequentialChars = PatternMatcher.hasSequentialCharacters(password);
        boolean hasRepeatedChars = PatternMatcher.hasRepeatedCharacters(password);
        boolean matchesCommonPattern = PatternMatcher.matchesCommonPattern(password);

        // Генерация рекомендаций
        List<String> recommendations = generateRecommendations(password, lengthScore, diversityScore);

        // Создание объекта Feedback
        Feedback feedback = new Feedback(
            hasSequentialChars,
            hasRepeatedChars,
            matchesCommonPattern,
            lengthScore,
            diversityScore,
            recommendations
        );

        return feedback;
    }

    /**
     * Рассчитывает итоговую оценку надежности пароля (0-100)
     * 
     * @param password пароль для оценки
     * @return оценка от 0 до 100
     */
    public static int calculateTotalScore(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int lengthScore = calculateLengthScore(password);
        int diversityScore = calculateDiversityScore(password);
        int entropyScore = calculateEntropyScore(password);
        int patternPenalty = calculatePatternPenalty(password);
        int bonusPoints = calculateBonusPoints(password, diversityScore);

        int totalScore = lengthScore + diversityScore + entropyScore + patternPenalty + bonusPoints;

        // Ограничение диапазона 0-100
        return Math.max(0, Math.min(100, totalScore));
    }

    /**
     * Рассчитывает время взлома пароля методом перебора
     * 
     * @param password пароль для анализа
     * @return строка с описанием времени взлома на русском языке
     */
    public static String calculateCrackTime(String password) {
        if (password == null || password.isEmpty()) {
            return "Мгновенно";
        }

        // Определение размера алфавита
        int charsetSize = 0;
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        if (hasLowercase) charsetSize += 26;
        if (hasUppercase) charsetSize += 26;
        if (hasDigit) charsetSize += 10;
        if (hasSpecial) charsetSize += 32;

        if (charsetSize == 0) charsetSize = 26;

        // Расчет количества возможных комбинаций
        double combinations = Math.pow(charsetSize, password.length());

        // Штраф за общие паттерны (уменьшаем время взлома)
        if (PatternMatcher.matchesCommonPattern(password)) {
            combinations /= 1000000; // Словарная атака намного быстрее
        }
        if (PatternMatcher.hasSequentialCharacters(password)) {
            combinations /= 100;
        }
        if (PatternMatcher.hasRepeatedCharacters(password)) {
            combinations /= 100;
        }

        // Предполагаем 10 миллиардов попыток в секунду (современные GPU)
        double attemptsPerSecond = 10_000_000_000.0;
        double secondsToCrack = combinations / attemptsPerSecond / 2; // делим на 2 (в среднем)

        return formatCrackTime(secondsToCrack);
    }

    /**
     * Форматирует время взлома в читаемый вид
     * 
     * @param seconds время в секундах
     * @return отформатированная строка
     */
    private static String formatCrackTime(double seconds) {
        if (seconds < 0.001) {
            return "Мгновенно";
        } else if (seconds < 1) {
            return "Меньше секунды";
        } else if (seconds < 60) {
            return String.format("%.0f секунд", seconds);
        } else if (seconds < 3600) {
            double minutes = seconds / 60;
            return String.format("%.0f минут", minutes);
        } else if (seconds < 86400) {
            double hours = seconds / 3600;
            return String.format("%.0f часов", hours);
        } else if (seconds < 2592000) { // 30 дней
            double days = seconds / 86400;
            return String.format("%.0f дней", days);
        } else if (seconds < 31536000) { // 365 дней
            double months = seconds / 2592000;
            return String.format("%.0f месяцев", months);
        } else if (seconds < 3153600000.0) { // 100 лет
            double years = seconds / 31536000;
            return String.format("%.0f лет", years);
        } else if (seconds < 31536000000.0) { // 1000 лет
            double years = seconds / 31536000;
            return String.format("%.0f лет", years);
        } else if (seconds < 31536000000000.0) { // 1 миллион лет
            double thousands = seconds / 31536000000.0;
            return String.format("%.0f тысяч лет", thousands);
        } else if (seconds < 31536000000000000.0) { // 1 миллиард лет
            double millions = seconds / 31536000000000.0;
            return String.format("%.1f миллионов лет", millions);
        } else {
            double billions = seconds / 31536000000000000.0;
            return String.format("%.1f миллиардов лет", billions);
        }
    }
}
