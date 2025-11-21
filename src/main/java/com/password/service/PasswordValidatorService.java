package com.password.service;

import com.password.dto.Feedback;
import com.password.dto.ValidationRequest;
import com.password.dto.ValidationResult;
import com.password.util.PasswordStrengthCalculator;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidatorService {

    public ValidationResult validatePassword(ValidationRequest request) {
        String password = request.getPassword();
        
        int score = PasswordStrengthCalculator.calculateTotalScore(password);
        String strength = PasswordStrengthCalculator.determineStrengthCategory(score);
        Feedback feedback = PasswordStrengthCalculator.calculateFullStrength(password);
        String crackTime = PasswordStrengthCalculator.calculateCrackTime(password);

        ValidationResult result = new ValidationResult();
        result.setScore(score);
        result.setStrength(strength);
        result.setFeedback(feedback);
        result.setCrackTime(crackTime);
        
        return result;
    }
}
