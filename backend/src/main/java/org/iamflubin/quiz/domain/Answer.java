package org.iamflubin.quiz.domain;

import lombok.NonNull;


public record Answer(String text) {
    public Answer(@NonNull String text) {
        if (text.isBlank()) {
            throw new IllegalArgumentException("Answer text cannot be blank");
        }
        this.text = text;
    }
}
