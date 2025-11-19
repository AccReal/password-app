package com.password.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO для запроса генерации пароля
 */
public class GenerationRequest {

    @NotNull(message = "Длина пароля обязательна")
    @Min(value = 8, message = "Минимальная длина пароля - 8 символов")
    @Max(value = 128, message = "Максимальная длина пароля - 128 символов")
    private Integer length;

    @NotNull(message = "Параметр includeLowercase обязателен")
    private Boolean includeLowercase;

    @NotNull(message = "Параметр includeUppercase обязателен")
    private Boolean includeUppercase;

    @NotNull(message = "Параметр includeDigits обязателен")
    private Boolean includeDigits;

    @NotNull(message = "Параметр includeSpecial обязателен")
    private Boolean includeSpecial;

    public GenerationRequest() {
    }

    public GenerationRequest(Integer length, Boolean includeLowercase, Boolean includeUppercase, 
                           Boolean includeDigits, Boolean includeSpecial) {
        this.length = length;
        this.includeLowercase = includeLowercase;
        this.includeUppercase = includeUppercase;
        this.includeDigits = includeDigits;
        this.includeSpecial = includeSpecial;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean getIncludeLowercase() {
        return includeLowercase;
    }

    public void setIncludeLowercase(Boolean includeLowercase) {
        this.includeLowercase = includeLowercase;
    }

    public Boolean getIncludeUppercase() {
        return includeUppercase;
    }

    public void setIncludeUppercase(Boolean includeUppercase) {
        this.includeUppercase = includeUppercase;
    }

    public Boolean getIncludeDigits() {
        return includeDigits;
    }

    public void setIncludeDigits(Boolean includeDigits) {
        this.includeDigits = includeDigits;
    }

    public Boolean getIncludeSpecial() {
        return includeSpecial;
    }

    public void setIncludeSpecial(Boolean includeSpecial) {
        this.includeSpecial = includeSpecial;
    }
}
