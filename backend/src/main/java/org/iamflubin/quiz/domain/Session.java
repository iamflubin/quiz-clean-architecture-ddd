package org.iamflubin.quiz.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Session {
    private final UUID id;

    private final Quiz quiz;

    private Score score;

    public Session(@NonNull Quiz quiz, UUID id, Score score) {
        this.quiz = quiz;
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
        this.score = score;
    }

    public static Session create(Quiz quiz) {
        return new Session(quiz, null, null);
    }
}
