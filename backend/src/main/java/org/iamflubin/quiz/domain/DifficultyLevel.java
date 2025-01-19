package org.iamflubin.quiz.domain;

public enum DifficultyLevel {
    EASY(1.0),
    MEDIUM(1.5),
    HARD(2.0);

    private final double multiplier;

    DifficultyLevel(double multiplier) {
        this.multiplier = multiplier;
    }
}
