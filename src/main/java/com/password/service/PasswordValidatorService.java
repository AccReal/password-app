package com.password.service;

import com.password.dto.Feedback;
import com.password.dto.ValidationRequest;
import com.password.dto.ValidationResult;
import com.password.util.PasswordStrengthCalculator;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidatorService {

    private final StatisticsService statisticsService;
    private final BreachCheckService breachCheckService;
    private final PasswordStrengthService passwordStrengthService;
    private final TranslationService translationService;

    public PasswordValidatorService(StatisticsService statisticsService, 
                                   BreachCheckService breachCheckService,
                                   PasswordStrengthService passwordStrengthService,
                                   TranslationService translationService) {
        this.statisticsService = statisticsService;
        this.breachCheckService = breachCheckService;
        this.passwordStrengthService = passwordStrengthService;
        this.translationService = translationService;
    }

    public ValidationResult validatePassword(ValidationRequest request) {
        String password = request.getPassword();
        String lang = request.getLang();
        
        int score = PasswordStrengthCalculator.calculateTotalScore(password);
        String strength = passwordStrengthService.determineStrengthCategory(score, lang);
        Feedback feedback = passwordStrengthService.calculateFullStrength(password, lang);
        String crackTime = passwordStrengthService.calculateCrackTime(password, lang);
        boolean isPwned = breachCheckService.isPasswordPwned(password);

        ValidationResult result = new ValidationResult();
        result.setScore(score);
        result.setStrength(strength);
        result.setFeedback(feedback);
        result.setCrackTime(crackTime);
        result.setIsPwned(isPwned);
        
        statisticsService.recordValidation(score);
        
        return result;
    }

    public com.password.dto.CompareResponse comparePasswords(com.password.dto.CompareRequest request) {
        String lang = request.getLang();
        
        ValidationResult result1 = validatePassword(new ValidationRequest(request.getPassword1(), lang));
        ValidationResult result2 = validatePassword(new ValidationRequest(request.getPassword2(), lang));

        int score1 = result1.getScore();
        int score2 = result2.getScore();
        int difference = Math.abs(score1 - score2);

        String winner;
        String recommendation;

        if (score1 > score2) {
            winner = "password1";
            recommendation = translationService.translate("compare.stronger1", lang, difference);
        } else if (score2 > score1) {
            winner = "password2";
            recommendation = translationService.translate("compare.stronger2", lang, difference);
        } else {
            winner = "equal";
            recommendation = translationService.translate("compare.equal", lang);
        }

        return new com.password.dto.CompareResponse(result1, result2, winner, difference, recommendation);
    }
}
