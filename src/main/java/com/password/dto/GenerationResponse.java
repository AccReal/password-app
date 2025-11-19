package com.password.dto;

/**
 * DTO для ответа с сгенерированным паролем
 */
public class GenerationResponse {

    private String password;
    private Integer length;

    public GenerationResponse() {
    }

    public GenerationResponse(String password, Integer length) {
        this.password = password;
        this.length = length;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
