package com.password.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO для запроса проверки пароля
 */
public class ValidationRequest {

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    public ValidationRequest() {
    }

    public ValidationRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
