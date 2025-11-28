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

    private Boolean excludeSimilar;
    private Boolean includeCyrillic;
    private String excludeCharacters;
    private Integer minLowercase;
    private Integer minUppercase;
    private Integer minDigits;
    private Integer minSpecial;

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

    public Boolean getExcludeSimilar() {
        return excludeSimilar;
    }

    public void setExcludeSimilar(Boolean excludeSimilar) {
        this.excludeSimilar = excludeSimilar;
    }

    public Boolean getIncludeCyrillic() {
        return includeCyrillic;
    }

    public void setIncludeCyrillic(Boolean includeCyrillic) {
        this.includeCyrillic = includeCyrillic;
    }

    public String getExcludeCharacters() {
        return excludeCharacters;
    }

    public void setExcludeCharacters(String excludeCharacters) {
        this.excludeCharacters = excludeCharacters;
    }

    public Integer getMinLowercase() {
        return minLowercase;
    }

    public void setMinLowercase(Integer minLowercase) {
        this.minLowercase = minLowercase;
    }

    public Integer getMinUppercase() {
        return minUppercase;
    }

    public void setMinUppercase(Integer minUppercase) {
        this.minUppercase = minUppercase;
    }

    public Integer getMinDigits() {
        return minDigits;
    }

    public void setMinDigits(Integer minDigits) {
        this.minDigits = minDigits;
    }

    public Integer getMinSpecial() {
        return minSpecial;
    }

    public void setMinSpecial(Integer minSpecial) {
        this.minSpecial = minSpecial;
    }
}

