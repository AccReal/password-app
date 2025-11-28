package com.password.dto;

/**
 * DTO с результатом проверки пароля
 */
public class ValidationResult {

    private Integer score;
    private String strength;
    private Feedback feedback;
    private String crackTime;
    private Boolean isPwned;

    public ValidationResult() {
    }

    public ValidationResult(Integer score, String strength, Feedback feedback, String crackTime, Boolean isPwned) {
        this.score = score;
        this.strength = strength;
        this.feedback = feedback;
        this.crackTime = crackTime;
        this.isPwned = isPwned;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public String getCrackTime() {
        return crackTime;
    }

    public void setCrackTime(String crackTime) {
        this.crackTime = crackTime;
    }

    public Boolean getIsPwned() {
        return isPwned;
    }

    public void setIsPwned(Boolean isPwned) {
        this.isPwned = isPwned;
    }
}
