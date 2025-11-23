package com.password.controller;

import com.password.dto.GenerationRequest;
import com.password.dto.GenerationResponse;
import com.password.dto.ValidationRequest;
import com.password.dto.ValidationResult;
import com.password.service.PasswordGeneratorService;
import com.password.service.PasswordValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordGeneratorService generatorService;
    private final PasswordValidatorService validatorService;

    public PasswordController(PasswordGeneratorService generatorService, 
                            PasswordValidatorService validatorService) {
        this.generatorService = generatorService;
        this.validatorService = validatorService;
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
}
