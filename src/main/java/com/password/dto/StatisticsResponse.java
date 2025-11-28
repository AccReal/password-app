package com.password.dto;

import java.util.List;

public class StatisticsResponse {
    private Integer generatedCount;
    private Integer validatedCount;
    private Double averageScore;
    private List<Long> activityTimestamps;

    public StatisticsResponse() {
    }

    public StatisticsResponse(Integer generatedCount, Integer validatedCount, 
                            Double averageScore, List<Long> activityTimestamps) {
        this.generatedCount = generatedCount;
        this.validatedCount = validatedCount;
        this.averageScore = averageScore;
        this.activityTimestamps = activityTimestamps;
    }

    public Integer getGeneratedCount() {
        return generatedCount;
    }

    public void setGeneratedCount(Integer generatedCount) {
        this.generatedCount = generatedCount;
    }

    public Integer getValidatedCount() {
        return validatedCount;
    }

    public void setValidatedCount(Integer validatedCount) {
        this.validatedCount = validatedCount;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public List<Long> getActivityTimestamps() {
        return activityTimestamps;
    }

    public void setActivityTimestamps(List<Long> activityTimestamps) {
        this.activityTimestamps = activityTimestamps;
    }
}
