package com.password.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO с детальной информацией о проверке пароля
 */
public class Feedback {

    private Boolean hasSequentialChars;
    private Boolean hasRepeatedChars;
    private Boolean matchesCommonPattern;
    private Integer lengthScore;
    private Integer diversityScore;
    private List<String> recommendations;

    public Feedback() {
        this.recommendations = new ArrayList<>();
    }

    public Feedback(Boolean hasSequentialChars, Boolean hasRepeatedChars, 
                   Boolean matchesCommonPattern, Integer lengthScore, 
                   Integer diversityScore, List<String> recommendations) {
        this.hasSequentialChars = hasSequentialChars;
        this.hasRepeatedChars = hasRepeatedChars;
        this.matchesCommonPattern = matchesCommonPattern;
        this.lengthScore = lengthScore;
        this.diversityScore = diversityScore;
        this.recommendations = recommendations != null ? recommendations : new ArrayList<>();
    }

    public Boolean getHasSequentialChars() {
        return hasSequentialChars;
    }

    public void setHasSequentialChars(Boolean hasSequentialChars) {
        this.hasSequentialChars = hasSequentialChars;
    }

    public Boolean getHasRepeatedChars() {
        return hasRepeatedChars;
    }

    public void setHasRepeatedChars(Boolean hasRepeatedChars) {
        this.hasRepeatedChars = hasRepeatedChars;
    }

    public Boolean getMatchesCommonPattern() {
        return matchesCommonPattern;
    }

    public void setMatchesCommonPattern(Boolean matchesCommonPattern) {
        this.matchesCommonPattern = matchesCommonPattern;
    }

    public Integer getLengthScore() {
        return lengthScore;
    }

    public void setLengthScore(Integer lengthScore) {
        this.lengthScore = lengthScore;
    }

    public Integer getDiversityScore() {
        return diversityScore;
    }

    public void setDiversityScore(Integer diversityScore) {
        this.diversityScore = diversityScore;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public void addRecommendation(String recommendation) {
        this.recommendations.add(recommendation);
    }
}
