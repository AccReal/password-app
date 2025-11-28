package com.password.dto;

public class CompareResponse {
    private ValidationResult password1Result;
    private ValidationResult password2Result;
    private String winner;
    private Integer scoreDifference;
    private String recommendation;

    public CompareResponse() {
    }

    public CompareResponse(ValidationResult password1Result, ValidationResult password2Result,
                          String winner, Integer scoreDifference, String recommendation) {
        this.password1Result = password1Result;
        this.password2Result = password2Result;
        this.winner = winner;
        this.scoreDifference = scoreDifference;
        this.recommendation = recommendation;
    }

    public ValidationResult getPassword1Result() {
        return password1Result;
    }

    public void setPassword1Result(ValidationResult password1Result) {
        this.password1Result = password1Result;
    }

    public ValidationResult getPassword2Result() {
        return password2Result;
    }

    public void setPassword2Result(ValidationResult password2Result) {
        this.password2Result = password2Result;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Integer getScoreDifference() {
        return scoreDifference;
    }

    public void setScoreDifference(Integer scoreDifference) {
        this.scoreDifference = scoreDifference;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
