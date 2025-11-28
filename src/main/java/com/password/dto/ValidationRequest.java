package com.password.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO для запроса проверки пароля
 */
public class ValidationRequest {

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
    private String lang;

    public ValidationRequest() {
    }

    public ValidationRequest(String password) {
        this.password = password;
    }

    public ValidationRequest(String password, String lang) {
        this.password = password;
        this.lang = lang;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLang() {
        return lang != null ? lang : "ru";
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
