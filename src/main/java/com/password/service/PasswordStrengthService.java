package com.password.service;

import com.password.dto.Feedback;
import com.password.util.PasswordStrengthCalculator;
import com.password.util.PatternMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordStrengthService {

    private final TranslationService translationService;

    public PasswordStrengthService(TranslationService translationService) {
        this.translationService = translationService;
    }

    public String determineStrengthCategory(int score, String lang) {
        if (score < 0) score = 0;
        else if (score > 100) score = 100;

        if (score <= 25) {
            return translationService.translate("strength.veryWeak", lang);
        } else if (score <= 50) {
            return translationService.translate("strength.weak", lang);
        } else if (score <= 75) {
            return translationService.translate("strength.medium", lang);
        } else if (score <= 90) {
            return translationService.translate("strength.strong", lang);
        } else {
            return translationService.translate("strength.veryStrong", lang);
        }
    }

    public String calculateCrackTime(String password, String lang) {
        if (password == null || password.isEmpty()) {
            return translationService.translate("crackTime.instant", lang);
        }

        int charsetSize = 0;
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) hasLowercase = true;
            else if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        if (hasLowercase) charsetSize += 26;
        if (hasUppercase) charsetSize += 26;
        if (hasDigit) charsetSize += 10;
        if (hasSpecial) charsetSize += 32;
        if (charsetSize == 0) charsetSize = 26;

        double combinations = Math.pow(charsetSize, password.length());

        if (PatternMatcher.matchesCommonPattern(password)) combinations /= 1000000;
        if (PatternMatcher.hasSequentialCharacters(password)) combinations /= 100;
        if (PatternMatcher.hasRepeatedCharacters(password)) combinations /= 100;

        double attemptsPerSecond = 10_000_000_000.0;
        double secondsToCrack = combinations / attemptsPerSecond / 2;

        return formatCrackTime(secondsToCrack, lang);
    }

    private String formatCrackTime(double seconds, String lang) {
        if (seconds < 0.001) {
            return translationService.translate("crackTime.instant", lang);
        } else if (seconds < 1) {
            return translationService.translate("crackTime.lessThanSecond", lang);
        } else if (seconds < 60) {
            return String.format("%.0f %s", seconds, translationService.translate("crackTime.seconds", lang));
        } else if (seconds < 3600) {
            double minutes = seconds / 60;
            return String.format("%.0f %s", minutes, translationService.translate("crackTime.minutes", lang));
        } else if (seconds < 86400) {
            double hours = seconds / 3600;
            return String.format("%.0f %s", hours, translationService.translate("crackTime.hours", lang));
        } else if (seconds < 2592000) {
            double days = seconds / 86400;
            return String.format("%.0f %s", days, translationService.translate("crackTime.days", lang));
        } else if (seconds < 31536000) {
            double months = seconds / 2592000;
            return String.format("%.0f %s", months, translationService.translate("crackTime.months", lang));
        } else if (seconds < 3153600000.0) {
            double years = seconds / 31536000;
            return String.format("%.0f %s", years, translationService.translate("crackTime.years", lang));
        } else if (seconds < 31536000000.0) {
            double years = seconds / 31536000;
            return String.format("%.0f %s", years, translationService.translate("crackTime.years", lang));
        } else if (seconds < 31536000000000.0) {
            double thousands = seconds / 31536000000.0;
            return String.format("%.0f %s", thousands, translationService.translate("crackTime.thousands", lang));
        } else if (seconds < 31536000000000000.0) {
            double millions = seconds / 31536000000000.0;
            return String.format("%.1f %s", millions, translationService.translate("crackTime.millions", lang));
        } else {
            double billions = seconds / 31536000000000000.0;
            return String.format("%.1f %s", billions, translationService.translate("crackTime.billions", lang));
        }
    }

    public List<String> generateRecommendations(String password, int lengthScore, int diversityScore, String lang) {
        List<String> recommendations = new ArrayList<>();

        if (password == null || password.isEmpty()) {
            recommendations.add(translationService.translate("recommendation.empty", lang));
            return recommendations;
        }

        if (password.length() < 8) {
            recommendations.add(translationService.translate("recommendation.increaseLength8", lang));
        } else if (password.length() < 12) {
            recommendations.add(translationService.translate("recommendation.increaseLength12", lang));
        } else if (password.length() < 16) {
            recommendations.add(translationService.translate("recommendation.increaseLength16", lang));
        }

        boolean hasLowercase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

        if (!hasLowercase) recommendations.add(translationService.translate("recommendation.addLowercase", lang));
        if (!hasUppercase) recommendations.add(translationService.translate("recommendation.addUppercase", lang));
        if (!hasDigit) recommendations.add(translationService.translate("recommendation.addDigits", lang));
        if (!hasSpecial) recommendations.add(translationService.translate("recommendation.addSpecial", lang));

        if (PatternMatcher.hasSequentialCharacters(password)) {
            recommendations.add(translationService.translate("recommendation.avoidSequential", lang));
        }
        if (PatternMatcher.hasRepeatedCharacters(password)) {
            recommendations.add(translationService.translate("recommendation.avoidRepeated", lang));
        }
        if (PatternMatcher.matchesCommonPattern(password)) {
            recommendations.add(translationService.translate("recommendation.avoidCommon", lang));
        }

        if (recommendations.isEmpty()) {
            recommendations.add(translationService.translate("recommendation.excellent", lang));
        }

        return recommendations;
    }

    public Feedback calculateFullStrength(String password, String lang) {
        if (password == null || password.isEmpty()) {
            Feedback feedback = new Feedback();
            feedback.setHasSequentialChars(false);
            feedback.setHasRepeatedChars(false);
            feedback.setMatchesCommonPattern(false);
            feedback.setLengthScore(0);
            feedback.setDiversityScore(0);
            feedback.setRecommendations(generateRecommendations(password, 0, 0, lang));
            return feedback;
        }

        int lengthScore = PasswordStrengthCalculator.calculateLengthScore(password);
        int diversityScore = PasswordStrengthCalculator.calculateDiversityScore(password);

        boolean hasSequentialChars = PatternMatcher.hasSequentialCharacters(password);
        boolean hasRepeatedChars = PatternMatcher.hasRepeatedCharacters(password);
        boolean matchesCommonPattern = PatternMatcher.matchesCommonPattern(password);

        List<String> recommendations = generateRecommendations(password, lengthScore, diversityScore, lang);

        return new Feedback(
            hasSequentialChars,
            hasRepeatedChars,
            matchesCommonPattern,
            lengthScore,
            diversityScore,
            recommendations
        );
    }
}
