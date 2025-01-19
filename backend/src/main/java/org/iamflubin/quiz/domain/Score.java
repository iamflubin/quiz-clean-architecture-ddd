package org.iamflubin.quiz.domain;

public record Score(int value) {
    public Score {
        if (value < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
    }
}
