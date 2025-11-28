package com.password.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatisticsService {

    private final AtomicInteger generatedCount = new AtomicInteger(0);
    private final AtomicInteger validatedCount = new AtomicInteger(0);
    private final List<Integer> validationScores = new ArrayList<>();
    private final List<Long> activityTimestamps = new ArrayList<>();

    public void incrementGenerated() {
        generatedCount.incrementAndGet();
        recordActivity();
    }

    public void recordValidation(int score) {
        validatedCount.incrementAndGet();
        synchronized (validationScores) {
            validationScores.add(score);
        }
        recordActivity();
    }

    private void recordActivity() {
        synchronized (activityTimestamps) {
            activityTimestamps.add(System.currentTimeMillis());
        }
    }

    public int getGeneratedCount() {
        return generatedCount.get();
    }

    public int getValidatedCount() {
        return validatedCount.get();
    }

    public double getAverageScore() {
        synchronized (validationScores) {
            if (validationScores.isEmpty()) {
                return 0.0;
            }
            return validationScores.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0);
        }
    }

    public List<Long> getActivityTimestamps() {
        synchronized (activityTimestamps) {
            return new ArrayList<>(activityTimestamps);
        }
    }
}
