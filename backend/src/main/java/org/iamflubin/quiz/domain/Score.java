package org.iamflubin.quiz.domain;

public record Score(double value) {
    public Score {
        if (value < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
    }
}
