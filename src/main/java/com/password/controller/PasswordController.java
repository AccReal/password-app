package com.password.controller;

import com.password.dto.*;
import com.password.service.PasswordGeneratorService;
import com.password.service.PasswordValidatorService;
import com.password.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordGeneratorService generatorService;
    private final PasswordValidatorService validatorService;
    private final StatisticsService statisticsService;

    public PasswordController(PasswordGeneratorService generatorService, 
                            PasswordValidatorService validatorService,
                            StatisticsService statisticsService) {
        this.generatorService = generatorService;
        this.validatorService = validatorService;
        this.statisticsService = statisticsService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GenerationResponse> generatePassword(@RequestBody GenerationRequest request) {
        GenerationResponse response = generatorService.generatePassword(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidationResult> validatePassword(@RequestBody ValidationRequest request) {
        ValidationResult result = validatorService.validatePassword(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/compare")
    public ResponseEntity<CompareResponse> comparePasswords(@RequestBody CompareRequest request) {
        CompareResponse response = validatorService.comparePasswords(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> getStatistics() {
        StatisticsResponse response = new StatisticsResponse(
            statisticsService.getGeneratedCount(),
            statisticsService.getValidatedCount(),
            statisticsService.getAverageScore(),
            statisticsService.getActivityTimestamps()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate/batch")
    public ResponseEntity<com.password.dto.BatchGenerationResponse> generateBatch(
            @RequestBody com.password.dto.BatchGenerationRequest request) {
        com.password.dto.BatchGenerationResponse response = generatorService.generateBatch(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate/batch/csv")
    public ResponseEntity<String> generateBatchCsv(
            @RequestBody com.password.dto.BatchGenerationRequest request) {
        com.password.dto.BatchGenerationResponse batch = generatorService.generateBatch(request);
        
        StringBuilder csv = new StringBuilder();
        csv.append("Number,Password,Length,Strength\n");
        
        for (int i = 0; i < batch.getPasswords().size(); i++) {
            String password = batch.getPasswords().get(i);
            int score = com.password.util.PasswordStrengthCalculator.calculateTotalScore(password);
            String strength = com.password.util.PasswordStrengthCalculator.determineStrengthCategory(score);
            
            csv.append(i + 1).append(",")
               .append(password).append(",")
               .append(password.length()).append(",")
               .append(strength).append("\n");
        }
        
        return ResponseEntity.ok()
                .header("Content-Type", "text/csv; charset=utf-8")
                .header("Content-Disposition", "attachment; filename=passwords.csv")
                .body(csv.toString());
    }
}
